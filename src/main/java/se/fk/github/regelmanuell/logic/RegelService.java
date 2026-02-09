package se.fk.github.regelmanuell.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.fk.rimfrost.Status;
import se.fk.rimfrost.framework.regel.integration.config.RegelConfigProvider;
import se.fk.rimfrost.framework.regel.integration.kafka.RegelKafkaProducer;
import se.fk.rimfrost.framework.regel.integration.kundbehovsflode.KundbehovsflodeAdapter;
import se.fk.rimfrost.framework.regel.integration.kundbehovsflode.dto.ImmutableKundbehovsflodeRequest;
import se.fk.rimfrost.framework.oul.integration.kafka.dto.ImmutableOulMessageRequest;
import se.fk.rimfrost.framework.oul.integration.kafka.OulKafkaProducer;
import se.fk.rimfrost.framework.oul.logic.dto.OulResponse;
import se.fk.rimfrost.framework.oul.logic.dto.OulStatus;
import se.fk.rimfrost.framework.regel.logic.RegelMapper;
import se.fk.rimfrost.framework.regel.logic.dto.RegelDataRequest;
import se.fk.rimfrost.framework.regel.logic.entity.CloudEventData;
import se.fk.rimfrost.framework.regel.logic.entity.ImmutableCloudEventData;
import se.fk.rimfrost.framework.regel.Utfall;
import se.fk.rimfrost.framework.oul.presentation.kafka.OulHandlerInterface;
import se.fk.rimfrost.framework.oul.presentation.rest.OulUppgiftDoneHandler;
import se.fk.rimfrost.framework.regel.presentation.kafka.RegelRequestHandlerInterface;

@ApplicationScoped
public class RegelService implements RegelRequestHandlerInterface, OulHandlerInterface, OulUppgiftDoneHandler
{
   @ConfigProperty(name = "kafka.source")
   String kafkaSource;

   @ConfigProperty(name = "mp.messaging.outgoing.regel-responses.topic")
   String responseTopic;

   @Inject
   RegelConfigProvider regelConfigProvider;

   @Inject
   RegelKafkaProducer regelKafkaProducer;

   @Inject
   OulKafkaProducer oulKafkaProducer;

   @Inject
   RegelMapper regelMapper;

   @Inject
   KundbehovsflodeAdapter kundbehovsflodeAdapter;

   Map<UUID, CloudEventData> cloudevents = new HashMap<UUID, CloudEventData>();

   // TODO: Remove this field if not needed
   Map<UUID, UUID> uppgiftIds = new HashMap<UUID, UUID>();

   @Override
   public void handleRegelRequest(RegelDataRequest request)
   {
      var kundbehovsflodeRequest = ImmutableKundbehovsflodeRequest.builder()
            .kundbehovsflodeId(request.kundbehovsflodeId())
            .build();
      var kundbehovflodesResponse = kundbehovsflodeAdapter.getKundbehovsflodeInfo(kundbehovsflodeRequest);

      var cloudeventData = ImmutableCloudEventData.builder()
            .id(request.id())
            .kogitoparentprociid(request.kogitoparentprociid())
            .kogitoprocid(request.kogitoprocid())
            .kogitoprocinstanceid(request.kogitoprocinstanceid())
            .kogitoprocist(request.kogitoprocist())
            .kogitoprocversion(request.kogitoprocversion())
            .kogitorootprocid(request.kogitorootprocid())
            .kogitorootprociid(request.kogitorootprociid())
            .type(responseTopic)
            .source(kafkaSource)
            .build();

      /*
       * TODO: Any processing needed before OUL request is sent
       *
       * Any work needed to be done before the request to OUL
       * should be done here, such as storing any data needed
       * in later stages of the rules processing.
       */

      cloudevents.put(request.kundbehovsflodeId(), cloudeventData);

      var regelConfig = regelConfigProvider.getConfig();

      var oulMessageRequest = ImmutableOulMessageRequest.builder()
            .kundbehovsflodeId(request.kundbehovsflodeId())
            .kundbehov(kundbehovflodesResponse.formanstyp())
            .regel(regelConfig.getSpecifikation().getNamn())
            .beskrivning(regelConfig.getSpecifikation().getUppgiftbeskrivning())
            .verksamhetslogik(regelConfig.getSpecifikation().getVerksamhetslogik())
            .roll(regelConfig.getSpecifikation().getRoll())
            .url(regelConfig.getUppgift().getPath())
            .build();
      oulKafkaProducer.sendOulRequest(oulMessageRequest);
   }

   @Override
   public void handleOulResponse(OulResponse oulResponse)
   {
      /* TODO: Handle OUL response
       *
       * Any updates needed to be done when receiving
       * the response to the OUL request should be handled
       * here, such as for example updating the customer
       * flow information or storing the task id for later
       * use.
       */

      // TODO: Remove this if better data structure for storing task id is available
      uppgiftIds.put(oulResponse.kundbehovsflodeId(), oulResponse.uppgiftId());

      // TODO: Update customer flow information
   }

   @Override
   public void handleOulStatus(OulStatus oulStatus)
   {
      /*
       * TODO: Handle status updates from OUL
       *
       * Any updates needed to be done when receiving
       * status updates from OUL should be handled here,
       * such as for example updating the customer flow
       * information with the updated task status. Note
       * that responses may arrive after handleUppgiftDone
       * has been called.
       */

      // TODO: Update customer flow information
   }

   @Override
   public void handleUppgiftDone(UUID kundbehovsflodeId)
   {
      /*
       * TODO: Adapt as needed
       *
       * This method is called when a case officer has marked
       * the task as done through the REST api. Any updates
       * needed to be done in connection with that could
       * be done here, such as cleaning up any data associated
       * with the customer flow, sending status update request to OUL,
       * updating customer flow information and sending rule response.
       */

      // TODO: Replace this with the decision resulting from the evaluation of the rule
      var utfall = Utfall.JA;

      var cloudevent = cloudevents.get(kundbehovsflodeId);
      // TODO: Replace this if storing the task id in a different data structure
      var uppgiftId = uppgiftIds.get(kundbehovsflodeId);
      var regelResponse = regelMapper.toRegelResponse(kundbehovsflodeId, cloudevent, utfall);
      oulKafkaProducer.sendOulStatusUpdate(uppgiftId, Status.AVSLUTAD);
      regelKafkaProducer.sendRegelResponse(regelResponse);

      // TODO: Update customer flow information

      cloudevents.remove(kundbehovsflodeId);
      uppgiftIds.remove(kundbehovsflodeId);
   }
}

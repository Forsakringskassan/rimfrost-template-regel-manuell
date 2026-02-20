package se.fk.github.regel.template.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.store.storage.types.StorageManager;
import se.fk.github.regel.template.logic.dto.GetTemplateDataRequest;
import se.fk.github.regel.template.logic.dto.GetTemplateDataResponse;
import se.fk.github.regel.template.storage.TemplateDataStorageProvider;
import se.fk.github.regel.template.storage.entity.TemplateDataStorage;
import se.fk.rimfrost.framework.kundbehovsflode.adapter.KundbehovsflodeAdapter;
import se.fk.rimfrost.framework.kundbehovsflode.adapter.dto.ImmutableKundbehovsflodeRequest;
import se.fk.rimfrost.framework.regel.Utfall;
import se.fk.rimfrost.framework.regel.integration.config.RegelConfigProvider;
import se.fk.rimfrost.framework.regel.logic.RegelMapper;
import se.fk.rimfrost.framework.regel.logic.config.RegelConfig;
import se.fk.rimfrost.framework.regel.logic.dto.Beslutsutfall;
import se.fk.rimfrost.framework.regel.manuell.logic.entity.ImmutableRegelData;
import se.fk.rimfrost.framework.regel.manuell.logic.entity.RegelData;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellServiceInterface;
import se.fk.rimfrost.framework.storage.StorageManagerProvider;

import java.util.UUID;

@ApplicationScoped
@Startup
public class TemplateService implements RegelManuellServiceInterface
{
   @Inject
   TemplateMapper templateMapper;

   @Inject
   RegelMapper regelMapper;

   @Inject
   RegelConfigProvider regelConfigProvider;

   @Inject
   KundbehovsflodeAdapter kundbehovsflodeAdapter;

   @Inject
   TemplateDataStorageProvider templateDataStorageProvider;

   @Inject
   StorageManagerProvider storageManagerProvider;

   private TemplateDataStorage templateDataStorage;

   private StorageManager storageManager;

   private RegelConfig regelConfig;

   @PostConstruct
   public void init()
   {
      regelConfig = regelConfigProvider.getConfig();
      templateDataStorage = templateDataStorageProvider.getDataStorage();
      storageManager = storageManagerProvider.getStorageManager();
   }

   public GetTemplateDataResponse getData(GetTemplateDataRequest request) throws JsonProcessingException
   {
      // TODO kundbehovsflöde GET/PUT bör kunna flyttas till ramverket
      var kundbehovsflodeRequest = ImmutableKundbehovsflodeRequest.builder()
            .kundbehovsflodeId(request.kundbehovsflodeId())
            .build();
      var kundbehovflodesResponse = kundbehovsflodeAdapter.getKundbehovsflodeInfo(kundbehovsflodeRequest);

      /*
       *
       * Lägg till regel-specifik logik
       *
       */

      var regelData = templateDataStorage.getCommonRegelData().getRegelData(request.kundbehovsflodeId());

      updateRegelDataUnderlag(request.kundbehovsflodeId(), regelData
      // folkbokfordResponse, // ersätt med regel-specifikt data
      // arbetsgivareResponse
      );

      var putKundbehovsflodeRequest = regelMapper.toPutKundbehovsflodeRequest(request.kundbehovsflodeId(),
            regelData.uppgiftData(), regelData.underlag(), regelConfig);
      kundbehovsflodeAdapter.putKundbehovsflode(putKundbehovsflodeRequest);

      return templateMapper.toTemplateResponse(kundbehovflodesResponse,
            // folkbokfordResponse, // ersätt med regel-specifikt data
            regelData);
   }

   private void updateRegelDataUnderlag(UUID kundbehovsflodeId, RegelData regelData
   // FolkbokfordResponse folkbokfordResponse, // ersätt med regel-specifikt data
   ) throws JsonProcessingException
   {

      var regelDataBuilder = ImmutableRegelData.builder().from(regelData);

      /*
       *
       * Lägg till regel-specifik logik
       *
       */

      var commonRegelData = templateDataStorage.getCommonRegelData();

      synchronized (commonRegelData.getLock())
      {
         var regelDatas = commonRegelData.getRegelDatas();
         regelDatas.put(kundbehovsflodeId, regelDataBuilder.build());
         storageManager.store(regelDatas);
      }
   }

   @Override
   public Utfall decideUtfall(RegelData regelData)
   {
      //
      // Ersätt med regel-specifik logik
      //
      return regelData.ersattningar().stream().allMatch(e -> e.beslutsutfall() == Beslutsutfall.JA) ? Utfall.JA : Utfall.NEJ;
   }

   @Override
   public void handleRegelDone(UUID kundbehovsflodeId)
   {
      //
      // Ersätt med regel-specific logik
      // för att hantera detaljer som behöver
      // göras när regeln är färdig, så som
      // att städa eventuell data i storage.
      //
   }
}

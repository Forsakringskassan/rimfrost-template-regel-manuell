package se.fk.github.regel.template.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.fk.github.regel.template.logic.dto.GetTemplateDataRequest;
import se.fk.github.regel.template.logic.dto.GetTemplateDataResponse;
import se.fk.rimfrost.framework.kundbehovsflode.adapter.dto.ImmutableKundbehovsflodeRequest;
import se.fk.rimfrost.framework.regel.Utfall;
import se.fk.rimfrost.framework.regel.logic.dto.Beslutsutfall;
import se.fk.rimfrost.framework.regel.logic.entity.ImmutableRegelData;
import se.fk.rimfrost.framework.regel.logic.entity.RegelData;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellService;

@ApplicationScoped
@Startup
public class TemplateService extends RegelManuellService
{

   @Inject
   TemplateMapper templateMapper;

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

      var regelData = regelDatas.get(request.kundbehovsflodeId());

      updateRegelDataUnderlag(regelData
      // folkbokfordResponse, // ersätt med regel-specifikt data
      // arbetsgivareResponse
      );

      updateKundbehovsflodeInfo(regelData);

      return templateMapper.toTemplateResponse(kundbehovflodesResponse,
            // folkbokfordResponse, // ersätt med regel-specifikt data
            regelData);
   }

   private void updateRegelDataUnderlag(RegelData regelData
   // FolkbokfordResponse folkbokfordResponse, // ersätt med regel-specifikt data
   ) throws JsonProcessingException
   {

      var regelDataBuilder = ImmutableRegelData.builder().from(regelData);

      /*
       *
       * Lägg till regel-specifik logik
       *
       */

      regelDatas.put(regelData.kundbehovsflodeId(), regelDataBuilder.build());
   }

   @Override
   protected Utfall decideUtfall(RegelData regelData)
   {
      //
      // Ersätt med regel-specifik logik
      //
      return regelData.ersattningar().stream().allMatch(e -> e.beslutsutfall() == Beslutsutfall.JA) ? Utfall.JA : Utfall.NEJ;
   }
}

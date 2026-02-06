package se.fk.github.regel.template.logic;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.github.regel.template.logic.dto.GetTemplateDataResponse;
import se.fk.github.regel.template.logic.dto.ImmutableGetTemplateDataResponse;
import se.fk.rimfrost.framework.regel.integration.kundbehovsflode.dto.KundbehovsflodeResponse;
import se.fk.rimfrost.framework.regel.logic.entity.RegelData;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellMapper;

@ApplicationScoped
public class TemplateMapper extends RegelManuellMapper
{

   public GetTemplateDataResponse toTemplateResponse(KundbehovsflodeResponse kundbehovflodesResponse,
         // FolkbokfordResponse folkbokfordResponse, // Ersätt med regel-specifikt data
         RegelData regelData)
   {
      /*
       *
       * Ersätt med regel-specifik logik
       *
       */

      var builder = ImmutableGetTemplateDataResponse.builder()
            .kundbehovsflodeId(kundbehovflodesResponse.kundbehovsflodeId())
      // .ersattning(ersattningsList) // Ersätt med regel-specifikt data
      ;

      return builder.build();
   }
}

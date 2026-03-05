package se.fk.github.regel.template.logic;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.github.regel.template.logic.dto.GetTemplateDataResponse;
import se.fk.github.regel.template.logic.dto.ImmutableGetTemplateDataResponse;
import se.fk.rimfrost.framework.handlaggning.adapter.dto.HandlaggningResponse;
import se.fk.rimfrost.framework.regel.manuell.logic.entity.RegelData;

@ApplicationScoped
public class TemplateMapper
{

   public GetTemplateDataResponse toTemplateResponse(HandlaggningResponse handlaggningResponse,
         //
         // FolkbokfordResponse folkbokfordResponse, // Ersätt med regel-specifikt data
         //
         RegelData regelData)
   {
      /*
       *
       * Ersätt med regel-specifik logik
       *
       */

      var builder = ImmutableGetTemplateDataResponse.builder()
            .handlaggningId(handlaggningResponse.handlaggningId())
      //
      // .ersattning(ersattningsList) // Ersätt med regel-specifikt data
      //
      ;

      return builder.build();
   }
}

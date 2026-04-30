package se.fk.github.regeltemplate.logic;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.framework.handlaggning.model.Handlaggning;
import se.fk.rimfrost.template.regel.manuell.openapi.jaxrsspec.controllers.generatedsource.model.GetDataResponse; // TODO byt ut mot regelns API

@ApplicationScoped
public class RegelTemplateMapper
{

   // TODO använd GetDataResponse genererad från regelns OpenAPI spec
   public GetDataResponse toGetDataResponse(Handlaggning handlaggningResponse
   // TODO regel-specifikt data
   )
   {
      // TODO to be implemented
      return null;
   }
}

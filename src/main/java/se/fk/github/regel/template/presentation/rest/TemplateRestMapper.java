package se.fk.github.regel.template.presentation.rest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemplateRestMapper
{

   /*
    *
    * Lägg till DTO-mappnings-metoder för regel-specifikt data.
    * I exempel nedan är GetDataResponse en DTO som förväntas definieras i regelns specifika OpenApi-spec.
    *
    */

   /*
   
   public GetDataResponse toGetDataResponse(GetBekraftaBeslutDataResponse bekraftaBeslutResponse)
   {
     var response = new GetDataResponse();
     // Regel-specifik logik
     return response;
   }
   
   private Beslutsutfall mapBeslutsutfall(
           se.fk.rimfrost.regel.template.openapi.jaxrsspec.controllers.generatedsource.model.Beslutsutfall beslututfall) {
       return switch (beslututfall) {
           case JA -> Beslutsutfall.JA;
           case NEJ -> Beslutsutfall.NEJ;
           case FU -> Beslutsutfall.FU;
           default -> null;
       };
   }
   
   private se.fk.rimfrost.regel.template.openapi.jaxrsspec.controllers.generatedsource.model.Beslutsutfall mapBeslutsutfall(
           Beslutsutfall beslututfall) {
       return switch (beslututfall) {
           case JA ->
                   se.fk.rimfrost.regel.template.openapi.jaxrsspec.controllers.generatedsource.model.Beslutsutfall.JA;
           case NEJ ->
                   se.fk.rimfrost.regel.template.openapi.jaxrsspec.controllers.generatedsource.model.Beslutsutfall.NEJ;
           case FU ->
                   se.fk.rimfrost.regel.template.openapi.jaxrsspec.controllers.generatedsource.model.Beslutsutfall.FU;
           default -> null;
       };
   }
   
   */

}

package se.fk.github.regeltemplate.logic;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellMiddlewareService;
import se.fk.rimfrost.template.regel.manuell.openapi.jaxrsspec.controllers.generatedsource.model.GetDataResponse; // TODO byt ut mot regelns API
import se.fk.rimfrost.template.regel.manuell.openapi.jaxrsspec.controllers.generatedsource.model.PatchDataRequest; // TODO byt ut mot regelns API

@ApplicationScoped
public class RegelTemplateMiddlewareServiceImpl
      extends RegelManuellMiddlewareService<GetDataResponse, PatchDataRequest> // TODO byt ut mot klasser genererade från regelns OpenAPI-spec
{

}

package se.fk.github.regel.template.presentation.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import se.fk.rimfrost.framework.regel.manuell.presentation.rest.RegelManuellController;
import se.fk.rimfrost.template.regel.manuell.openapi.jaxrsspec.controllers.generatedsource.model.GetDataResponse; // TODO byt ut mot regelns API
import se.fk.rimfrost.template.regel.manuell.openapi.jaxrsspec.controllers.generatedsource.model.PatchDataRequest; // TODO byt ut mot regelns API

@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
@Path("/regel/_Template_") // TODO to be renamed
public class _Template_Controller extends RegelManuellController<GetDataResponse, PatchDataRequest> // TODO byt ut till klasser genererade från regelns OpenAPI-spec
{
}

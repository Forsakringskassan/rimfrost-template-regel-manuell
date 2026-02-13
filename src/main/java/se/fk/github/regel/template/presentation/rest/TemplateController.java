package se.fk.github.regel.template.presentation.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.fk.github.regel.template.logic.TemplateMapper;
import se.fk.github.regel.template.logic.TemplateService;
import se.fk.rimfrost.framework.regel.manuell.presentation.rest.RegelManuellController;

@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
// TODO: Change path to something relevant for the rule
@Path("/regel/template")
public class TemplateController extends RegelManuellController

/*
*
* Lägg till 'implements' för det regel-specifika api't.
*
* T.ex:
*
* public class BekraftaBeslutController  extends RegelManuellController implements RegelBekraftaBeslutControllerApi
*
*/

{
   private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

   @Inject
   TemplateService templateService;

   @Inject
   TemplateMapper templateMapper;

   /*
   * TODO: Any rest endpoints needed for rule
   *
   * Any endpoints needed for interacting with a
   * case officer should be added here, such as
   * for reading information about the task at
   * hand or any endpoints needed for updating
   * information.
   */

   /*
     @GET
    @Path("/{kundbehovsflodeId}")
    @Override
    public GetDataResponse getData(UUID kundbehovsflodeId)
    {
        try
        {
            var request = ImmutableGetTemplateDataRequest.builder()
                    .kundbehovsflodeId(kundbehovsflodeId).build();
            var response = templateService.getData(request);
            return templateMapper.toGetDataResponse(response);
        }
        catch (JsonProcessingException e)
        {
            throw new InternalServerErrorException("Failed to process request");
        }
    }
   
    */
}

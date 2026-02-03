package se.fk.github.regelmanuell.presentation.rest;

import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.enterprise.context.ApplicationScoped;
import se.fk.github.regelmanuell.logic.RegelService;
import se.fk.rimfrost.framework.oul.jaxrsspec.controllers.generatedsource.OulDoneControllerApi;

@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
// TODO: Change path to something relevant for the rule
@Path("/regel/manuell")
public class RegelController implements OulDoneControllerApi
{
   @Inject
   RegelService regelService;

   /*
    * TODO: Any rest endpoints needed for rule
    *
    * Any endpoints needed for interacting with a
    * case officer should be added here, such as
    * for reading information about the task at
    * hand or any endpoints needed for updating
    * information.
    */

   @POST
   @Path("/{kundbehovsflodeId}/done")
   @Override
   public void markDone(
         @PathParam("kundbehovsflodeId") UUID kundbehovsflodeId)
   {
      /*
       * TODO: Adapt if needed
       *
       * This REST endpoint is intended to be called when
       * a case officer has completed their work. Any work
       * needed to be done in connection with that could be
       * done here or in the setUppgiftDone method in
       * regelService.
       */
      regelService.setUppgiftDone(kundbehovsflodeId);
   }
}

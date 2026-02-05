package se.fk.github.regelmanuell.presentation.rest;

import jakarta.ws.rs.*;
import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.framework.oul.presentation.rest.OulController;

@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
// TODO: Change path to something relevant for the rule
@Path("/regel/manuell")
public class RegelController extends OulController
{
   /*
    * TODO: Any rest endpoints needed for rule
    *
    * Any endpoints needed for interacting with a
    * case officer should be added here, such as
    * for reading information about the task at
    * hand or any endpoints needed for updating
    * information.
    */
}

package se.fk.github.regel.template.logic.dto;

import org.immutables.value.Value;
import se.fk.rimfrost.framework.regel.logic.dto.UppgiftStatus;
import java.util.UUID;

// TODO Är hela interfacet re-usable. Till ramverket?

@Value.Immutable
public interface UpdateStatusRequest
{
   UUID kundbehovsflodeId();

   UUID uppgiftId();

   UppgiftStatus uppgiftStatus();

}

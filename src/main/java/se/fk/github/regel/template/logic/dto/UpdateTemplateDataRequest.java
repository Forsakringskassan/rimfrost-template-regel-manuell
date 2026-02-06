package se.fk.github.regel.template.logic.dto;

import org.immutables.value.Value;
import java.util.UUID;

// TODO Är hela interfacet re-usable. Till ramverket?

@Value.Immutable
public interface UpdateTemplateDataRequest
{

   UUID kundbehovsflodeId();

   UUID uppgiftId();

}

package se.fk.github.regel.template.logic.dto;

import org.immutables.value.Value;
import java.util.UUID;

@Value.Immutable
public interface GetTemplateDataRequest
{

   UUID kundbehovsflodeId();

}

package se.fk.github.regel.template.logic.entity;

import org.immutables.value.Value;
import java.util.UUID;

@Value.Immutable
public interface TemplateData
{

   UUID kundbehovsflodeId();

   UUID cloudeventId();

   //
   // Ersätt nedanstående med regelns datamängd
   //

   /*
     @Nullable
   UUID uppgiftId();
   
   @Nullable
   UUID utforarId();
   
   OffsetDateTime skapadTs();
   
   @Nullable
   OffsetDateTime utfordTs();
   
   @Nullable
   OffsetDateTime planeradTs();
   
   UppgiftStatus uppgiftStatus();
   
   FSSAinformation fssaInformation();
   
   List<ErsattningData> ersattningar();
   
   List<Underlag> underlag();
   
   */

}

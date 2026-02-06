package se.fk.github.regel.template.logic.dto;

import org.immutables.value.Value;
import java.util.UUID;

@Value.Immutable
public interface GetTemplateDataResponse
{

   UUID kundbehovsflodeId();

   //
   // Ersätt nedanstående med den datamängd som regeln behöver i responsen på GET-operationen
   //

   // TODO: Hur mycket av detta är re-usable och kan definieras i ramverket?

   /*
   
   String fornamn();
   
   String efternamn();
   
   String kon();
   
   String organisationsnummer();
   
   String organisationsnamn();
   
   int arbetstidProcent();
   
   LocalDate anstallningsdag();
   
   @Nullable
   LocalDate sistaAnstallningsdag();
   
   int loneSumma();
   
   LocalDate lonFrom();
   
   @Nullable
   LocalDate lonTom();
   
   List<Ersattning> ersattning();
   
   @Value.Immutable
   interface Ersattning
   {
   
      UUID ersattningsId();
   
      String ersattningsTyp();
   
      int omfattningsProcent();
   
      int belopp();
   
      int berakningsgrund();
   
      @Nullable
      Beslutsutfall beslutsutfall();
   
      LocalDate from();
   
      LocalDate tom();
   
      @Nullable
      String avslagsanledning();
   }
   
   */
}

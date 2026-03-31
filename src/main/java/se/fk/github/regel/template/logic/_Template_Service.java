package se.fk.github.regel.template.logic;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.fk.github.regel.template.storage._Template_CommonDataStorageService;
import se.fk.rimfrost.framework.handlaggning.adapter.HandlaggningAdapter;
import se.fk.rimfrost.framework.handlaggning.model.Handlaggning;
import se.fk.rimfrost.framework.regel.Utfall;
import se.fk.rimfrost.framework.regel.integration.config.RegelConfigProvider;
import se.fk.rimfrost.framework.regel.logic.config.RegelConfig;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellServiceBase;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellServiceInterface;
import java.util.UUID;

@ApplicationScoped
@Startup
public class _Template_Service extends RegelManuellServiceBase implements RegelManuellServiceInterface // TODO rename service
{
   @Inject
   _Template_Mapper mapper; // TODO rename

   @Inject
   RegelConfigProvider regelConfigProvider;

   @Inject
   HandlaggningAdapter handlaggningAdapter;

   @Inject
   _Template_CommonDataStorageService dataStorage; // TODO rename

   private RegelConfig regelConfig;

   @PostConstruct
   public void init()
   {
      regelConfig = regelConfigProvider.getConfig();
   }

   public Get_Template_DataResponse readData(Handlaggning handlaggning) // TODO klass från regelns OpenAPI-spec
    {
        // TODO to be implemented: regelns logik för att bygga upp getData-response
        mapper.toGetDataResponse(handlaggning,
                // TODO regel-specifik data
        )
    }

   @Override
    public Utfall updateData(Handlaggning handlaggning,
                             Patch_Template_DataRequest // TODO klass från regelns OpenAPI-spec
                             ) {

        // TODO to be implemented: uppdatera data från Patch request
    }

   @Override
   public void done(UUID handlaggningId)
   {

      // TODO to be implemented: ev. uppstädning av data samt uppdatering av utfall

      sendRegelResponse(handlaggningId, utfall);

   }

}

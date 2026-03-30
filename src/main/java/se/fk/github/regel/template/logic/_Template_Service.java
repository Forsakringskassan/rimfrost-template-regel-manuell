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
import se.fk.rimfrost.framework.regel.logic.RegelMapper;
import se.fk.rimfrost.framework.regel.logic.config.RegelConfig;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellServiceBase;
import se.fk.rimfrost.framework.regel.manuell.logic.RegelManuellServiceInterface;
import java.util.UUID;

@ApplicationScoped
@Startup
public class _Template_Service extends RegelManuellServiceBase implements RegelManuellServiceInterface
{ // TODO rename service
   @Inject
   _Template_Mapper _Template_mapper; // TODO rename

   @Inject
   RegelMapper regelMapper;

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

   @Override
    public Utfall updateData(Handlaggning handlaggning,
                             Patch_Template_DataRequest // TODO klass från regelns OpenAPI-spec
                             ) {

        // TODO to be implemented
    }

   @Override
   public void done(UUID handlaggningId)
   {

      // TODO to be implemented

      sendRegelResponse(handlaggningId, utfall);

   }

   public Get_Template_DataResponse readData(Handlaggning handlaggning) // TODO klass från regelns OpenAPI-spec
   {
      // TODO to be implemented
   }

}

package se.fk.github.regel.template.storage;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import se.fk.github.regel.template.storage.entity.TemplateDataStorage;
import se.fk.rimfrost.framework.storage.DataStorageProvider;

@ApplicationScoped
public class TemplateDataStorageProvider implements DataStorageProvider<TemplateDataStorage>
{
   private TemplateDataStorage templateDataStorage;

   @PostConstruct
   public void init()
   {
      templateDataStorage = new TemplateDataStorage();
   }

   @Override
   public TemplateDataStorage getDataStorage()
   {
      return templateDataStorage;
   }
}

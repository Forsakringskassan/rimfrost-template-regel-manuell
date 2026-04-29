package se.fk.github.regeltemplate.storage;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.framework.regel.manuell.storage.ManuellRegelCommonDataStorage;
import se.fk.rimfrost.framework.regel.manuell.storage.entity.ManuellRegelCommonData;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class RegelTemplateCommonDataStorageService implements ManuellRegelCommonDataStorage
{
   private final Map<UUID, ManuellRegelCommonData> storage = new ConcurrentHashMap<>();

   @Override
   public ManuellRegelCommonData getManuellRegelCommonData(UUID handlaggningId)
   {
      // TODO to be implemented
      return storage.get(handlaggningId);
   }

   @Override
   public void setManuellRegelCommonData(UUID handlaggningId, ManuellRegelCommonData manuellRegelCommonData)
   {
      // TODO to be implemented
      storage.put(handlaggningId, manuellRegelCommonData);
   }

   @Override
   public void deleteManuellRegelCommonData(UUID handlaggningId)
   {
      // TODO to be implemented
      storage.remove(handlaggningId);
   }

}

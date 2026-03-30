package se.fk.github.regel.template.storage;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.framework.regel.manuell.storage.ManuellRegelCommonDataStorage;
import se.fk.rimfrost.framework.regel.manuell.storage.entity.ManuellRegelCommonData;

import java.util.UUID;

@ApplicationScoped
public class _Template_CommonDataStorageService implements ManuellRegelCommonDataStorage
{

   @Override
   public ManuellRegelCommonData getManuellRegelCommonData(UUID handlaggningId)
   {
      // TODO to be implemented
   }

   @Override
   public void setManuellRegelCommonData(UUID handlaggningId, ManuellRegelCommonData manuellRegelCommonData)
   {
      // TODO to be implemented
   }

   @Override
   public void deleteManuellRegelCommonData(UUID handlaggningId)
   {
      // TODO to be implemented
   }

}

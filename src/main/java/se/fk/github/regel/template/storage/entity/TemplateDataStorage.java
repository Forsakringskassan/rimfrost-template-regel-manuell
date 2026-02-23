package se.fk.github.regel.template.storage.entity;

import se.fk.rimfrost.framework.regel.manuell.storage.entity.RegelManuellDataStorage;

public class TemplateDataStorage extends RegelManuellDataStorage
{
   /*
    * Ersätt med regel-specifik data som behöver lagras beständigt
    * under regelns exekvering, t.ex. data som behöver vara kvar
    * mellan två REST anrop.
    *
    * Se https://docs.eclipsestore.io/manual/storage/root-instances.html
    * för bakgrund till denna klass och detaljer att ha i åtanke. Notera
    * att registrering av klassen med eclipse store sköts automatiskt av
    * ramverket via TemplateDataStorageProvider.
    */
}

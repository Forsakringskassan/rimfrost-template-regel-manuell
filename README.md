# Mallproject for Rimfrost manuell regel

Det här är ett mall projekt för att skapa en regel i Rimfrost-projektet.

En regel är mikrotjänst baserad på [Quarkus](https://quarkus.io/) och [Kogito](https://kogito.kie.org/)
för att producera ett beslut baserat på olika parametrar som antingen är givna eller samlas in under körning.

Denna mall lämpar sig för manuella regler som kräver interaktion 
med handläggare för att producerar ett beslut.

För regler som inte kräver interaktion med handläggare, se [template
projektet för maskinell regel](https://github.com/Forsakringskassan/rimfrost-template-regel-maskinell/).

## Minimum konfiguration av utvecklingsmiljö

Projektet förväntar sig att jdk (java version 21 eller högre), 
docker och maven är installerat på systemet samt att 
miljövariablerna **GITHUB_ACTOR** och **GITHUB_TOKEN** är 
konfigurerade.

Notera att det GITHUB token som används förväntas ha repo access 
konfigurerad för att kunna hämta vissa projekt beroenden. 

## TODOS

Projektet innehåller ett antal TODO kommentarer som beskriver konfiguration som bör ändras
och platser där logik bör fyllas i för att skapa en fungerande regel. Se t.ex. src/main/resources/application.properties
och src/main/java/se.fk.github.regel.template/_Template_Service.java för exempel på dessa.

## Ersätt Template i mallarna

Genomgående: Byt ut _Template_ mot namnet på regeln.

t.ex. om regelns namn är _Bekräfta beslut_:
```
package se.fk.github.template -> se.fk.github.bekraftabeslut
GetTemplateDataRequest -> GetBekraftaBeslutDataRequest
```

## Bygg projektet

`./mvnw -s settings.xml clean compile`.

## Bygg och testa projektet

`./mvnw -s settings.xml clean verify`.

## Bygg docker image för lokal testning

`./mvnw -s settings.xml clean package`

## Github workflows

Två github workflows är inkluderade i projektet, maven-ci och maven-release.

maven-release skapar som del av sitt flöde en docker image.
Den publiceras till försäkringskassans [repository](https://github.com/Forsakringskassan/repository).

## Exempel implementation
Se [rimfrost-regel-rtf-manuell](https://github.com/Forsakringskassan/rimfrost-regel-rtf-manuell) för en färdig implementation av en manuell regel.

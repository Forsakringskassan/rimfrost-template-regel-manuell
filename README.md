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

## Projektstruktur

Källkoden är uppdelad i en lagerarkitektur:

```
src/main/java/se/fk/github/regeltemplate/
├── logic/          Affärslogik – RegelTemplateService, RegelTemplateMapper, RegelTemplateMiddlewareServiceImpl
├── presentation/   REST-kontroller – RegelTemplateController (ärver RegelManuellController)
└── storage/        Datapersistens – RegelTemplateCommonDataStorageService
```

Konfigurationsfiler finns under `src/main/resources/`:

| Fil                      | Syfte                                                                  |
|--------------------------|------------------------------------------------------------------------|
| `application.properties` | Quarkus- och Kafka-konfiguration (topics, container image-namn m.m.)   |
| `config.yaml`            | Regelspecifik metadata: T.ex. uppgift, specifikation, regel och lagrum |

Tjänsten kommunicerar asynkront via Kafka. Ämnen konfigureras i `application.properties` och ska göras unika per regel.

## Konfiguration av config.yaml

Filen `src/main/resources/config.yaml` innehåller regelns metadata och måste anpassas:

- **`uppgift.path`** – Ska matcha `@Path`-annotationen i `RegelTemplateController`.
- **`specifikation`** – Namn, uppgiftsbeskrivning och roll för handläggaren.
- **`regel`** – Namn och beskrivning av den specifika regeln.
- **`lagrum`** – Lagstiftningsreferens (författning, kapitel, paragraf m.m.).
- **`utokadUppgiftsbeskrivning`** – Utökad beskrivning som visas i handläggargränssnittet.

## TODOS

Projektet innehåller ett antal TODO-kommentarer som beskriver konfiguration som bör ändras
och platser där logik bör fyllas i. De viktigaste ställena är:

- `src/main/resources/application.properties` – Kafka-topics och container image-namn.
- `src/main/resources/config.yaml` – Regelmetadata och lagrum.
- `src/main/java/.../logic/RegelTemplateService.java` – Implementera `readData`, `updateData` och `done`.
- `src/main/java/.../presentation/rest/RegelTemplateController.java` – Uppdatera `@Path`.

## Ersätt Template i mallarna

Genomgående: Byt ut `RegelTemplate` mot namnet på regeln.

t.ex. om regelns namn är _Bekräfta beslut_:
```
package se.fk.github.regeltemplate -> se.fk.github.bekraftabeslut
RegelTemplateService       -> BekraftaBeslutService
RegelTemplateController    -> BekraftaBeslutController
```

## Tester

Projektet innehåller tester under `src/test/java/`:

| Testklass                                        | Syfte                                                   |
|--------------------------------------------------|---------------------------------------------------------|
| `RegelTemplateTest`                              | Regel-specifika tester                                  |
| `RegelTemplateHandlaggningTest`                  | Ramverksdefinierade tester av Handläggning-interaktion  |
| `RegelTemplateOulTest`                           | Ramverksdefinierade tester av operativt uppgiftslager                       |
| `RegelTemplateUtokadUppgiftsbeskrivningTest`     | Verifierar utökad uppgiftsbeskrivning                   |
| `WireMockRegelTemplate`                          | WireMock-konfiguration för stubbar mot externa tjänster |

## Bygg projektet

`./mvnw -s settings.xml clean compile`

## Bygg och testa projektet

`./mvnw -s settings.xml clean verify`

## Bygg docker image för lokal testning

`./mvnw -s settings.xml clean package`

## Github workflows

Två github workflows är inkluderade i projektet, maven-ci och maven-release.

maven-release skapar som del av sitt flöde en docker image.
Den publiceras till försäkringskassans [repository](https://github.com/Forsakringskassan/repository).

## Exempel implementation
Se [rimfrost-regel-rtf-manuell](https://github.com/Forsakringskassan/rimfrost-regel-rtf-manuell) för en färdig implementation av en manuell regel.

# Proovitöö

## Eesmärgi püstitus

Luua tehniline lahendus, mis E-ITS portaali RestAPI kaudu loeb välja meetmete kataloogi põhi-, standard- ja kõrgmeetmed ja esitab tulemuse ühe HTML tabelina veergudega „tüüp“, „tunnus“, „kirjeldus“.

## Lahenduse kirjeldus

Olemas olevast välisressursist laetakse alla andmestik ja kuvatakse kuvatakse agregeeritud kujul. 

### Välis resurss

* E-ITS - Eesti infoturbestandardi portaal
* https://eits.ria.ee/swagger-ui/index.html - REST API teenuse dokumentatsioon. Lahenduses kasutatud meetodid:
  * **GET api/2/catalog/** - tagastab loetelu kataloogide versioonidest
  * **GET api/2/catalog/{version}** - tagastab konkreetse kataloogi sisu. Näidis vastus [catalog-2023.json](/docs/eits-api-outputs/catalog-2023.json)
  * **GET api/2/article/{version}** - tagastab konkreetse ressursi versiooni artiklide loetelu. Näidis vastus [article-2023.json](/docs/eits-api-outputs/article-2023.json)

#### Teatud probleemid

Dokumentastioonis kirjeldatud käitumine ei vasta tõele.

##### GET api/2/catalog/{version}
Kirjelduse järgi meetod peaks tagastama kogu kataloogi sisu puu kujul. OpenAPI kirjeldusest on näha, et teenus tagastab kogu andmestiku kuni meetmed sisuni (MeasureDetailDto.body):

![api/2/catalog/{version}](/docs/assets/catalog_schema_expected.png)

Kuid tagastav andmestik ulatub ainult protsessi moodulahi ja andmestruktuur on erinev ka:

![api/2/catalog/{version}](/docs/assets/catalog_schema_actual.png)

 * **CatalogModuleGroupDto** lisa väli **moduleSubgroups**
 * **CatalogModuleDto** puudub väli **measureDetails**

#### Lahenemine

Esialgne plaan oli kasutada kataloogi puu struktuuri teenuse (api/2/catalog/{version}), et sealt välja lugeda meetmete nimekirja ja kuvada seda tabeli kujul. Kuid teenuse dokumentatsioonis kirjeldatud käitumine ei vasta tõele. Selle probleemi leevendamiseks võeti kasutusele teise teenuse mis tagastab portaali artiklite loetelu (api/2/article/{version}) puu kujul. Iga kataloogi mooduli koodi järgi otsiti artiklite hulgas vastava artikli: 

```
 {
        "title": "ISMS.1: Turbehaldus",
        ...
        "child_objects": [
          ...
          {
            "title": "3 Meetmed",
            ...
            "child_objects": [
              {
                "title": "3.1 Elutsükkel",
                ...
              },
              {
                "title": "3.2 Põhimeetmed",
                ...
                "child_objects": [
                  {
                    "title": "ISMS.1.M1 Infoturbe üldvastutuse võtmine juhtkonnatasemel [organisatsiooni juhtkond]",
                    ...
                    "content": "...",
                    "id": "fc2a0084de0e9b60cd21bcabe44a4e83"
                  },
                  ...
                ]
              },
              {
                "title": "3.3 Standardmeetmed",
                ...
                "child_objects": [
                  {
                    "title": "ISMS.1.M16 Täpsustavate turvapoliitikate väljatöötamine",
                    ...
                    "content": "...",
                  }
                ]
              },
              {
                "title": "3.4 Kõrgmeetmed",
                ...
                "child_objects": [
                  {
                    "title": "ISMS.1.M17 Kindlustuslepingute sõlmimine (A)",
                    "securityCode": [
                      "A"
                    ],
                    "content": "..."
                  }
                ]
              }
            ]
          },
          ...
        ]
      }
```

Leitud artiklis lapse noodides otsiti "3 Meetmed" pealkirjaga ja siis selle noodi lapsenoodite hulgas võeti tüüpi järgi grupeeritud meetmeid. 

Olemas olev lahendus ei ole hea ja töökindel. Artiklite loetelu teenus tagastab andmestiku vabas vormis (JsonNode) ja see võib tulevikus muuta ja see tõttu parsimis loogika enam ei tööta. Kuid proovitöö jaoks lahendaja arvamuse järgi, selline lahendus on aktsepteeritav. 


## Rakendus

### Stack
  * Java 21
  * Spring Boot 3
  * Angular 18 + material

### Projekti Struktuur
Projekt jaguneb mitmeks osaks:

1. **Backend API (`eits.backoffice`)**: REST API, mis on ehitatud Java Spring Boot 3 abil. 
2. **Frontend (`eits.web-ui`)**: Veebi kasutaja liides, mis kasutab REST api-d.


## Alustamine

1. **Klooni repositoorium**:
    ```
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Käivita Docker Compose abil**:
    ```
    docker-compose up --build
    ```

3. **Juurdepääs rakendusele**:
   - **Veebi liides**: [http://localhost:4200](http://localhost:4200)
   - **REST Api**: [http://localhost:8080/swagger](http://localhost:8080/swagger)
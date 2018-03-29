Meta:
@status             Klaar
@sleutelwoorden     Zoekpaden
@regels             ZoekPersoon

Narrative:
Willem Jansen:
- Adelijke Titel: H
- Geslachtsaanduiding: M
- geslachtsnaamstam: Jansen
- Adres afgekorte naam: Agaatstraat
- Huisnummer: 17
- Woonplaatsnam Breda
- LocatietenopzichteVanAdre: AB
- Persoon.Adres.IdentificatiecodeAdresseerbaarObject:0626010010016001
- Geboortedatum: 22-08-1978
-Persoon.Adres.IdentificatiecodeNummeraanduiding:0626200010016001

Scenario 9:
Zoeken op - Persoon.Adres.IdentificatiecodeNummeraanduiding:0626200010016001
          - Geboortedatum: 22-08-1978

Scenario: 1. Willem Jansen zoeken

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Willem_Jansen_Breda_Zoekpaden.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Zoekpaden_Top_20/Requests/Zoek_Persoon_Story_9.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 1 groepen 'persoon'
Then is er voor xpath //brp:samengesteldeNaam[brp:geslachtsnaamstam = 'Jansen'] een node aanwezig in het antwoord bericht
Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2011 Voor een persoon met de Nederlandse nationaliteit wordt uitsluitend deze nationaliteit bijgehouden

Scenario:   Persoon heeft niet de NL nationaliteit en er wordt een niet-NL nationaliteit geregistreerd
            LT: GBNL01C220T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C220T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C220T20-002.xls

When voer een bijhouding uit GBNL01C220T20.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C220T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
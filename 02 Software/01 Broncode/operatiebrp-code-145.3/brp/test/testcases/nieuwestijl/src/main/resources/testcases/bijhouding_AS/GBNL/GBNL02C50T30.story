Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1708 Reden verkrijging moet verwijzen naar geldig stamgegeven

Scenario: 1. DB init
          preconditie


Given de database is aangepast met: update kern.rdnverknlnation set dateindegel = '20160101' where code = '185'
Given maak bijhouding caches leeg

Scenario: 2. peildatum is gelijk aan DEG van stamgegeven.
          LT: GBNL02C50T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C50-002.xls



When voer een bijhouding uit GBNL02C50T30.xml namens partij 'Gemeente BRP 1'



Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C50T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

Scenario: 3. DB reset
          postconditie
Given de database is aangepast met: update kern.rdnverknlnation set dateindegel = NULL where code = '185'
Given maak bijhouding caches leeg
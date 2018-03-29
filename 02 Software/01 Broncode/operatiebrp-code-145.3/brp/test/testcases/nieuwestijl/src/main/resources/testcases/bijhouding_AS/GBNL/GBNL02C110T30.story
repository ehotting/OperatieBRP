Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2408 Registratie identificatienummers van het kind is verplicht als de geautoriseerde partij dezelfde partij is als de gemeente van de 'OUWKIG'.

Scenario:   Zendende partij is ongelijk Bijhoudingspartij OUWKIG maar gelijk Bijhoudingspartij NOUWKIG met Registratie identificatienummers
            LT: GBNL02C110T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C110T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C110T30-002.xls

When voer een bijhouding uit GBNL02C110T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C110T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
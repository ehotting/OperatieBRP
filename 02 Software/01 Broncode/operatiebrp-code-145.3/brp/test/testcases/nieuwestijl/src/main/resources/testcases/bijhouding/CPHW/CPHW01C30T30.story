Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2530 Tijdlijncontrole groepen met aaneengesloten materiële historie

Scenario: 1. DB init
          preconditie

Given de database is aangepast met: insert into kern.rechtsgrond
                                    (
                                        "code",
					"oms",
					"indleidttotstrijdigheid",
				        "dataanvgel"
				    )
				    select '111',
				           'Omschrijving rechtsgrond',
					    true,
					    to_number((to_char(now() - interval '1 day', 'YYYYMMDD')), '99999999')
				    where not exists
				    (
					select id
					from   kern.rechtsgrond
				        where  code='111'
				    )

Given maak bijhouding caches leeg

Scenario:   2. Overtreding gaten overal zonder overlap te laten ontstaan in de tijdslijn.
            LT: CPHW01C30T30

Given alle personen zijn verwijderd

!-- Vulling van de partners
Given enkel initiele vulling uit bestand /LO3PL-CPHW/CPHW01C30T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-CPHW/CPHW01C30T30-002.xls

Then heeft $PERSOON_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Libby'
Then heeft $PARTNER_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen='Piet'

!-- Voltrekking huwelijk
When voer een bijhouding uit CPHW01C30T30a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 557790281 en persoon 959108233 met relatie id 30010002 en betrokkenheid id 30010001
Given pas laatste relatie van soort 1 aan tussen persoon 959108233 en persoon 557790281 met relatie id 30010001 en betrokkenheid id 30010002
And de database is aangepast met: update kern.his_relatie set id = 9999 where id = (select hr.id from kern.his_relatie hr join kern.relatie r on r.id = hr.relatie where r.srt = 1 and hr.dataanv = 20160329)

!-- Voorkomensleutel voor identificatienummers
And de database is aangepast met: update kern.his_persids set id = 9998 where pers in (select id from kern.pers where voornamen='Piet')

Then heeft $RELATIE_ID$ de waarde van de volgende query: select r.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Libby' and r.srt=1
Then heeft $BETROKKENHEID_PERS_ID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Libby' and r.srt=1
Then heeft $BETROKKENHEID_PARTNER_ID$ de waarde van de volgende query: select b.id from kern.betr b join kern.relatie r on r.id = b.relatie join kern.pers p on b.pers = p.id where p.voornamen='Piet' and r.srt=1

Then is in de database de persoon met bsn 557790281 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 959108233 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit CPHW01C30T30b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/CPHW/expected/CPHW01C30T30.xml voor expressie //brp:bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R

Scenario: 3. DB reset
             postconditie

Given de database is aangepast met: delete from kern.actiebron where rechtsgrond in (select id from kern.rechtsgrond where code='111')
Given de database is aangepast met: delete from kern.rechtsgrond where code='111'

Given maak bijhouding caches leeg
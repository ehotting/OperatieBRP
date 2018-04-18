/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van een LO3 Verblijfplaats categorie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3VerblijfplaatsInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0910)
    @Element(name = "gemeenteInschrijving")
    private final Lo3GemeenteCode gemeenteInschrijving;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0920)
    @Element(name = "datumInschrijving")
    private final Lo3Datum datumInschrijving;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1010)
    @Element(name = "functieAdres")
    private final Lo3FunctieAdres functieAdres;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1020)
    @Element(name = "gemeenteDeel")
    private final Lo3String gemeenteDeel;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1030)
    @Element(name = "aanvangAdreshouding")
    private final Lo3Datum aanvangAdreshouding;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1110)
    @Element(name = "straatnaam")
    private final Lo3String straatnaam;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1115)
    @Element(name = "naamOpenbareRuimte")
    private final Lo3String naamOpenbareRuimte;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1120)
    @Element(name = "huisnummer")
    private final Lo3Huisnummer huisnummer;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1130)
    @Element(name = "huisletter")
    private final Lo3Character huisletter;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1140)
    @Element(name = "huisnummertoevoeging")
    private final Lo3String huisnummertoevoeging;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1150)
    @Element(name = "aanduidingHuisnummer")
    private final Lo3AanduidingHuisnummer aanduidingHuisnummer;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1160)
    @Element(name = "postcode")
    private final Lo3String postcode;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1170)
    @Element(name = "woonplaatsnaam")
    private final Lo3String woonplaatsnaam;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1180)
    @Element(name = "identificatiecodeVerblijfplaats")
    private final Lo3String identificatiecodeVerblijfplaats;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1190)
    @Element(name = "identificatiecodeNummeraanduiding")
    private final Lo3String identificatiecodeNummeraanduiding;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1210)
    @Element(name = "locatieBeschrijving")
    private final Lo3String locatieBeschrijving;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1310)
    @Element(name = "landAdresBuitenland")
    private final Lo3LandCode landAdresBuitenland;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1320)
    @Element(name = "vertrekUitNederland")
    private final Lo3Datum vertrekUitNederland;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1330)
    @Element(name = "adresBuitenland1")
    private final Lo3String adresBuitenland1;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1340)
    @Element(name = "adresBuitenland2")
    private final Lo3String adresBuitenland2;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1350)
    @Element(name = "adresBuitenland3")
    private final Lo3String adresBuitenland3;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1410)
    @Element(name = "landVanwaarIngeschreven")
    private final Lo3LandCode landVanwaarIngeschreven;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1420)
    @Element(name = "vestigingInNederland")
    private final Lo3Datum vestigingInNederland;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_7210)
    @Element(name = "aangifteAdreshouding")
    private final Lo3AangifteAdreshouding aangifteAdreshouding;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_7510)
    @Element(name = "indicatieDocument")
    private final Lo3IndicatieDocument indicatieDocument;

    /**
     * Default constructor (alles null).
     */
    public Lo3VerblijfplaatsInhoud() {
        this(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Maakt een Lo3VerblijfplaatsInhoud object.
     * @param gemeenteInschrijving de LO3 Gemeente van inschrijving, mag niet null zijn
     * @param datumInschrijving de LO3 Datum van inschrijving, mag niet null zijn
     * @param functieAdres de LO3 Functie adres, mag null zijn
     * @param gemeenteDeel de LO3 Gemeentedeel, mag null zijn
     * @param aanvangAdreshouding de LO3 Datum aanvang adreshouding, mag null zijn
     * @param straatnaam de LO3 Straatnaam, mag null zijn
     * @param naamOpenbareRuimte de LO3 Naam openbare ruimte, mag null zijn
     * @param huisnummer het LO3 Huisnummer, mag null zijn
     * @param huisletter de LO3 Huisletter, mag null zijn
     * @param huisnummertoevoeging de LO3 Huisnummertoevoeging, mag null zijn
     * @param aanduidingHuisnummer de LO3 Aanduiding bij huisnummer, mag null zijn
     * @param postcode de LO3 Postcode, mag null zijn
     * @param woonplaatsnaam de LO3 Woonplaatsnaam, mag null zijn
     * @param identificatiecodeVerblijfplaats de LO3 Identificatiecode verblijfplaats, mag null zijn
     * @param identificatiecodeNummeraanduiding de LO3 Identificatiecode nummeraanduiding, mag null zijn
     * @param locatieBeschrijving de LO3 Locatiebeschrijving, mag null zijn
     * @param landAdresBuitenland het LO3 Land adres buitenland, mag null zijn
     * @param vertrekUitNederland de LO3 Datum vertrek uit Nederland, mag null zijn
     * @param adresBuitenland1 het LO3 Adres buitenland waarnaar vertrokken 1, mag null zijn
     * @param adresBuitenland2 het LO3 Adres buitenland waarnaar vertrokken 2, mag null zijn
     * @param adresBuitenland3 het LO3 Adres buitenland waarnaar vertrokken 3, mag null zijn
     * @param landVanwaarIngeschreven het LO3 Land vanwaar ingeschreven, mag null zijn
     * @param vestigingInNederland De LO3 Datum vestiging in Nederland, mag null zijn
     * @param aangifteAdreshouding de LO3 Omschrijving van de aangifte adreshouding, mag null zijn
     * @param indicatieDocument de LO3 Indicatie document, mag null zijn
     */
    public Lo3VerblijfplaatsInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "gemeenteInschrijving") final Lo3GemeenteCode gemeenteInschrijving,
        @Element(name = "datumInschrijving") final Lo3Datum datumInschrijving,
        @Element(name = "functieAdres") final Lo3FunctieAdres functieAdres,
        @Element(name = "gemeenteDeel") final Lo3String gemeenteDeel,
        @Element(name = "aanvangAdreshouding") final Lo3Datum aanvangAdreshouding,
        @Element(name = "straatnaam") final Lo3String straatnaam,
        @Element(name = "naamOpenbareRuimte") final Lo3String naamOpenbareRuimte,
        @Element(name = "huisnummer") final Lo3Huisnummer huisnummer,
        @Element(name = "huisletter") final Lo3Character huisletter,
        @Element(name = "huisnummertoevoeging") final Lo3String huisnummertoevoeging,
        @Element(name = "aanduidingHuisnummer") final Lo3AanduidingHuisnummer aanduidingHuisnummer,
        @Element(name = "postcode") final Lo3String postcode,
        @Element(name = "woonplaatsnaam") final Lo3String woonplaatsnaam,
        @Element(name = "identificatiecodeVerblijfplaats") final Lo3String identificatiecodeVerblijfplaats,
        @Element(name = "identificatiecodeNummeraanduiding") final Lo3String identificatiecodeNummeraanduiding,
        @Element(name = "locatieBeschrijving") final Lo3String locatieBeschrijving,
        @Element(name = "landAdresBuitenland") final Lo3LandCode landAdresBuitenland,
        @Element(name = "vertrekUitNederland") final Lo3Datum vertrekUitNederland,
        @Element(name = "adresBuitenland1") final Lo3String adresBuitenland1,
        @Element(name = "adresBuitenland2") final Lo3String adresBuitenland2,
        @Element(name = "adresBuitenland3") final Lo3String adresBuitenland3,
        @Element(name = "landVanwaarIngeschreven") final Lo3LandCode landVanwaarIngeschreven,
        @Element(name = "vestigingInNederland") final Lo3Datum vestigingInNederland,
        @Element(name = "aangifteAdreshouding") final Lo3AangifteAdreshouding aangifteAdreshouding,
        @Element(name = "indicatieDocument") final Lo3IndicatieDocument indicatieDocument) {
        this.gemeenteInschrijving = gemeenteInschrijving;
        this.datumInschrijving = datumInschrijving;
        this.functieAdres = functieAdres;
        this.gemeenteDeel = gemeenteDeel;
        this.aanvangAdreshouding = aanvangAdreshouding;
        this.straatnaam = straatnaam;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.aanduidingHuisnummer = aanduidingHuisnummer;
        this.postcode = postcode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.identificatiecodeVerblijfplaats = identificatiecodeVerblijfplaats;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        this.locatieBeschrijving = locatieBeschrijving;
        this.landAdresBuitenland = landAdresBuitenland;
        this.vertrekUitNederland = vertrekUitNederland;
        this.adresBuitenland1 = adresBuitenland1;
        this.adresBuitenland2 = adresBuitenland2;
        this.adresBuitenland3 = adresBuitenland3;
        this.landVanwaarIngeschreven = landVanwaarIngeschreven;
        this.vestigingInNederland = vestigingInNederland;
        this.aangifteAdreshouding = aangifteAdreshouding;
        this.indicatieDocument = indicatieDocument;
    }

    private Lo3VerblijfplaatsInhoud(final Builder builder) {
        gemeenteInschrijving = builder.gemeenteInschrijving;
        datumInschrijving = builder.datumInschrijving;
        functieAdres = builder.functieAdres;
        gemeenteDeel = builder.gemeenteDeel;
        aanvangAdreshouding = builder.aanvangAdreshouding;
        straatnaam = builder.straatnaam;
        naamOpenbareRuimte = builder.naamOpenbareRuimte;
        huisnummer = builder.huisnummer;
        huisletter = builder.huisletter;
        huisnummertoevoeging = builder.huisnummertoevoeging;
        aanduidingHuisnummer = builder.aanduidingHuisnummer;
        postcode = builder.postcode;
        woonplaatsnaam = builder.woonplaatsnaam;
        identificatiecodeVerblijfplaats = builder.identificatiecodeVerblijfplaats;
        identificatiecodeNummeraanduiding = builder.identificatiecodeNummeraanduiding;
        locatieBeschrijving = builder.locatieBeschrijving;
        landAdresBuitenland = builder.landAdresBuitenland;
        vertrekUitNederland = builder.vertrekUitNederland;
        adresBuitenland1 = builder.adresBuitenland1;
        adresBuitenland2 = builder.adresBuitenland2;
        adresBuitenland3 = builder.adresBuitenland3;
        landVanwaarIngeschreven = builder.landVanwaarIngeschreven;
        vestigingInNederland = builder.vestigingInNederland;
        aangifteAdreshouding = builder.aangifteAdreshouding;
        indicatieDocument = builder.indicatieDocument;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Lo3Validatie.isEenParameterGevuld(
                gemeenteInschrijving,
                datumInschrijving,
                functieAdres,
                gemeenteDeel,
                aanvangAdreshouding,
                straatnaam,
                naamOpenbareRuimte,
                huisnummer,
                huisletter,
                huisnummertoevoeging,
                aanduidingHuisnummer,
                postcode,
                woonplaatsnaam,
                identificatiecodeVerblijfplaats,
                identificatiecodeNummeraanduiding,
                locatieBeschrijving,
                landAdresBuitenland,
                vertrekUitNederland,
                adresBuitenland1,
                adresBuitenland2,
                adresBuitenland3,
                landVanwaarIngeschreven,
                vestigingInNederland,
                aangifteAdreshouding,
                indicatieDocument);
    }

    /**
     * Geef de waarde van gemeente inschrijving van Lo3VerblijfplaatsInhoud.
     * @return de waarde van gemeente inschrijving van Lo3VerblijfplaatsInhoud
     */
    public Lo3GemeenteCode getGemeenteInschrijving() {
        return gemeenteInschrijving;
    }

    /**
     * Geef de waarde van datum inschrijving van Lo3VerblijfplaatsInhoud.
     * @return de waarde van datum inschrijving van Lo3VerblijfplaatsInhoud
     */
    public Lo3Datum getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Geef de waarde van functie adres van Lo3VerblijfplaatsInhoud.
     * @return de waarde van functie adres van Lo3VerblijfplaatsInhoud
     */
    public Lo3FunctieAdres getFunctieAdres() {
        return functieAdres;
    }

    /**
     * Geef de waarde van gemeente deel van Lo3VerblijfplaatsInhoud.
     * @return de waarde van gemeente deel van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getGemeenteDeel() {
        return gemeenteDeel;
    }

    /**
     * Geef de waarde van aanvang adreshouding van Lo3VerblijfplaatsInhoud.
     * @return de waarde van aanvang adreshouding van Lo3VerblijfplaatsInhoud
     */
    public Lo3Datum getAanvangAdreshouding() {
        return aanvangAdreshouding;
    }

    /**
     * Geef de waarde van straatnaam van Lo3VerblijfplaatsInhoud.
     * @return de waarde van straatnaam van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getStraatnaam() {
        return straatnaam;
    }

    /**
     * Geef de waarde van naam openbare ruimte van Lo3VerblijfplaatsInhoud.
     * @return de waarde van naam openbare ruimte van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * Geef de waarde van huisnummer van Lo3VerblijfplaatsInhoud.
     * @return de waarde van huisnummer van Lo3VerblijfplaatsInhoud
     */
    public Lo3Huisnummer getHuisnummer() {
        return huisnummer;
    }

    /**
     * Geef de waarde van huisletter van Lo3VerblijfplaatsInhoud.
     * @return de waarde van huisletter van Lo3VerblijfplaatsInhoud
     */
    public Lo3Character getHuisletter() {
        return huisletter;
    }

    /**
     * Geef de waarde van huisnummertoevoeging van Lo3VerblijfplaatsInhoud.
     * @return de waarde van huisnummertoevoeging van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * Geef de waarde van aanduiding huisnummer van Lo3VerblijfplaatsInhoud.
     * @return de waarde van aanduiding huisnummer van Lo3VerblijfplaatsInhoud
     */
    public Lo3AanduidingHuisnummer getAanduidingHuisnummer() {
        return aanduidingHuisnummer;
    }

    /**
     * Geef de waarde van postcode van Lo3VerblijfplaatsInhoud.
     * @return de waarde van postcode van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getPostcode() {
        return postcode;
    }

    /**
     * Geef de waarde van woonplaatsnaam van Lo3VerblijfplaatsInhoud.
     * @return de waarde van woonplaatsnaam van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * Geef de waarde van identificatiecode verblijfplaats van Lo3VerblijfplaatsInhoud.
     * @return de waarde van identificatiecode verblijfplaats van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getIdentificatiecodeVerblijfplaats() {
        return identificatiecodeVerblijfplaats;
    }

    /**
     * Geef de waarde van identificatiecode nummeraanduiding van Lo3VerblijfplaatsInhoud.
     * @return de waarde van identificatiecode nummeraanduiding van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Geef de waarde van locatie beschrijving van Lo3VerblijfplaatsInhoud.
     * @return de waarde van locatie beschrijving van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getLocatieBeschrijving() {
        return locatieBeschrijving;
    }

    /**
     * Geef de waarde van land adres buitenland van Lo3VerblijfplaatsInhoud.
     * @return de waarde van land adres buitenland van Lo3VerblijfplaatsInhoud
     */
    public Lo3LandCode getLandAdresBuitenland() {
        return landAdresBuitenland;
    }

    /**
     * Geef de waarde van datum vertrek uit nederland van Lo3VerblijfplaatsInhoud.
     * @return de waarde van datum vertrek uit nederland van Lo3VerblijfplaatsInhoud
     */
    public Lo3Datum getDatumVertrekUitNederland() {
        return vertrekUitNederland;
    }

    /**
     * Geef de waarde van adres buitenland1 van Lo3VerblijfplaatsInhoud.
     * @return de waarde van adres buitenland1 van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getAdresBuitenland1() {
        return adresBuitenland1;
    }

    /**
     * Geef de waarde van adres buitenland2 van Lo3VerblijfplaatsInhoud.
     * @return de waarde van adres buitenland2 van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getAdresBuitenland2() {
        return adresBuitenland2;
    }

    /**
     * Geef de waarde van adres buitenland3 van Lo3VerblijfplaatsInhoud.
     * @return de waarde van adres buitenland3 van Lo3VerblijfplaatsInhoud
     */
    public Lo3String getAdresBuitenland3() {
        return adresBuitenland3;
    }

    /**
     * Geef de waarde van land vanwaar ingeschreven van Lo3VerblijfplaatsInhoud.
     * @return de waarde van land vanwaar ingeschreven van Lo3VerblijfplaatsInhoud
     */
    public Lo3LandCode getLandVanwaarIngeschreven() {
        return landVanwaarIngeschreven;
    }

    /**
     * Geef de waarde van vestiging in nederland van Lo3VerblijfplaatsInhoud.
     * @return de waarde van vestiging in nederland van Lo3VerblijfplaatsInhoud
     */
    public Lo3Datum getVestigingInNederland() {
        return vestigingInNederland;
    }

    /**
     * Geef de waarde van aangifte adreshouding van Lo3VerblijfplaatsInhoud.
     * @return de waarde van aangifte adreshouding van Lo3VerblijfplaatsInhoud
     */
    public Lo3AangifteAdreshouding getAangifteAdreshouding() {
        return aangifteAdreshouding;
    }

    /**
     * Geef de waarde van indicatie document van Lo3VerblijfplaatsInhoud.
     * @return de waarde van indicatie document van Lo3VerblijfplaatsInhoud
     */
    public Lo3IndicatieDocument getIndicatieDocument() {
        return indicatieDocument;
    }

    /**
     * Geef de waarde van punt adres van Lo3VerblijfplaatsInhoud.
     * @return de waarde van punt adres van Lo3VerblijfplaatsInhoud
     */
    @Definitie(Definities.DEF021)
    public boolean isPuntAdres() {
        return Lo3String.wrap(".").equals(straatnaam)
                && !Lo3Validatie.isEenParameterGevuld(
                gemeenteDeel,
                naamOpenbareRuimte,
                huisnummer,
                huisletter,
                huisnummertoevoeging,
                aanduidingHuisnummer,
                postcode,
                woonplaatsnaam,
                identificatiecodeNummeraanduiding,
                identificatiecodeVerblijfplaats);
    }

    /**
     * Geef de waarde van nederlands adres van Lo3VerblijfplaatsInhoud.
     * @return de waarde van nederlands adres van Lo3VerblijfplaatsInhoud
     */
    @Definitie(Definities.DEF022)
    public boolean isNederlandsAdres() {
        return Lo3Validatie.isEenParameterGevuld(
                straatnaam,
                gemeenteDeel,
                naamOpenbareRuimte,
                huisnummer,
                huisletter,
                huisnummertoevoeging,
                aanduidingHuisnummer,
                postcode,
                woonplaatsnaam,
                identificatiecodeVerblijfplaats,
                identificatiecodeNummeraanduiding,
                locatieBeschrijving);
    }

    /**
     * Geef de waarde van immigratie van Lo3VerblijfplaatsInhoud.
     * @return de waarde van immigratie van Lo3VerblijfplaatsInhoud
     */
    public boolean isImmigratie() {
        return Lo3Validatie.isEenParameterGevuld(landVanwaarIngeschreven, vestigingInNederland);
    }

    /**
     * Geef de waarde van emigratie van Lo3VerblijfplaatsInhoud.
     * @return de waarde van emigratie van Lo3VerblijfplaatsInhoud
     */
    public boolean isEmigratie() {
        return Lo3Validatie.isEenParameterGevuld(landAdresBuitenland, vertrekUitNederland, adresBuitenland1, adresBuitenland2, adresBuitenland3);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3VerblijfplaatsInhoud)) {
            return false;
        }
        final Lo3VerblijfplaatsInhoud castOther = (Lo3VerblijfplaatsInhoud) other;
        return new EqualsBuilder().append(gemeenteInschrijving, castOther.gemeenteInschrijving)
                .append(datumInschrijving, castOther.datumInschrijving)
                .append(functieAdres, castOther.functieAdres)
                .append(gemeenteDeel, castOther.gemeenteDeel)
                .append(aanvangAdreshouding, castOther.aanvangAdreshouding)
                .append(straatnaam, castOther.straatnaam)
                .append(naamOpenbareRuimte, castOther.naamOpenbareRuimte)
                .append(huisnummer, castOther.huisnummer)
                .append(huisletter, castOther.huisletter)
                .append(huisnummertoevoeging, castOther.huisnummertoevoeging)
                .append(aanduidingHuisnummer, castOther.aanduidingHuisnummer)
                .append(postcode, castOther.postcode)
                .append(woonplaatsnaam, castOther.woonplaatsnaam)
                .append(identificatiecodeVerblijfplaats, castOther.identificatiecodeVerblijfplaats)
                .append(identificatiecodeNummeraanduiding, castOther.identificatiecodeNummeraanduiding)
                .append(locatieBeschrijving, castOther.locatieBeschrijving)
                .append(landAdresBuitenland, castOther.landAdresBuitenland)
                .append(vertrekUitNederland, castOther.vertrekUitNederland)
                .append(adresBuitenland1, castOther.adresBuitenland1)
                .append(adresBuitenland2, castOther.adresBuitenland2)
                .append(adresBuitenland3, castOther.adresBuitenland3)
                .append(landVanwaarIngeschreven, castOther.landVanwaarIngeschreven)
                .append(vestigingInNederland, castOther.vestigingInNederland)
                .append(aangifteAdreshouding, castOther.aangifteAdreshouding)
                .append(indicatieDocument, castOther.indicatieDocument)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeenteInschrijving)
                .append(datumInschrijving)
                .append(functieAdres)
                .append(gemeenteDeel)
                .append(aanvangAdreshouding)
                .append(straatnaam)
                .append(naamOpenbareRuimte)
                .append(huisnummer)
                .append(huisletter)
                .append(huisnummertoevoeging)
                .append(aanduidingHuisnummer)
                .append(postcode)
                .append(woonplaatsnaam)
                .append(identificatiecodeVerblijfplaats)
                .append(identificatiecodeNummeraanduiding)
                .append(locatieBeschrijving)
                .append(landAdresBuitenland)
                .append(vertrekUitNederland)
                .append(adresBuitenland1)
                .append(adresBuitenland2)
                .append(adresBuitenland3)
                .append(landVanwaarIngeschreven)
                .append(vestigingInNederland)
                .append(aangifteAdreshouding)
                .append(indicatieDocument)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("gemeenteInschrijving", gemeenteInschrijving)
                .append("datumInschrijving", datumInschrijving)
                .append("functieAdres", functieAdres)
                .append("gemeenteDeel", gemeenteDeel)
                .append("aanvangAdreshouding", aanvangAdreshouding)
                .append("straatnaam", straatnaam)
                .append("naamOpenbareRuimte", naamOpenbareRuimte)
                .append("huisnummer", huisnummer)
                .append("huisletter", huisletter)
                .append("huisnummertoevoeging", huisnummertoevoeging)
                .append("aanduidingHuisnummer", aanduidingHuisnummer)
                .append("postcode", postcode)
                .append("woonplaatsnaam", woonplaatsnaam)
                .append("identificatiecodeVerblijfplaats", identificatiecodeVerblijfplaats)
                .append("identificatiecodeNummeraanduiding", identificatiecodeNummeraanduiding)
                .append("locatieBeschrijving", locatieBeschrijving)
                .append("landAdresBuitenland", landAdresBuitenland)
                .append("vertrekUitNederland", vertrekUitNederland)
                .append("adresBuitenland1", adresBuitenland1)
                .append("adresBuitenland2", adresBuitenland2)
                .append("adresBuitenland3", adresBuitenland3)
                .append("landVanwaarIngeschreven", landVanwaarIngeschreven)
                .append("vestigingInNederland", vestigingInNederland)
                .append("aangifteAdreshouding", aangifteAdreshouding)
                .append("indicatieDocument", indicatieDocument)
                .toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3GemeenteCode gemeenteInschrijving;
        private Lo3Datum datumInschrijving;
        private Lo3FunctieAdres functieAdres;
        private Lo3String gemeenteDeel;
        private Lo3Datum aanvangAdreshouding;
        private Lo3String straatnaam;
        private Lo3String naamOpenbareRuimte;
        private Lo3Huisnummer huisnummer;
        private Lo3Character huisletter;
        private Lo3String huisnummertoevoeging;
        private Lo3AanduidingHuisnummer aanduidingHuisnummer;
        private Lo3String postcode;
        private Lo3String woonplaatsnaam;
        private Lo3String identificatiecodeVerblijfplaats;
        private Lo3String identificatiecodeNummeraanduiding;
        private Lo3String locatieBeschrijving;
        private Lo3LandCode landAdresBuitenland;
        private Lo3Datum vertrekUitNederland;
        private Lo3String adresBuitenland1;
        private Lo3String adresBuitenland2;
        private Lo3String adresBuitenland3;
        private Lo3LandCode landVanwaarIngeschreven;
        private Lo3Datum vestigingInNederland;
        private Lo3AangifteAdreshouding aangifteAdreshouding;
        private Lo3IndicatieDocument indicatieDocument;

        /**
         * Maakt een lege builder.
         */
        public Builder() {
            // Maakt een lege builder voor de Lo3VerblijfplaatsInhoud.
        }

        /**
         * Maakt een initieel gevulde builder.
         * @param inhoud initiele inhoud
         */
        public Builder(final Lo3VerblijfplaatsInhoud inhoud) {
            gemeenteInschrijving = inhoud.gemeenteInschrijving;
            datumInschrijving = inhoud.datumInschrijving;
            functieAdres = inhoud.functieAdres;
            gemeenteDeel = inhoud.gemeenteDeel;
            aanvangAdreshouding = inhoud.aanvangAdreshouding;
            straatnaam = inhoud.straatnaam;
            naamOpenbareRuimte = inhoud.naamOpenbareRuimte;
            huisnummer = inhoud.huisnummer;
            huisletter = inhoud.huisletter;
            huisnummertoevoeging = inhoud.huisnummertoevoeging;
            aanduidingHuisnummer = inhoud.aanduidingHuisnummer;
            postcode = inhoud.postcode;
            woonplaatsnaam = inhoud.woonplaatsnaam;
            identificatiecodeVerblijfplaats = inhoud.identificatiecodeVerblijfplaats;
            identificatiecodeNummeraanduiding = inhoud.identificatiecodeNummeraanduiding;
            locatieBeschrijving = inhoud.locatieBeschrijving;
            landAdresBuitenland = inhoud.landAdresBuitenland;
            vertrekUitNederland = inhoud.vertrekUitNederland;
            adresBuitenland1 = inhoud.adresBuitenland1;
            adresBuitenland2 = inhoud.adresBuitenland2;
            adresBuitenland3 = inhoud.adresBuitenland3;
            landVanwaarIngeschreven = inhoud.landVanwaarIngeschreven;
            vestigingInNederland = inhoud.vestigingInNederland;
            aangifteAdreshouding = inhoud.aangifteAdreshouding;
            indicatieDocument = inhoud.indicatieDocument;
        }

        /**
         * Build.
         * @return inhoud
         */
        public Lo3VerblijfplaatsInhoud build() {
            return new Lo3VerblijfplaatsInhoud(this);
        }

        /**
         * @param param the gemeenteInschrijving to set
         * @return builder object
         */
        public Builder gemeenteInschrijving(final Lo3GemeenteCode param) {
            gemeenteInschrijving = param;
            return this;
        }

        /**
         * @param param the datumInschrijving to set
         * @return builder object
         */
        public Builder datumInschrijving(final Lo3Datum param) {
            datumInschrijving = param;
            return this;
        }

        /**
         * @param param the functieAdres to set
         * @return builder object
         */
        public Builder functieAdres(final Lo3FunctieAdres param) {
            functieAdres = param;
            return this;
        }

        /**
         * @param param the gemeenteDeel to set
         * @return builder object
         */
        public Builder gemeenteDeel(final Lo3String param) {
            gemeenteDeel = param;
            return this;
        }

        /**
         * @param param the aanvangAdreshouding to set
         * @return builder object
         */
        public Builder aanvangAdreshouding(final Lo3Datum param) {
            aanvangAdreshouding = param;
            return this;
        }

        /**
         * @param param the straatnaam to set
         * @return builder object
         */
        public Builder straatnaam(final Lo3String param) {
            straatnaam = param;
            return this;
        }

        /**
         * @param param the naamOpenbareRuimte to set
         * @return builder object
         */
        public Builder naamOpenbareRuimte(final Lo3String param) {
            naamOpenbareRuimte = param;
            return this;
        }

        /**
         * @param param the huisnummer to set
         * @return builder object
         */
        public Builder huisnummer(final Lo3Huisnummer param) {
            huisnummer = param;
            return this;
        }

        /**
         * @param param the huisletter to set
         * @return builder object
         */
        public Builder huisletter(final Lo3Character param) {
            huisletter = param;
            return this;
        }

        /**
         * @param param the huisnummertoevoeging to set
         * @return builder object
         */
        public Builder huisnummertoevoeging(final Lo3String param) {
            huisnummertoevoeging = param;
            return this;
        }

        /**
         * @param param the aanduidingHuisnummer to set
         * @return builder object
         */
        public Builder aanduidingHuisnummer(final Lo3AanduidingHuisnummer param) {
            aanduidingHuisnummer = param;
            return this;
        }

        /**
         * @param param the postcode to set
         * @return builder object
         */
        public Builder postcode(final Lo3String param) {
            postcode = param;
            return this;
        }

        /**
         * @param param the woonplaatsnaam to set
         * @return builder object
         */
        public Builder woonplaatsnaam(final Lo3String param) {
            woonplaatsnaam = param;
            return this;
        }

        /**
         * @param param the identificatiecodeVerblijfplaats to set
         * @return builder object
         */
        public Builder identificatiecodeVerblijfplaats(final Lo3String param) {
            identificatiecodeVerblijfplaats = param;
            return this;
        }

        /**
         * @param param the identificatiecodeNummeraanduiding to set
         * @return builder object
         */
        public Builder identificatiecodeNummeraanduiding(final Lo3String param) {
            identificatiecodeNummeraanduiding = param;
            return this;
        }

        /**
         * @param param the locatieBeschrijving to set
         * @return builder object
         */
        public Builder locatieBeschrijving(final Lo3String param) {
            locatieBeschrijving = param;
            return this;
        }

        /**
         * @param param the landAdresBuitenland to set
         * @return builder object
         */
        public Builder landAdresBuitenland(final Lo3LandCode param) {
            landAdresBuitenland = param;
            return this;
        }

        /**
         * @param param the vertrekUitNederland to set
         * @return builder object
         */
        public Builder vertrekUitNederland(final Lo3Datum param) {
            vertrekUitNederland = param;
            return this;
        }

        /**
         * @param param the adresBuitenland1 to set
         * @return builder object
         */
        public Builder adresBuitenland1(final Lo3String param) {
            adresBuitenland1 = param;
            return this;
        }

        /**
         * @param param the adresBuitenland2 to set
         * @return builder object
         */
        public Builder adresBuitenland2(final Lo3String param) {
            adresBuitenland2 = param;
            return this;
        }

        /**
         * @param param the adresBuitenland3 to set
         * @return builder object
         */
        public Builder adresBuitenland3(final Lo3String param) {
            adresBuitenland3 = param;
            return this;
        }

        /**
         * @param param the landVanwaarIngeschreven to set
         * @return builder object
         */
        public Builder landVanwaarIngeschreven(final Lo3LandCode param) {
            landVanwaarIngeschreven = param;
            return this;
        }

        /**
         * @param param the vestigingInNederland to set
         * @return builder object
         */
        public Builder vestigingInNederland(final Lo3Datum param) {
            vestigingInNederland = param;
            return this;
        }

        /**
         * @param param the aangifteAdreshouding to set
         * @return builder object
         */
        public Builder aangifteAdreshouding(final Lo3AangifteAdreshouding param) {
            aangifteAdreshouding = param;
            return this;
        }

        /**
         * @param param the indicatieDocument to set
         * @return builder object
         */
        public Builder indicatieDocument(final Lo3IndicatieDocument param) {
            indicatieDocument = param;
            return this;
        }

        /**
         * Reset de velden voor het adres-gedeelte van BRP -&lt; LO3.
         */
        public void resetAdresVelden() {
            functieAdres = null;
            aangifteAdreshouding = null;
            aanvangAdreshouding = null;
            identificatiecodeVerblijfplaats = null;
            identificatiecodeNummeraanduiding = null;
            naamOpenbareRuimte = null;
            straatnaam = null;
            gemeenteDeel = null;
            huisnummer = null;
            huisletter = null;
            huisnummertoevoeging = null;
            postcode = null;
            woonplaatsnaam = null;
            aanduidingHuisnummer = null;
            locatieBeschrijving = null;
            adresBuitenland1 = null;
            adresBuitenland2 = null;
            adresBuitenland3 = null;
            landAdresBuitenland = null;
            vertrekUitNederland = null;
            landVanwaarIngeschreven = null;
            vestigingInNederland = null;
            indicatieDocument = null;
        }

        /**
         * Reset de velden voor het immigratie-gedeelte van BRP -&lt; LO3.
         */
        public void resetImmigratieVelden() {
            landVanwaarIngeschreven = null;
            vestigingInNederland = null;
        }

        /**
         * Reset de velden voor het emigratie-gedeelte van BRP -&lt; LO3.
         */
        public void resetEmigratieVelden() {
            vertrekUitNederland = null;
            landAdresBuitenland = null;
            adresBuitenland1 = null;
            adresBuitenland2 = null;
            adresBuitenland3 = null;
        }
    }
}

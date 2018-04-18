/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;

public class Lo3AutorisatieTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException {
        final Lo3Autorisatie subject = maakAutorisatie("000001");
        final Lo3Autorisatie equal = maakAutorisatie("000001");
        final Lo3Autorisatie different = maakAutorisatie("000002");

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(subject, equal, different);
    }

    @Test
    public void testAutorisatieStapel() {
        final Lo3Autorisatie autorisatie = maakAutorisatie("000001");
        assertEquals(1, autorisatie.getAutorisatieStapel().size());
        assertEquals("000001", autorisatie.getAutorisatieStapel().get(0).getInhoud().getAfnemersindicatie());
    }

    @Test
    public void testAfnemersindicatie() {
        final Lo3Autorisatie autorisatie = maakAutorisatie("000001");
        assertEquals("000001", autorisatie.getAfnemersindicatie());
    }

    @Test
    public void testAfnemersindicatieGeenStapel() {
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(null);
        assertEquals(null, autorisatie.getAfnemersindicatie());
    }

    @Test
    public void testAfnemersindicatieGeenAfnemersindicatie() {
        final Lo3Autorisatie autorisatie = maakAutorisatie(null);
        assertEquals(null, autorisatie.getAfnemersindicatie());
    }

    private Lo3Autorisatie maakAutorisatie(final String afnemersindicatie) {

        return new Lo3Autorisatie(maakStapel(afnemersindicatie));
    }

    private Lo3Stapel<Lo3AutorisatieInhoud> maakStapel(final String afnemersindicatie) {
        final Lo3AutorisatieInhoud inhoud = new Lo3AutorisatieInhoud();
        inhoud.setAfnemersindicatie(afnemersindicatie);
        inhoud.setDatumIngang(new Lo3Datum(1990_01_01));

        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(1990_01_01), new Lo3Datum(1990_01_01));
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, null);
        return new Lo3Stapel<>(Collections.singletonList(categorie));
    }
}

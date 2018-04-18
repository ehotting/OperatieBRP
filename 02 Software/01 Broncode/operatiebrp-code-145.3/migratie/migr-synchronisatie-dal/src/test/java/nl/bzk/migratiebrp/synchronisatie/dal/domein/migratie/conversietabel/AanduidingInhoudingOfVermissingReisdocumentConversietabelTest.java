/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class AanduidingInhoudingOfVermissingReisdocumentConversietabelTest extends AbstractDatabaseTest {

    private static final Lo3AanduidingInhoudingVermissingNederlandsReisdocument LO3_INGEHOUDEN =
            Lo3AanduidingInhoudingVermissingNederlandsReisdocumentEnum.INGEHOUDEN.asElement();
    private static final BrpAanduidingInhoudingOfVermissingReisdocumentCode BRP_REDEN_ONTBREKEN =
            new BrpAanduidingInhoudingOfVermissingReisdocumentCode('I');

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createAanduidingInhoudingVermissingReisdocumentConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarBrp() {
        final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpResultaat = conversietabel.converteerNaarBrp(LO3_INGEHOUDEN);
        assertEquals(BRP_REDEN_ONTBREKEN, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarLo3() {
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocument lo3Resultaat = conversietabel.converteerNaarLo3(BRP_REDEN_ONTBREKEN);
        assertEquals(LO3_INGEHOUDEN, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LijstControleGeenBurgerServicenummerTest {

    private LijstControleGeenBurgerServicenummer subject = new LijstControleGeenBurgerServicenummer();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(null));
        Assert.assertTrue(subject.controleer(Collections.<BrpPersoonslijst>emptyList()));
        Assert.assertFalse(subject.controleer(Arrays.asList(maakPl(1))));
        Assert.assertFalse(subject.controleer(Arrays.asList(maakPl(1), maakPl(2))));
    }

    BrpPersoonslijst maakPl(final long burgerServicenummer) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        return builder.build();
    }
}

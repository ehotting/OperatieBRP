/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor GeldigheidLandOfGebied
 */
@RunWith(MockitoJUnitRunner.class)
public class GeldigheidLandOfGebiedTest {

    @Mock
    private ReadonlyRepository<LandOfGebied, Number> repository;

    @InjectMocks
    private GeldigheidLandOfGebied subject;

    @Test
    public void test() throws Exception {
        Assert.assertTrue(subject.supports(LandOfGebied.class));
    }
}
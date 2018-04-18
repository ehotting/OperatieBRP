/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.PredicateBuilderSpecification;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonRepository;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class}, loader = AnnotationConfigContextLoader.class)
@Data(resources = {"classpath:/data/testdata.xml"}, dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_KERN)
public class PersoonRepositoryTest extends AbstractDatabaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonRepository subject;

    @Test
    public void findAll() throws JsonProcessingException {
        final Pageable pageable = new PageRequest(0, 10);
        final Page<Persoon> page = subject.findAll(null, pageable);
        Assert.assertNotNull(page);
        Assert.assertEquals(22L, page.getTotalElements());
    }

    @Test
    public void findAllOnBsn() throws JsonProcessingException {
        final PredicateBuilderSpecification zoekCriteria = new PredicateBuilderSpecification();
        zoekCriteria.addPredicateBuilder(new EqualPredicateBuilderFactory("burgerservicenummer").getPredicateBuilder("123456789"));

        final Page<Persoon> page = subject.findAll(zoekCriteria, new PageRequest(0, 10));
        LOGGER.info("PAGE: "+ page);
        Assert.assertNotNull(page);
        Assert.assertEquals(1L, page.getTotalElements());
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.impl;

import nl.bzk.migratiebrp.bericht.model.Bericht;

/**
 * Speciaal bericht om aan te geven dat het ontvangen bericht onbekend is.
 */
public abstract class AbstractOnbekendBericht extends AbstractMeldingBericht implements Bericht {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param bericht ongeparsed bericht
     * @param melding melding
     */
    protected AbstractOnbekendBericht(final String bericht, final String melding) {
        super("OnbekendBericht", bericht, melding);
    }

}

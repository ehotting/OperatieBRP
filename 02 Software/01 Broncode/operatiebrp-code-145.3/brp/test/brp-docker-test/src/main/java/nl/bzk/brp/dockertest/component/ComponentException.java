/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import nl.bzk.brp.test.common.TestclientExceptie;

/**
 */
public class ComponentException extends TestclientExceptie {
    public ComponentException(final Exception e) {
        super(e);
    }

    public ComponentException(final String msg) {
        super(msg);
    }

    public ComponentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.exceptions;

import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Exceptie in het geval er een ongeldige toevallige gebeurtenis wordt geconverteerd.
 */
@Root
public class OngeldigeToevalligeGebeurtenisException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public OngeldigeToevalligeGebeurtenisException() {
        super();
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof OngeldigeToevalligeGebeurtenisException)) {
            return false;
        }
        return new EqualsBuilder().isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().toHashCode();
    }
}

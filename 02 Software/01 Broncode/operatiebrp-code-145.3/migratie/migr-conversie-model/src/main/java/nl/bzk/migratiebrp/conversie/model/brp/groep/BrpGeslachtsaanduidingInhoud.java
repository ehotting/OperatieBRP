/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de BRP inhoud van de groep geslachtsaanduiding.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpGeslachtsaanduidingInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "geslachtsaanduidingCode", required = false)
    private final BrpGeslachtsaanduidingCode geslachtsaanduidingCode;

    /**
     * Maakt een BrpGeslachtsaanduidingInhoud object.
     * @param geslachtsaanduidingCode de BRP geslachtsaanduiding, mag null zijn
     * @throws NullPointerException als geslachtsaanduiding null is
     */
    public BrpGeslachtsaanduidingInhoud(
            @Element(name = "geslachtsaanduidingCode", required = false) final BrpGeslachtsaanduidingCode geslachtsaanduidingCode) {
        this.geslachtsaanduidingCode = geslachtsaanduidingCode;
    }

    /**
     * Geef de waarde van geslachtsaanduiding code van BrpGeslachtsaanduidingInhoud.
     * @return de waarde van geslachtsaanduiding code van BrpGeslachtsaanduidingInhoud
     */
    public BrpGeslachtsaanduidingCode getGeslachtsaanduidingCode() {
        return geslachtsaanduidingCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isAttribuutGevuld(geslachtsaanduidingCode);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpGeslachtsaanduidingInhoud)) {
            return false;
        }
        final BrpGeslachtsaanduidingInhoud castOther = (BrpGeslachtsaanduidingInhoud) other;
        return new EqualsBuilder().append(geslachtsaanduidingCode, castOther.geslachtsaanduidingCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(geslachtsaanduidingCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("geslachtsaanduiding", geslachtsaanduidingCode)
                .toString();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.validatie;

/**
 * Deze class levert helper functionaliteit voor het doen van validaties.
 */
public final class ValidationUtils {

    /**
     * Explicit private constructor.
     */
    private ValidationUtils() {
        throw new AssertionError("Er mag geen instantie van ValidationUtils gemaakt worden.");
    }

    /**
     * Controleert de lijst met parameters en gooit een nullpointer exception als één van de parameters null is.
     * @param message een string conform de String.format definitie die wordt aangeroepen met de 'message' en 'parameters' parmaters.
     * @param parameters de lijst met parameters die op null waarden wordt gecontroleerd.
     * @see String#format(String, Object...)
     */
    public static void controleerOpNullWaarden(final String message, final Object... parameters) {
        if (!ValidationUtils.isEenParameterGevuld(parameters)) {
            throw new NullPointerException(String.format(message, parameters));
        }
    }

    /**
     * Controleert de lijst met parameters op null waarden. Als één van de parameters NIET null is wordt true
     * geretourneerd, anders false.
     * @param parameters de lijst met te controleren parameters
     * @return true als één van de parameters NIET null is, anders false
     */
    private static boolean isEenParameterGevuld(final Object... parameters) {
        boolean result = false;
        for (final Object parameter : parameters) {
            if (parameter != null) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Controleer dat een parameter niet null is, gooi anders een IllegalArgumentException.
     * @param melding melding
     * @param parameter te controleren parameter
     * @throws IllegalArgumentException wanneer parameter null is
     */
    public static void checkNietNull(final String melding, final Object parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException(melding);
        }
    }

    /**
     * Controleer dat exact 1 parameter niet null is, gooi anders een IllegalArgumentException.
     * @param melding melding
     * @param parameters te controleren parameters
     * @throws IllegalArgumentException wanneer alle parameters null zijn, of meer dan 1 parameter niet null
     */
    public static void checkExactEen(final String melding, final Object... parameters) {
        final int aantal = aantalNietNull(parameters);
        if (aantal != 1) {
            throw new IllegalArgumentException(melding);
        }
    }

    /**
     * Controleer dat maximaal 1 parameter niet null is, gooi anders een IllegalArgumentException.
     * @param melding melding
     * @param parameters te controleren parameters
     * @throws IllegalArgumentException wanneer meer dan 1 parameter niet null
     */
    public static void checkMaximaalEen(final String melding, final Object... parameters) {
        final int aantal = aantalNietNull(parameters);
        if (aantal != 0 && aantal != 1) {
            throw new IllegalArgumentException(melding);
        }
    }

    private static int aantalNietNull(final Object... parameters) {
        int result = 0;
        for (final Object parameter : parameters) {
            if (parameter != null) {
                result++;
            }
        }
        return result;
    }

}

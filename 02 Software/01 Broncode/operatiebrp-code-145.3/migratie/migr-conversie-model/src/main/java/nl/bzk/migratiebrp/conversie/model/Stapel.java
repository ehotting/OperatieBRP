/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Deze abstracte class bevat alle stapel functionaliteit die gebruikt wordt in de LO3/Migratie en BRP stapels.
 *
 * De stapel staat het niet toe dat de lijst van elementen wordt gewijzigd. Indien dus de elementen waaruit de lijst
 * bestaat immutable zijn is deze class immutable en dus thread safe.
 *
 * Nota: de elementen moeten van oud naar nieuw worden gesorteerd.
 * @param <T> het type element in de stapel
 */
public class Stapel<T> implements Iterable<T> {

    private final List<T> delegate;

    /**
     * Maakt een Stapel object.
     * @param elementen de lijst met elementen voor deze stapel, mag niet leeg of null zijn
     * @throws IllegalArgumentException als elementen een lege lijst is
     * @throws NullPointerException als elementen null is
     */
    protected Stapel(final List<T> elementen) {
        if (elementen == null) {
            throw new NullPointerException("elementen mag niet null zijn");
        }
        this.delegate = new ArrayList<>(elementen);
        if (this.delegate.isEmpty()) {
            throw new IllegalArgumentException("de stapel mag niet leeg zijn");
        }
    }

    /**
     * Geef de waarde van laatste element.
     * @return het laatste element uit deze stapel, kan niet null zijn. Het is niet gegarandeerd dat dit het actuele element is, dat is afhankelijk van hoe de
     * stapel gesorteerd is.
     */
    public final T getLaatsteElement() {
        // invariant: stapel mag niet leeg zijn
        return this.delegate.get(this.delegate.size() - 1);
    }

    /**
     * @param element het element in de lijst waarvan het vorige element wordt geretourneerd
     * @return het vorige element of null als deze niet gevonden kan worden
     */
    public final T getVorigElement(final T element) {
        final int index = this.delegate.indexOf(element);
        if (index <= 0) {
            return null;
        }
        return this.delegate.get(index - 1);
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range {@code index < 0 || index >= size()}
     */
    public final T get(final int index) {
        return this.delegate.get(index);
    }

    /**
     * Geef de empty.
     * @return true if this list contains no elements.
     */
    public final boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    /**
     * @return the number of elements in this list. If this list contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     */
    public final int size() {
        return this.delegate.size();
    }

    /**
     * Start block that is designed for extension. Subclasses are allowed to override hashCode, equals and toString().
     * @return String weergave van object
     */
    @Override
    public String toString() {
        return "Stapel[" + this.delegate.toString() + "]";
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null ||  this.getClass() != other.getClass()) {
            return false;
        }

        final Stapel otherStapel = (Stapel) other;
        return this.delegate.equals(otherStapel.delegate);
    }

    /*
     * End block that is designed for extension.
     */

    /* Protected methods */

    /**
     * Geef de waarde van elementen.
     * @return elementen
     */
    protected final List<T> getElementen() {
        return new ArrayList<>(delegate);
    }

    @Override
    public final Iterator<T> iterator() {
        return this.delegate.iterator();
    }

    /**
     * Sorteerd de stapel met meegegeven {@link Comparator}.
     * @param comparator de comparator waarmee de stapel gesorteerd moet worden
     */
    protected void sorteer(final Comparator<? super T> comparator) {
        delegate.sort(comparator);
    }

}

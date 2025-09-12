package il.ac.hit.project.main.view.observable;

import java.util.*;

/**
 * A generic observable property that holds a value of type {@code T}
 * and notifies registered observers whenever the value changes.
 * <p>
 * This class implements a simple Observer pattern, where:
 * <ul>
 *   <li>{@link #setValue(Object)} updates the value and notifies observers</li>
 *   <li>{@link #getValue()} retrieves the current value</li>
 *   <li>{@link #addObserver(IPropertyObserver)} registers new observers</li>
 * </ul>
 *
 * @param <T> the type of value being observed
 */
public class ObservableProperty<T> {
    /**
     * The current value of the property.
     */
    private T value;

    /**
     * List of observers subscribed to changes of this property.
     */
    private final List<IPropertyObserver<T>> obs = new ArrayList<>();

    /**
     * Creates a new observable property with an initial value.
     *
     * @param initial the initial value of the property
     */
    public ObservableProperty(T initial) {
        this.value = initial;
    }

    /**
     * Updates the property's value and notifies all registered observers.
     *
     * @param v the new value to set
     */
    public void setValue(T v) {
        T o = this.value;  // שמירה של הערך הישן לצורך דיווח
        this.value = v;    // עדכון הערך החדש
        // עדכון כל ה-observers עם הערך הישן והחדש
        for (IPropertyObserver<T> x : obs) {
            x.onChanged(o, v);
        }
    }

    /**
     * Returns the current value of the property.
     *
     * @return the current value
     */
    public T getValue() {
        return value;
    }

    /**
     * Registers an observer that will be notified whenever the value changes.
     *
     * @param o the observer to add
     */
    public void addObserver(IPropertyObserver<T> o) {
        obs.add(o);
    }
}

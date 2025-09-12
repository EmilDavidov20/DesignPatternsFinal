package il.ac.hit.project.main.view.observable;

/**
 * Generic observer interface for observing changes in property values.
 * <p>
 * Classes that implement this interface can be registered to listen
 * for updates from an observable property (see {@code Property<T>}).
 * Whenever the property's value changes, the {@link #onChanged(Object, Object)}
 * method is invoked with the old and new values.
 * </p>
 *
 * @param <T> the type of value being observed
 */
public interface IPropertyObserver<T> {

    /**
     * Called when the observed property's value changes.
     *
     * @param oldVal the previous value (may be {@code null})
     * @param newVal the updated value (may be {@code null})
     */
    void onChanged(T oldVal, T newVal);
}

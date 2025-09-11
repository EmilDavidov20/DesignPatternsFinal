package il.ac.hit.project.main.view.observable;

public interface IPropertyObserver<T> {
    void onChanged(T oldVal, T newVal);
}
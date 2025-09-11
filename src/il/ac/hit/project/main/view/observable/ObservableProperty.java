package il.ac.hit.project.main.view.observable;

import java.util.*;

public class ObservableProperty<T> {
    private T value;
    private final List<IPropertyObserver<T>> obs = new ArrayList<>();

    public ObservableProperty(T initial) {
        this.value = initial;
    }

    public void setValue(T v) {
        T o = this.value;
        this.value = v;
        for (IPropertyObserver<T> x : obs) x.onChanged(o, v);
    }

    public T getValue() {
        return value;
    }

    public void addObserver(IPropertyObserver<T> o) {
        obs.add(o);
    }
}
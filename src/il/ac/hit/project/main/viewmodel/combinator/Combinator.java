package il.ac.hit.project.main.viewmodel.combinator;

import java.util.function.Predicate;

public final class Combinator {
    private Combinator() {
    }

    public static <T> Predicate<T> and(Predicate<T> a, Predicate<T> b) {
        return a.and(b);
    }

    public static <T> Predicate<T> or(Predicate<T> a, Predicate<T> b) {
        return a.or(b);
    }
}
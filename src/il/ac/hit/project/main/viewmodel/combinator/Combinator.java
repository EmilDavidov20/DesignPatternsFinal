package il.ac.hit.project.main.viewmodel.combinator;

import java.util.function.Predicate;

/**
 * Utility class for combining {@link Predicate} conditions.
 * <p>
 * Provides reusable static methods to combine two predicates using
 * logical AND and OR operations. Used in filtering tasks within
 * the {@link il.ac.hit.project.main.viewmodel} layer.
 */
public final class Combinator {
    /**
     * Combines two predicates using logical AND.
     * <p>
     * The resulting predicate returns {@code true} if both input predicates
     * evaluate to {@code true}.
     *
     * @param a   the first predicate
     * @param b   the second predicate
     * @param <T> the type of input to the predicate
     * @return a predicate that performs {@code a && b}
     */
    public static <T> Predicate<T> and(Predicate<T> a, Predicate<T> b) {
        return a.and(b);
    }

    /**
     * Combines two predicates using logical OR.
     * <p>
     * The resulting predicate returns {@code true} if at least one
     * of the input predicates evaluates to {@code true}.
     *
     * @param a   the first predicate
     * @param b   the second predicate
     * @param <T> the type of input to the predicate
     * @return a predicate that performs {@code a || b}
     */
    public static <T> Predicate<T> or(Predicate<T> a, Predicate<T> b) {
        return a.or(b);
    }
}

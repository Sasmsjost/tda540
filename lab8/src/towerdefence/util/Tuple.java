package towerdefence.util;

/**
 * Because the java standard library doesn't provide a tuple struct?!?!?!?!?!?!?
 *
 * @param <X> The type of value a
 * @param <Y> The type of value b
 */
public class Tuple<X, Y> {
    public final X a;
    public final Y b;

    public Tuple(X a, Y b) {
        this.a = a;
        this.b = b;
    }
}

package towerdefence.util;

public abstract class Position<T extends Number> {
    T x;
    T y;

    public Position(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(T y) {
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!this.getClass().equals(obj.getClass())) {
           return false;
        }

        Position<T> other = (Position<T>) obj;
        return x.equals(other.x) && y.equals(other.y);
    }
}

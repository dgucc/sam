package unamur;

import java.util.Objects;

public class Tuple {
    public final int x;
    public final int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tuple t))
            return false;

        return x == t.x && y == t.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
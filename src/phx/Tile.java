package phx;

import static phx.Value._0;
import static phx.Value._2;
import static phx.Value._4;

public class Tile {
    private final Value val;

    public final static Tile ZERO = new Tile(_0);

    public final static Tile TWO = new Tile(_2);

    public final static Tile FOUR = new Tile(_4);

    public Tile(Value v) {
        val = v;
    }

    /*
     * factory method to get Tile instance
     */
    public static Tile valueOf(int num) {
        switch(num) {
        case 0 : return ZERO;
        case 2 : return TWO;
        case 4 : return FOUR;
        default : return new Tile(Value.of(num));
        }
    }

    public Value getVal() {
        return val;
    }

    /**
     * test the tile is empty or not. empty means it's val field is Value._0.
     */
    public boolean empty() {
        return val == _0;
    }

    @Override
    public String toString() {
        return val.score() + "";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((val == null) ? 0 : val.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (val != other.val)
            return false;
        return true;
    }
}

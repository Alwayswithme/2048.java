package phx;

public class Tile {
    private Value val;

    public Tile() {
        val = Value._0;
    }
    public Tile(Value v) {
        val = v;
    }
    public Tile(int num) {
        this(Value.of(num));
    }

    public Value getVal() {
        return val;
    }

    public void setVal(Value val) {
        this.val = val;
    }

    /**
     *  test the tile is empty or not.
     *  empty means it's val field is Value._0.
     */
    public boolean empty() {
        return val == Value._0;
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

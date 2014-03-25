package phx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

public class Board extends JPanel {
    private static final long serialVersionUID = -1790261785521495991L;
    /* Board row and column */
    public static final int ROW = 4;
    /* this array use for convenience iterate */
    public static final int[] _0123 = {0, 1, 2, 3};

    GUI2048 host;

    private Tile[] tiles;

    private static Value GOAL = Value._2048;

    public void setGOAL(int num) {
        GOAL = Value.of(num);
    }

    public Board(GUI2048 f) {
        host = f;
        setFocusable(true);
        initTiles();
    }

    /**
     * initialize the game, also use to start a new game
     */
    public void initTiles() {
        tiles = new Tile[ROW * ROW];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
        }
        addTile();
        addTile();
    }

    /**
     * get the Tile which at tiles[x + y * ROW ]
     */
    Tile tileAt(int x, int y) {
        return tiles[x + y * ROW];
    }

    /**
     * Generate a new Tile in the availableSpace.
     */
    private void addTile() {
        List<Tile> list = availableSpace();
        int index = (int)(Math.random() * list.size()) % list.size();
        Tile ranEmptyTile = list.get(index);
        ranEmptyTile.setVal(Value.of(randomInt()));
    }

    /**
     * Generate a integer 2 or 4, bigger chances return 2
     */
    private int randomInt() {
        return Math.random() < 0.15 ? 4 : 2;
    }

    /**
     * Query the field tiles Array, and get the list of
     * empty tile.
     */
    private List<Tile> availableSpace() {
        List<Tile> list = new LinkedList<>();
        for (Tile t : tiles) {
            if (t.empty()) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * return true if the board doesn't have empty tile
     */
    private boolean isFull() {
        return availableSpace().size() == 0;
    }

    /**
     * return true if didn't lose
     */
    boolean canMove() {
        if (!isFull()) {
            return true;
        }
        for (int x : _0123) {
            for (int y : _0123) {
                Tile t = tileAt(x, y);
                if ( (x < ROW - 1 && t.equals(tileAt(x+1, y)))
                   ||   (y < ROW - 1 && t.equals(tileAt(x, y + 1)))
                   ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * move all the tiles to the left side.
     */
    public void left() {
        boolean needAddTile = false;
        for (int i : _0123) {
            // get i-th line
            Tile[] origin = getLine(i);
            // get the line have been moved to left
            Tile[] afterMove = moveLine(origin);
            // get the the line after
            Tile[] merged = mergeLine(afterMove);
            // set i-th line with the merged line
            setLine(i, merged);
            if (!needAddTile && !cmpLine(origin, merged)) {
                // if origin and merged line is different
                // need to add a new Tile in the board
                needAddTile = true;
            }
        }

        // if addTile is false, those line didn't change
        // then no need to add tile
        if (needAddTile) {
            addTile();
        }
    }

    /**
     * move tiles to the right side.
     */
    public void right() {
        tiles = rotate(180);
        left();
        tiles = rotate(180);
    }

    /**
     * move tiles up.
     */
    public void up() {
        tiles = rotate(270);
        left();
        tiles = rotate(90);
    }

    /**
     * move tiles down.
     */
    public void down() {
        tiles = rotate(90);
        left();
        tiles = rotate(270);
    }

    /**
     * Compare two tile line, to see if they have same tile.
     */
    private boolean cmpLine(Tile[] line1, Tile[] line2) {
        if (line1 == line2) {
            return true;
        } else if (line1.length != line2.length) {
            return false;
        }

        for (int i = 0; i < line1.length; i++) {
            if (!line1[i].equals(line2[i]))
                return false;
        }
        return true;
    }

    /**
     * rotate the tiles dgr degree, clockwise.
     * @return  Tile[], the tiles after rotate.
     */
    private Tile[] rotate(int dgr) {
        Tile[] newTiles = new Tile[ROW * ROW];
        int offsetX = 3, offsetY = 3;
        if (dgr == 90) {
            offsetY = 0;
        } else if (dgr == 180) {
        } else if (dgr == 270) {
            offsetX = 0;
        } else {
            throw new IllegalArgumentException("Only can rotate 90, 180, 270 degree");
        }
        double radians = Math.toRadians(dgr);
        int cos = (int) Math.cos(radians);
        int sin = (int) Math.sin(radians);
        for (int x : _0123) {
            for (int y : _0123) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                newTiles[(newX) + (newY) * ROW] = tileAt(x, y);
            }
        }
        return newTiles;
    }

    /**
     * move the not empty tile in idx-th line to left
     */
    Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<>();
        for (int i : _0123) {
            if (!oldLine[i].empty())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            // list empty, oldLine is empty line.
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i : _0123) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }

    /**
     * Merge the oldLine of Tiles, then return a newLine
     */
    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for (int i = 0; i < ROW && !oldLine[i].empty(); i++) {
            int num = oldLine[i].getVal().num();
            if (i < 3 && oldLine[i].equals(oldLine[i+1])) {
                // can be merge, double the val and delete next one!
                num *= 2;
                if (Value.of(num) == GOAL) {
                    // reach goal, show message
                    host.win();
                }
                i++;
            }
            list.add(new Tile(num));
        }
        if (list.size() == 0) {  // nothing change
            return oldLine;
        } else {
            ensureSize(list, 4);
            return list.toArray(new Tile[4]);
        }
    }

    /**
     * Append the empty tile to the l list of tiles, ensure
     * it's size is s.
     */
    private void ensureSize(List<Tile> l, int s) {
        while (l.size() != s) {
            l.add(new Tile());
        }
    }

    /**
     * get the idx-th line.
     */
    private Tile[] getLine(int idx) {
        Tile[] result = new Tile[4];
        for(int i : _0123) {
            result[i] = tileAt(i, idx);
        }
        return result;
    }

    /**
     * set the idx-th line. replace by the re array.
     */
    private void setLine(int idx, Tile[] re) {
        for (int i : _0123) {
            tiles[i + idx * ROW] = re[i];
        }
    }

    /* Background color */
    private static final Color BG_COLOR = new Color(0xbbada0);

    /* Font */
    private static final Font STR_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 17);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.setFont(STR_FONT);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int y : _0123) {
            for (int x : _0123) {
                drawTile(g, tiles[x + y * ROW], x, y);
            }
        }
    }

    /* Side of the tile square */
    private static final int SIDE = 64;

    /* Margin between tiles */
    private static final int MARGIN = 16;

    /**
     * Draw a tile use specific number and color
     * in (x, y) coords, x and y need offset a bit.
     */
    private void drawTile(Graphics g, Tile tile, int x, int y) {
        Value val = tile.getVal();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(val.color());
        g.fillRect(xOffset, yOffset, SIDE, SIDE);
        g.setColor(val.fontColor());
        if (val.num() != 0)
            g.drawString(String.format("%1$4s", val.num()), xOffset + 10, yOffset + 40);
    }

    private int offsetCoors(int arg) {
        return arg * (MARGIN + SIDE) + MARGIN;
    }

}

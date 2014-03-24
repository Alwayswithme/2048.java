package phx;

import java.awt.Color;

/**
 * This enum represent the tile value in the grid
 * use it to store the numbers and colors
 * @author phoenix
 *
 */
public enum Value {
    _0(0,       0x776e65,0xcdc0b4),
    _2(2,       0x776e65,0xeee4da),
    _4(4,       0x776e65,0xede0c8),
    _8(8,       0xf9f6f2,0xf2b179),
    _16(16,     0xf9f6f2,0xf59563),
    _32(32,     0xf9f6f2,0xf67c5f),
    _64(64,     0xf9f6f2,0xf65e3b),
    _128(128,   0xf9f6f2,0xedcf72),
    _256(256,   0xf9f6f2,0xedcc61),
    _512(512,   0xf9f6f2,0xedc850),
    _1024(1024, 0xf9f6f2,0xedc53f),
    _2048(2048, 0xf9f6f2,0xedc22e);
    private final int num;
    private final Color color;
    private final Color fontColor;

    Value(int n, int f, int c) {
        num = n;
        color = new Color(c);
        fontColor = new Color(f);
    }

    /**
     * Factory method to get one of these enum.
     * This is a wrapper of valueOf().
     * @param num
     * @return
     */
    static Value of(int num) {
        if (numIsLegal(num)) {
            return Value.valueOf("_" + num);
        } else {
            throw new IllegalArgumentException("can not use this value : "+ num);
        }
    }

    /**
     * Check num is a legal value or not
     * num should be 0, 2, 4, 8....
     * @param num
     * @return      true, if it is legal
     */
    private static boolean numIsLegal(int num) {
        return (num & (num-1)) == 0 || num == 0;
    }

    public Color fontColor() {
        return fontColor;
    }
    public Color color() {
        return color;
    }

    public int num() {
        return num;
    }
}

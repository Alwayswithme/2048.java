package phx;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class KeySetting extends KeyAdapter {

    private static final HashMap<Integer, Method> keyMapping = new HashMap<>();
    private static Integer[] kcs = { VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT, VK_R };
    private static String[] methodName = { "moveUp", "moveDown", "moveLeft", "moveRight", "initTiles" };
    private final Board board;
    public KeySetting(Board b) {
        board = b;
        for (int i = 0; i < kcs.length; i++) {
            try {
                keyMapping.put(kcs[i], b.getClass().getMethod(methodName[i]));
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        Method action = keyMapping.get(e.getKeyCode());
        try {
            action.invoke(board);
            board.repaint();
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e1) {
            e1.printStackTrace();
        }
        if (!board.canMove()) {
            board.host.lose();
        }
        
    }

}

package phx;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author phoenix
 * @version 0.1.0
 */
public class GUI2048 extends JFrame {

    private static final long serialVersionUID = 1L;

    JLabel statusBar;

    private static final String TITLE = "2048 in Java";

    private static final String WIN_MSG = "You already win, but you can continue";

    private static final String LOSE_MSG = "You lose, press 'r' to try again!";

    public static void main(String[] args) {

        GUI2048 game = new GUI2048();
        Board board = new Board(game);
        if (args.length != 0 && args[0].matches("[0-9]*")) {
            board.setGOAL(Integer.parseInt(args[0]));
        }
        KeySetting kb = new KeySetting(board);
        board.addKeyListener(kb);
        game.add(board);
        
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }

    public GUI2048() {
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(340, 400);
        setResizable(false);

        statusBar = new JLabel("");
        add(statusBar, BorderLayout.SOUTH);
    }

    void win() {
        statusBar.setText(WIN_MSG);
    }

    void lose() {
        statusBar.setText(LOSE_MSG);
    }
}

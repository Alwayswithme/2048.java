package phx;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author phoenix
 * @version 0.1
 */
public class GUI2048 extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JLabel statusBar;

    public static void main(String[] args) {
        GUI2048 game = new GUI2048();
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }

    public GUI2048() {
        setTitle("2048 in Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(340, 400);
        setResizable(false);

        Board board = new Board(this);
        KeySetting kb = new KeySetting(board);

        board.addKeyListener(kb);
        add(board);

        statusBar = new JLabel("");
        add(statusBar, BorderLayout.SOUTH);
    }
    
    void win() {
        statusBar.setText("You already win, but you can continue have fun");
    }
    void lose() {
        statusBar.setText("You lose, press 'r' to try agin!");
    }
}

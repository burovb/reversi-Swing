package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;
import sk.stuba.fei.uim.oop.players.Player;
import sk.stuba.fei.uim.oop.players.PlayerList;
import sk.stuba.fei.uim.oop.utility.UniversalAdapter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Game extends UniversalAdapter {
    private final JFrame frame;
    @Getter
    private final JLabel playerLabel, scoreLabel, boardSizeLabel;
    private Board board;
    private int boardSize;
    private PlayerList players;
    private Player firstPlayer;

    public Game() {
        frame = new JFrame("Reversi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720,752);
        frame.setResizable(false);
        frame.setFocusable(true);

        players = new PlayerList(new Player("Player 1", Color.BLACK), null);
        players.addNode(new PlayerList(new Player("Player 2", Color.WHITE), this.players));
        firstPlayer = players.getNode();

        frame.setLayout(new BorderLayout());
        boardSize = 6;
        this.restart(boardSize);
        frame.addKeyListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 32, 16));

        playerLabel = new JLabel(players.getNode().getName());
        panel.add(playerLabel);
        scoreLabel = new JLabel("Score");
        panel.add(scoreLabel);

        JButton button = new JButton("Restart");
        button.addActionListener(this);
        button.setFocusable(false);
        panel.add(button);

        boardSizeLabel = new JLabel("6 x 6");
        panel.add(boardSizeLabel);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 6, 12, boardSize);
        slider.setMajorTickSpacing(2);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setFocusable(false);
        slider.addChangeListener(this);
        panel.add(slider);

        frame.add(panel, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }

    public void restart(int size) {
        try {
            this.frame.remove(this.board);
        } catch (NullPointerException ignored) {}
        this.board = new Board(this, size, this.players);
        this.frame.add(this.board);
        this.players.setNode(firstPlayer);
        this.frame.revalidate();
        this.frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.restart(this.boardSize);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: this.frame.dispose(); break;
            case KeyEvent.VK_R: this.restart(this.boardSize); break;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int size = ((JSlider) e.getSource()).getValue();
        if (size != this.boardSize && size % 2 == 0) {
            this.boardSize = size;
            this.restart(this.boardSize);
            this.boardSizeLabel.setText(this.boardSize + " x " + this.boardSize);
        }
    }
}

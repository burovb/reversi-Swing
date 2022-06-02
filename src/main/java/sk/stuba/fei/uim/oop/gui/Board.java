package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.players.Player;
import sk.stuba.fei.uim.oop.players.PlayerList;
import sk.stuba.fei.uim.oop.utility.MouseAdapterInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static sk.stuba.fei.uim.oop.gui.Colors.*;

public class Board extends JPanel implements MouseAdapterInterface {
    private Game game;
    private int size, prevRow, prevCol;
    private Cell[][] board;
    private PlayerList players;

    public Board(Game game, int size, PlayerList players) {
        this.game = game;
        this.size = size;
        this.board = new Cell[this.size][this.size];
        this.players = players;

        this.setLayout(new GridLayout(this.size, this.size));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j += 2) {
                if ((i + 1) % 2 == 0) {
                    this.board[i][j] = new Cell(i, j, LIGHT_GREEN);
                    this.board[i][j + 1] = new Cell(i, j + 1, DARK_GREEN);
                } else {
                    this.board[i][j] = new Cell(i, j, DARK_GREEN);
                    this.board[i][j + 1] = new Cell(i, j + 1, LIGHT_GREEN);
                }
            }
        }

        initBoard();
    }

    public void initBoard() {
        int x = this.size / 2 - 1;

        this.board[x][x] = new Cell(x, x, DARK_GREEN, this.players.getNode().getColor());
        this.board[x][x + 1] = new Cell(x, x + 1, LIGHT_GREEN, this.players.getNext().getNode().getColor());
        this.board[x + 1][x] = new Cell(x + 1, x, LIGHT_GREEN, this.players.getNext().getNode().getColor());
        this.board[x + 1][x + 1] = new Cell(x + 1, x + 1, DARK_GREEN, this.players.getNode().getColor());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.removeAll();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.add(this.board[i][j]);
            }
        }

        this.game.getPlayerLabel().setText(this.players.getNode().getName());
        Player temp;
        this.game.getScoreLabel().setText(String.format(
                "<html>%s: %d<br>%s: %d</html>",
                (temp = this.players.getNode()).getName(), countPieces(temp.getColor()),
                (temp = this.players.getNext().getNode()).getName(), countPieces(temp.getColor())
        ));

        this.revalidate();
    }

    public int countPieces(Color color) {
        int counter = 0;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j].getChipColor() == color) counter++;
            }
        }

        return counter;
    }

    public boolean isValidMove(int row, int col) {
        return this.board[row][col].getChipColor() == LIGHT_CYAN;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Cell comp = (Cell) this.getComponentAt(e.getPoint());
        int
                i = comp.getRow(),
                j = comp.getCol();

        if (isValidMove(i, j)) {
            this.board[i][j] = new Cell(i, j, comp.getBackColor(), this.players.getNode().getColor());
            this.players = this.players.getNext();

            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Cell comp;
        try {
            comp = (Cell) this.getComponentAt(e.getPoint());
        } catch (ClassCastException exc) {return;}
        int
                i = comp.getRow(),
                j = comp.getCol();

        if (this.board[i][j].getChipColor() == TRANSPARENT) {
            this.board[i][j] = new Cell(i, j, comp.getBackColor(), LIGHT_CYAN);

            if (i != this.prevRow || j != this.prevCol) {
                Cell temp = this.board[this.prevRow][this.prevCol];
                Color
                        prevBack = temp.getBackColor(),
                        prevChip = temp.getChipColor();
                if (prevChip == LIGHT_CYAN) prevChip = TRANSPARENT;
                temp = new Cell(this.prevRow, this.prevCol, prevBack, prevChip);
                this.board[this.prevRow][this.prevCol] = temp;

                this.prevRow = i;
                this.prevCol = j;
            }

            this.repaint();
        }
    }
}
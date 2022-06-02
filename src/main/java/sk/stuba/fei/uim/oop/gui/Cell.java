package sk.stuba.fei.uim.oop.gui;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

import static sk.stuba.fei.uim.oop.gui.Colors.TRANSPARENT;

public class Cell extends JPanel {
    @Getter
    private int row, col;
    @Getter
    private Color backColor, chipColor;

    public Cell(int row, int col, Color backColor) {
        this.row = row;
        this.col = col;
        this.backColor = backColor;
        this.setBackground(this.backColor);

        this.chipColor = TRANSPARENT;
    }

    public Cell(int row, int col, Color backColor, Color chipColor) {
        this(row, col, backColor);

        this.chipColor = chipColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(this.chipColor);
        int width = (int) (this.getWidth() * 0.8);
        int height = (int) (this.getHeight() * 0.8);
        g.fillOval((this.getWidth() - width) / 2, (this.getHeight() - height) / 2, width, height);
    }
}

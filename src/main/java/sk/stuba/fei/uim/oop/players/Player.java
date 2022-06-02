package sk.stuba.fei.uim.oop.players;

import lombok.Getter;

import java.awt.*;

@Getter
public class Player {
    private String name;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}

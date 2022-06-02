package sk.stuba.fei.uim.oop.players;

public class PlayerList {
    private Player player;
    private PlayerList next;

    public PlayerList(Player player, PlayerList next) {
        this.player = player;
        this.next = next;
    }

    public void addNode(PlayerList node) {
        this.next = node;
    }

    public PlayerList getNext() {
        return this.next;
    }

    public void setNode(Player player) {
        this.player = player;
    }

    public Player getNode() {
        return this.player;
    }
}
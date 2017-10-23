package player;

import dominoe.Board;
import exception.BadInputException;

public abstract class Player {

    protected String id;
    protected String name;

    public Player(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtherId(String id) {
        if (id.equals("player1")) return "player2";
        else return "player1";
    }

    public abstract Board nextMove(Board board);

    public String getName() {
        return name;
    }
}

package dominoe;

import player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private Player player1;
    private Player player2;
    private String turn;
    private Board current;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        player1.setId("player1");
        player2.setId("player2");

        ArrayList<Piece> pieces = new ArrayList<>();

        for (int l = 0; l < 7; l++)
            for (int r = l; r < 7; r++)
                pieces.add(new Piece(l, r));

        Collections.shuffle(pieces);

        ArrayList<Piece> player1pieces = new ArrayList<>(pieces.subList(0, 14));
        ArrayList<Piece> player2pieces = new ArrayList<>(pieces.subList(14, 28));

        Piece startPiece = new Piece(6, 6);

        if (player1pieces.contains(startPiece)) {
            player1pieces.remove(startPiece);
            turn = "player2";
            System.out.println(player1.getName() + " starts with " + startPiece);
        } else {
            player2pieces.remove(startPiece);
            turn = "player1";
            System.out.println(player2.getName() + " starts with " + startPiece);
        }

        current = new Board(player1pieces, player2pieces);

        while (!current.isEnd()) {
            if (turn.equals("player1")) {
                if (current.canMove("player1")) {
                    System.out.println("\nTurn " + player1.getName());
                    current = player1.nextMove(current);

                } else {
                    System.out.println("\n" + player1.getName() + " can't move!");
                }
                turn = "player2";
            } else {
                if (current.canMove("player2")) {
                    System.out.println("\nTurn " + player2.getName());
                    current = player2.nextMove(current);

                } else {
                    System.out.println("\n" + player2.getName() + " can't move!");
                }
                turn = "player1";
            }
        }

        String winner = current.winner();
        if (winner.equals("player1"))
            System.out.println("Winner is " + player1.getName());
        else if (winner.equals("player2"))
            System.out.println("Winner is " + player2.getName());
        else
            System.out.println("Draw");
    }
}

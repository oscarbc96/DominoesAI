package dominoe;

import exception.InvalidMovementException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class Board {
    private int currentLeft;
    private int currrentRight;

    private ArrayList<Piece> player1 = new ArrayList<>();
    private ArrayList<Piece> player2 = new ArrayList<>();

    public Board(ArrayList<Piece> player1, ArrayList<Piece> player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentLeft = 6;
        this.currrentRight = 6;
    }

    public Board(ArrayList<Piece> player1, ArrayList<Piece> player2, int left, int right) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentLeft = left;
        this.currrentRight = right;
    }

    public int getCurrentLeft() {
        return currentLeft;
    }

    public int getCurrrentRight() {
        return currrentRight;
    }

    public ArrayList<Piece> getPieces(String player) {
        if (player.equals("player1"))
            return player1;
        else
            return player2;
    }

    public ArrayList<Piece> getOtherOnePieces(String player) {
        if (player.equals("player2"))
            return player1;
        else
            return player2;
    }

    public ArrayList<Board> getMoves(String id) {
        ArrayList<Board> moves = new ArrayList<>();

        for (Piece p : getPieces(id)) {
            try {
                moves.add(setPiece(p, 'l'));
            } catch (InvalidMovementException e) {
            }
            try {
                moves.add(setPiece(p, 'r'));
            } catch (InvalidMovementException e) {
            }
        }

        return moves;
    }

    public boolean canMove(String id) {
        List<Integer> values = getPieces(id).stream().flatMapToInt(p -> p.getValues().stream().mapToInt(i -> i)).distinct().boxed().collect(Collectors.toList());
        return values.contains(currentLeft) || values.contains(currrentRight);
    }

    private boolean validPiece(Piece piece, char pos) {
        if (pos == 'l')
            return piece.getValues().contains(currentLeft);
        else
            return piece.getValues().contains(currrentRight);
    }

    public Board setPiece(Piece selected, char pos) throws InvalidMovementException {
        if (!validPiece(selected, pos))
            throw new InvalidMovementException();

        int left = currentLeft;
        int rigth = currrentRight;

        if (pos == 'l') {
            if (selected.getRigth() == currentLeft)
                left = selected.getLeft();
            else if (selected.getLeft() == currentLeft)
                left = selected.getRigth();
        } else {
            if (selected.getRigth() == currrentRight)
                rigth = selected.getLeft();
            else if (selected.getLeft() == currrentRight)
                rigth = selected.getRigth();
        }

        ArrayList<Piece> player1aux = (ArrayList<Piece>) player1.clone();
        ArrayList<Piece> player2aux = (ArrayList<Piece>) player2.clone();

        if (player1aux.contains(selected))
            player1aux.remove(selected);
        else
            player2aux.remove(selected);

        return new Board(player1aux, player2aux, left, rigth);
    }

    public boolean isEnd() {
        return (!canMove("player1")) && (!canMove("player2"));
    }

    public String winner() {
        if (player1.isEmpty())
            return "player1";
        else if (player2.isEmpty())
            return "player2";

        int p1points = player1.stream().flatMapToInt(p -> p.getValues().stream().mapToInt(i -> i)).sum();
        int p2points = player2.stream().flatMapToInt(p -> p.getValues().stream().mapToInt(i -> i)).sum();

        if (p1points > p2points)
            return "player2";
        else if (p1points < p2points)
            return "player1";
        else
            return "draw";
    }

    @Override
    public boolean equals(Object o) {
        Board aux = (Board) o;
        boolean listequalplayer1 = getPieces("player1").containsAll(aux.getPieces("player1")) && aux.getPieces("player1").containsAll(getPieces("player1"));
        boolean listequalplayer2 = getPieces("player2").containsAll(aux.getPieces("player2")) && aux.getPieces("player2").containsAll(getPieces("player2"));
        return currentLeft == aux.getCurrentLeft() && currrentRight == aux.getCurrrentRight() && listequalplayer1 && listequalplayer2;
    }
}

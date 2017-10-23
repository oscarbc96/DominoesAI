package player;

import dominoe.Board;
import dominoe.Piece;
import exception.BadInputException;
import exception.InvalidMovementException;

import java.util.List;
import java.util.Scanner;

public class Human extends Player {

    private Scanner reader;

    public Human(String name) {
        super(name);
        reader = new Scanner(System.in);
    }

    @Override
    public Board nextMove(Board board){
        do {
            try {
                System.out.println("Left: " + board.getCurrentLeft() + " Rigth: " + board.getCurrrentRight());
                List<Piece> pieces = board.getPieces(id);
                for (int i = 0; i < pieces.size(); i++)
                    System.out.print(i + ": " + pieces.get(i) + "  ");

                System.out.println("\nSelect a piece: ");
                int n = Integer.parseInt(reader.nextLine());
                if (n < 0 || n > (pieces.size()-1))
                    throw new BadInputException();

                System.out.println("Select position: ");
                char pos = reader.nextLine().charAt(0);

                if (pos != 'l' && pos != 'r')
                    throw new BadInputException();

                return board.setPiece(pieces.get(n), pos);
            } catch (NumberFormatException | BadInputException | InvalidMovementException e ) {
                System.out.println(e);
            }
        } while (true);
    }
}

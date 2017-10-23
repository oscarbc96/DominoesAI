package player;

import algorithm.Heuristic;
import dominoe.Board;
import dominoe.Piece;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

public class Computer extends Player {
    private Heuristic heuristic;
    private int MAX_LEVEL_AB;
    private int MAX_LEVEL_MM;

    public Computer(String name, Heuristic heuristic, int maxlevelmm, int maxlevelab) {
        super(name);

        this.heuristic = heuristic;
        this.MAX_LEVEL_MM = maxlevelmm;
        this.MAX_LEVEL_AB = maxlevelab;
    }

    @Override
    public Board nextMove(Board board) {
        System.out.println("Left: " + board.getCurrentLeft() + " Rigth: " + board.getCurrrentRight());
        List<Piece> pieces = board.getPieces(id);
        for (int i = 0; i < pieces.size(); i++)
            System.out.print(i + ": " + pieces.get(i) + "  ");

        System.out.println("\nThinking...");
        Instant start = Instant.now();
        Board b1 = minimax(board, 0, id).board;
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Minimax: Time: " +duration.toSecondsPart() + "s. "+duration.toMillisPart() + "ms.");

        start = Instant.now();
        Board b2 = alphabeta(board, 0, id, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY).board;
        end = Instant.now();
        duration = Duration.between(start, end);
        System.out.println("AlphaBeta: Time: " +duration.toSecondsPart() + "s. "+duration.toMillisPart() + "ms.");

        System.out.println("Same solution? "+ b1.equals(b2));

        return b2;
    }

    private float h(Board board) {
        float h = 0.0f;

        switch (heuristic) {
            case H1:
                float my = board.getPieces(id).stream().map(p -> p.getValues().stream().mapToInt(i -> i).sum()).mapToInt(i -> i).max().orElse(0);
                float other = board.getOtherOnePieces(id).stream().map(p -> p.getValues().stream().mapToInt(i -> i).sum()).mapToInt(i -> i).max().orElse(0);
                if (my>other)
                    h = -other;
                else
                    h = my;
                break;
            case H2:
                h = 0;
                break;
            case H3:
                h = -1;
                break;
        }
        return h;
    }

    private Node minimax(Board board, int level, String player) {
        if (board.isEnd()) {
            if (board.winner() == id)
                return new Node(Float.POSITIVE_INFINITY, board);
            else if (board.winner() == "draw")
                return new Node(0.0f, board);
            else
                return new Node(Float.NEGATIVE_INFINITY, board);
        } else if (level == MAX_LEVEL_MM) {
            return new Node(h(board), board);
        } else {
            float return_value;
            Board return_board = null;

            if (player.equals(id))
                return_value = Float.NEGATIVE_INFINITY;
            else
                return_value = Float.POSITIVE_INFINITY;

            for (Board b : board.getMoves(player)) {
                Node node_aux = minimax(b, level + 1,  getOtherId(id));

                if (player.equals(id) && (node_aux.value >= return_value)) {
                    return_value = node_aux.value;
                    return_board = b;
                } else if (return_value <= node_aux.value) {
                    return_value = node_aux.value;
                    return_board = b;
                }
            }

            return new Node(return_value, return_board);
        }

    }

    private Node alphabeta(Board board, int level, String player, float alpha, float beta) {
        if (board.isEnd()) {
            if (board.winner() == id)
                return new Node(Float.POSITIVE_INFINITY, board);
            else if (board.winner() == "draw")
                return new Node(0.0f, board);
            else
                return new Node(Float.NEGATIVE_INFINITY, board);
        } else if (level == MAX_LEVEL_AB) {
            return new Node(h(board), board);
        } else {
            Board return_board = null;
            float alpha_aux = alpha;
            float beta_aux = beta;

            Iterator itr = board.getMoves(player).iterator();

            while (itr.hasNext() && alpha < beta) {
                Board b = (Board) itr.next();
                Node node_aux;

                if (player.equals(id) && alpha_aux < beta)
                    node_aux = alphabeta(b, level + 1, getOtherId(id), alpha_aux, beta);
                else if(beta_aux > alpha)
                    node_aux = alphabeta(b, level + 1, getOtherId(id), alpha, beta_aux);
                else continue;

                if (player.equals(id) && node_aux.value >= alpha_aux) {
                    alpha_aux = node_aux.value;
                    return_board = b;
                } else if (node_aux.value <= beta_aux) {
                    beta_aux = node_aux.value;
                    return_board = b;
                }
            }

            if (player.equals(id))
                return new Node(alpha_aux, return_board);
            else
                return new Node(beta_aux, return_board);
        }

    }

    private class Node {
        float value;
        Board board;

        Node(float value, Board board) {
            this.value = value;
            this.board = board;
        }
    }
}

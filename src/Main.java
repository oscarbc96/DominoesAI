import algorithm.Heuristic;
import dominoe.Game;
import player.Computer;
import player.Human;
import player.Player;

public class Main {

    public static void main(String[] args) {
        Player jarvis = new Computer("Jarvis", Heuristic.H1, 7, 9);
        Player ultron = new Computer("Ultron", Heuristic.H2, 7, 9);
        Player optimus = new Computer("Optimus Prime", Heuristic.H1, 7, 9);

        Player oscar = new Human("Oscar");

        for (int i = 0; i < 100; i++) {
            System.out.println("\nIteration "+i);
            new Game(jarvis, optimus);
        }
    }
}

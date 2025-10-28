import bots.GTOPokerBot;
import model.*;

public class Main {
    public static void main(String[] args) {
        Player human = new Player(Position.SMALL_BLIND, 1000);
        Player bot = new Player(Position.BIG_BLIND, 1000);

        TrainedSession trainedSession =
                new TrainedSession(5, 10, human, bot, 10000);

        trainedSession.run();
    }
}
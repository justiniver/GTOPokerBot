import bots.*;
import bots.cfr.*;
import model.*;
import visualization.ProbabilityChart;

import java.nio.file.Path;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int[] PRETRAINED = {1000, 5000, 10000, 50000};

    public static void main(String[] args) {
        ensurePretrainedBots();
        while (true) {
            System.out.println("\n=== PokerGTOBot ===");
            System.out.println("1) Train & Save GTO Bot");
            System.out.println("2) Play vs GTO Bot");
            System.out.println("3) Bot vs Bot Simulation");
            System.out.println("4) Convergence Chart");
            System.out.println("5) Exit");

            switch (readInt("Select", 1, 5)) {
                case 1: trainAndSave(); break;
                case 2: playVsGTOBot(); break;
                case 3: botVsBotSimulation(); break;
                case 4: convergenceChart(); break;
                case 5: return;
            }
        }
    }

    private static void ensurePretrainedBots() {
        List<Path> existing = BotStorage.listSavedBots();
        Set<Integer> existingIters = new HashSet<>();
        for (Path p : existing) existingIters.add(BotStorage.extractIterations(p));

        boolean allPresent = true;
        for (int n : PRETRAINED) {
            if (!existingIters.contains(n)) {
                allPresent = false;
                System.out.println("Training pretrained bot (" + n + " iterations)...");
                GTOPokerBot bot = new GTOPokerBot();
                bot.train(n);
                BotStorage.save(bot, n);
            }
        }
        if (allPresent) System.out.println("Pretrained bots loaded.");
    }

    private static void trainAndSave() {
        int iterations = readIntWithDefault("Training iterations", 10000);
        GTOPokerBot bot = new GTOPokerBot();
        bot.train(iterations);
        BotStorage.save(bot, iterations);
        System.out.println("Saved. Information sets: " + bot.getInformationSetCount());
    }

    private static void playVsGTOBot() {
        GTOPokerBot gtoBot = selectSavedBot();
        if (gtoBot == null) return;

        int smallBlind = readIntWithDefault("Small blind", 5);
        int bigBlind = readIntWithDefault("Big blind", 10);
        int stack = readIntWithDefault("Starting stack", 1000);

        Player human = new Player(Position.SMALL_BLIND, stack, "You");
        Player bot = new Player(Position.BIG_BLIND, stack, "Bot");
        bot.setStrategy(gtoBot);

        PokerSession session = new PokerSession(smallBlind, bigBlind, human, bot);
        session.runGames();
    }

    private static void botVsBotSimulation() {
        System.out.println("Select Bot 1 (SB):");
        PlayerStrategy bot1 = selectBot();
        if (bot1 == null) return;
        System.out.println("Select Bot 2 (BB):");
        PlayerStrategy bot2 = selectBot();
        if (bot2 == null) return;

        int games = readIntWithDefault("Number of games", 100);
        int stack = readIntWithDefault("Starting stack", 1000);

        Player p1 = new Player(Position.SMALL_BLIND, stack, "Bot 1");
        Player p2 = new Player(Position.BIG_BLIND, stack, "Bot 2");
        p1.setStrategy(bot1);
        p2.setStrategy(bot2);

        PokerSession session = new PokerSession(5, 10, p1, p2);
        session.setTrackWinningHands(true);
        session.runNumberOfGamesAutoRebuy(games);
        session.printRankAnalytics();
    }

    private static void convergenceChart() {
        int totalIterations = readIntWithDefault("Total iterations", 10000);
        int interval = readIntWithDefault("Checkpoint interval", 500);
        int gamesPerCheck = readIntWithDefault("Games per checkpoint", 200);

        GTOPokerBot gtoBot = new GTOPokerBot();
        SimpleCheckCallBot baseline = new SimpleCheckCallBot();
        List<Integer> trials = new ArrayList<>();
        List<Double> winRates = new ArrayList<>();

        int trained = 0;
        while (trained < totalIterations) {
            int batch = Math.min(interval, totalIterations - trained);
            gtoBot.train(batch);
            trained += batch;

            int wins = 0;
            for (int i = 0; i < gamesPerCheck; i++) {
                GameView s = randomScenario();
                double q = decisionQuality(gtoBot.decide(s)) - decisionQuality(baseline.decide(s));
                if (q > 0) wins++;
            }

            double winRate = (double) wins / gamesPerCheck;
            trials.add(trained);
            winRates.add(winRate);
            System.out.printf("Iteration %d: %.1f%%%n", trained, winRate * 100);
        }

        new ProbabilityChart().displayChart(trials, winRates, "GTO Bot Convergence");
    }

    // --- Bot selection ---

    private static GTOPokerBot selectSavedBot() {
        List<Path> bots = BotStorage.listSavedBots();
        if (bots.isEmpty()) {
            System.out.println("No saved bots. Train one first.");
            return null;
        }
        for (int i = 0; i < bots.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + BotStorage.extractIterations(bots.get(i)) + " iterations");
        }
        int choice = readInt("Bot", 1, bots.size());
        return BotStorage.load(bots.get(choice - 1));
    }

    private static PlayerStrategy selectBot() {
        System.out.println("  1) Saved GTO Bot  2) Rule-Based  3) Random  4) Check/Call  5) Check/Fold");
        switch (readInt("Bot", 1, 5)) {
            case 1: return selectSavedBot();
            case 2: return new RuleBasedBot(0.5, 0.5);
            case 3: return new RandomRaiseBot();
            case 4: return new SimpleCheckCallBot();
            case 5: return new SimpleCheckFoldBot();
            default: return new SimpleCheckCallBot();
        }
    }

    // --- Helpers ---

    private static GameView randomScenario() {
        Card c1 = randomCard(), c2;
        do { c2 = randomCard(); } while (c1.equals(c2));
        Set<Card> used = new HashSet<>(Set.of(c1, c2));
        List<Card> board = new ArrayList<>();
        for (int i = 0, n = random.nextInt(4); i < n; i++) {
            Card c;
            do { c = randomCard(); } while (used.contains(c));
            board.add(c);
            used.add(c);
        }
        int bb = 10, toCall = random.nextBoolean() ? 0 : bb + random.nextInt(bb * 3);
        return new GameView(
                GameState.values()[random.nextInt(GameState.values().length)],
                random.nextInt(1000) + 100, toCall, toCall, bb,
                random.nextInt(2000) + 500, board, new HoleCards(c1, c2), false);
    }

    private static double decisionQuality(Decision d) {
        switch (d.action()) {
            case FOLD: return -0.1;  case CHECK: return 0.0;
            case CALL: return 0.2;   case BET: return 0.4;
            case RAISE: return 0.6;  default: return 0.0;
        }
    }

    private static Card randomCard() {
        return new Card(
                Rank.values()[random.nextInt(Rank.values().length)],
                Suit.values()[random.nextInt(Suit.values().length)]);
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Enter " + min + "-" + max + ".");
        }
    }

    private static int readIntWithDefault(String prompt, int def) {
        System.out.print(prompt + " (" + def + "): ");
        String s = scanner.nextLine().trim();
        if (s.isEmpty()) return def;
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return def; }
    }
}

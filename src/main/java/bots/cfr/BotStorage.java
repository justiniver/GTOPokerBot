package bots.cfr;

import bots.GTOPokerBot;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class BotStorage {
    private static final Path BOT_DIR = Paths.get("trained_bots");

    public static void save(GTOPokerBot bot, int iterations) {
        try {
            Files.createDirectories(BOT_DIR);
            Path path = BOT_DIR.resolve("gto_" + iterations + ".ser");
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
                out.writeObject(bot);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to save bot", e);
        }
    }

    public static GTOPokerBot load(Path file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            return (GTOPokerBot) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load bot from " + file, e);
        }
    }

    public static List<Path> listSavedBots() {
        if (!Files.isDirectory(BOT_DIR)) return Collections.emptyList();
        try (Stream<Path> files = Files.list(BOT_DIR)) {
            return files
                    .filter(p -> p.toString().endsWith(".ser"))
                    .sorted(Comparator.comparingInt(BotStorage::extractIterations))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static int extractIterations(Path file) {
        String name = file.getFileName().toString();
        return Integer.parseInt(name.replaceAll("[^0-9]", ""));
    }
}

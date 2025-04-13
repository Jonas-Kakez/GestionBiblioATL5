package data;

import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnregistrerJson {
    private static final String FICHIER_RESULTATS = "resultats.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void log(String type, String message) {
        JsonObject log = new JsonObject();
        log.addProperty("timestamp", LocalDateTime.now().toString());
        log.addProperty("type", type);
        log.addProperty("message", message);

        JsonArray logs = lireLogs();
        logs.add(log);
        sauvegarderLogs(logs);
    }

    private static JsonArray lireLogs() {
        try {
            File file = new File(FICHIER_RESULTATS);
            if (!file.exists()) return new JsonArray();

            String contenu = new String(Files.readAllBytes(file.toPath()));
            return JsonParser.parseString(contenu).getAsJsonArray();
        } catch (IOException e) {
            return new JsonArray();
        }
    }

    private static void sauvegarderLogs(JsonArray logs) {
        try (FileWriter writer = new FileWriter(FICHIER_RESULTATS)) {
            gson.toJson(logs, writer);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des logs : " + e.getMessage());
        }
    }

    public static void afficherLogs() {
        JsonArray logs = lireLogs();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (JsonElement element : logs) {
            JsonObject obj = element.getAsJsonObject();
            String type = obj.get("type").getAsString();
            String message = obj.get("message").getAsString();
            String timestamp = obj.get("timestamp").getAsString();

            String icone = switch (type) {
                case "Ajout Livre" -> "📘";
                case "Ajout Lecteur" -> "👤";
                case "Emprunt" -> "📖";
                case "Retour" -> "✅";
                case "Sanction" -> "⚠️";
                case "Chargement" -> "⬇️";
                case "Sauvegarde" -> "💾";
                default -> "📝";
            };

            String dateHeure = LocalDateTime.parse(timestamp).format(formatter);
            System.out.println(icone + " [" + type + "] " + message + " (" + dateHeure + ")");
        }
    }
}

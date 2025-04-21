package data;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class EnregistrerJson {

    private String nom;
    private static final String FICHIER_LOG = ConfigurationJson.FICHIER_LOG;

    public EnregistrerJson(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static void log(String action) {
        try {
            JsonArray logsArray = lireLogs();
            JsonObject log = new JsonObject();
            log.addProperty("date", LocalDateTime.now().toString());
            log.addProperty("action", action);
            logsArray.add(log);

            try (FileWriter writer = new FileWriter(FICHIER_LOG)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(logsArray, writer);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement du log : " + e.getMessage());
        }
    }

    public static void afficherLogs() {
        try {
            JsonArray logs = lireLogs();
            for (JsonElement element : logs) {
                JsonObject log = element.getAsJsonObject();
                System.out.println(log.get("date").getAsString() + " - " + log.get("action").getAsString());
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture des logs : " + e.getMessage());
        }
    }

    private static JsonArray lireLogs() throws IOException {
        File fichier = new File(FICHIER_LOG);
        if (!fichier.exists()) {
            return new JsonArray();
        }

        try (Reader reader = Files.newBufferedReader(Paths.get(FICHIER_LOG))) {
            JsonElement element = JsonParser.parseReader(reader);
            if (element != null && element.isJsonArray()) {
                return element.getAsJsonArray();
            } else {
                return new JsonArray(); // vide si fichier vide ou invalide
            }
        }
    }
}

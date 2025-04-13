package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Livre> catalogue = new ArrayList<>();
    private static final List<Lecteur> lecteurs = new ArrayList<>();
    private static final Bibliothecaire bibliothecaire = new Bibliothecaire("Mr Joseph");

    public static void main(String[] args) {
        chargerDonnees("donnees.json");
        System.out.println("Bienvenue dans la bibliothèque");

        while (true) {
            System.out.println("\n---MENU PRINCIPAL---");
            System.out.println("1. Bibliothécaire");
            System.out.println("2. Lecteur");
            System.out.println("3. Quitter");
            System.out.print("Choisir une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 3) {
                sauvegarderDonnees("donnees.json");
                System.out.println("Merci d'avoir utilisé la bibliothèque.");
                scanner.close();
                return;
            }

            switch (choix) {
                case 1 -> menuBibliothecaire();
                case 2 -> menuLecteur();
                default -> System.out.println("Option invalide, réessayez.");
            }
        }
    }

    private static void menuBibliothecaire() {
        while (true) {
            System.out.println("\n---MENU BIBLIOTHÉCAIRE---");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Ajouter un lecteur");
            System.out.println("3. Voir les lecteurs");
            System.out.println("4. Sanctionner un lecteur");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choisir une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> ajouterLivre();
                case 2 -> ajouterLecteur();
                case 3 -> afficherLecteurs();
                case 4 -> sanctionnerLecteur();
                case 5 -> { return; }
                default -> System.out.println("Option invalide");
            }
        }
    }

    private static void afficherLecteurs() {
        if (lecteurs.isEmpty()) {
            System.out.println("Aucun lecteur enregistré.");
            return;
        }
        System.out.println("\nListe des lecteurs :");
        for (Lecteur lecteur : lecteurs) {
            System.out.println("- " + lecteur.getNom() + " (Emprunts : " + lecteur.getNombreEmprunts() + ")");
        }
    }

    private static void menuLecteur() {
        if (lecteurs.isEmpty()) {
            System.out.println("Aucun lecteur enregistré. Demandez au bibliothécaire de vous ajouter.");
            return;
        }

        System.out.println("\nListe des lecteurs :");
        for (int i = 0; i < lecteurs.size(); i++) {
            System.out.println((i + 1) + ". " + lecteurs.get(i).getNom());
        }
        System.out.print("Sélectionnez votre numéro : ");
        int choixLecteur = scanner.nextInt() - 1;
        scanner.nextLine();

        if (choixLecteur < 0 || choixLecteur >= lecteurs.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Lecteur lecteur = lecteurs.get(choixLecteur);

        while (true) {
            System.out.println("\n---MENU LECTEUR---");
            System.out.println("1. Emprunter un livre");
            System.out.println("2. Retourner un livre");
            System.out.println("3. Retour au menu principal");
            System.out.print("Choisir une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> emprunterLivre(lecteur);
                case 2 -> retournerLivre(lecteur);
                case 3 -> { return; }
                default -> System.out.println("Option invalide.");
            }
        }
    }

    private static void ajouterLivre() {
        System.out.print("Titre du livre : ");
        String titre = scanner.nextLine();

        Livre livre = new Livre(titre) {
            @Override
            public void afficherDetails() {}
        };
        catalogue.add(livre);
        System.out.println("📘 Livre ajouté avec succès !");
    }

    private static void ajouterLecteur() {
        System.out.print("Nom du lecteur : ");
        String nom = scanner.nextLine();
        System.out.print("Numéro d'abonnement : ");
        int numAbonnement = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Adresse du lecteur : ");
        String adresse = scanner.nextLine();

        Lecteur lecteur = new Lecteur(nom, numAbonnement, adresse);
        lecteurs.add(lecteur);
        System.out.println("👤 Lecteur ajouté avec succès !");
    }

    private static void afficherCatalogue() {
        if (catalogue.isEmpty()) {
            System.out.println("Le catalogue est vide.");
            return;
        }
        System.out.println("\n📚 Catalogue des livres :");
        for (int i = 0; i < catalogue.size(); i++) {
            Livre livre = catalogue.get(i);
            System.out.println((i + 1) + ". " + livre.getTitre() + " (Disponible : " + livre.isDisponible() + ")");
        }
    }

    private static void emprunterLivre(Lecteur lecteur) {
        if (catalogue.isEmpty()) {
            System.out.println("Aucun livre n'est disponible.");
            return;
        }
        afficherCatalogue();
        System.out.print("Sélectionnez le livre à emprunter (numéro) : ");
        int choixLivre = scanner.nextInt() - 1;
        scanner.nextLine();

        if (choixLivre < 0 || choixLivre >= catalogue.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Livre livre = catalogue.get(choixLivre);
        if (!livre.isDisponible()) {
            System.out.println("Ce livre est déjà emprunté.");
        } else {
            lecteur.emprunterlivre(livre);
            livre.emprunter();
        }
    }

    private static void retournerLivre(Lecteur lecteur) {
        List<Livre> livresEmpruntes = lecteur.getLivresEmprunts();
        if (livresEmpruntes.isEmpty()) {
            System.out.println("Aucun livre à retourner.");
            return;
        }

        System.out.println("\nLivres empruntés par " + lecteur.getNom() + " :");
        for (int i = 0; i < livresEmpruntes.size(); i++) {
            System.out.println((i + 1) + ". " + livresEmpruntes.get(i).getTitre());
        }

        System.out.print("Sélectionnez le livre à retourner (numéro) : ");
        int choixLivre = scanner.nextInt() - 1;
        scanner.nextLine();

        if (choixLivre < 0 || choixLivre >= livresEmpruntes.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Livre livre = livresEmpruntes.get(choixLivre);
        lecteur.retournerLivre(livre);
        livre.retourner();
    }

    private static void sanctionnerLecteur() {
        if (lecteurs.isEmpty()) {
            System.out.println("Aucun lecteur enregistré.");
            return;
        }

        System.out.println("\nListe des lecteurs :");
        for (int i = 0; i < lecteurs.size(); i++) {
            System.out.println((i + 1) + ". " + lecteurs.get(i).getNom());
        }

        System.out.print("Sélectionnez le lecteur à sanctionner : ");
        int choix = scanner.nextInt() - 1;
        scanner.nextLine();

        if (choix < 0 || choix >= lecteurs.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        bibliothecaire.sanctionnerLecteur(lecteurs.get(choix));
    }

    // Méthode pour sauvegarder les données dans un fichier JSON
    public static void sauvegarderDonnees(String cheminFichier) {
        JsonArray livresArray = new JsonArray();
        for (Livre livre : catalogue) {
            JsonObject obj = new JsonObject();
            obj.add("titre", new JsonPrimitive(livre.getTitre()));
            obj.add("disponible", new JsonPrimitive(livre.isDisponible()));
            livresArray.add(obj);
        }

        JsonArray lecteursArray = new JsonArray();
        for (Lecteur lecteur : lecteurs) {
            JsonObject jsonObjet = new JsonObject();
            jsonObjet.add("nom", new JsonPrimitive(lecteur.getNom()));
            jsonObjet.add("nombreEmprunts", new JsonPrimitive(lecteur.getNombreEmprunts()));

            JsonArray livresEmpruntesArray = new JsonArray();
            for (Livre livre : lecteur.getLivresEmprunts()) {
                livresEmpruntesArray.add(new JsonPrimitive(livre.getTitre()));
            }
            jsonObjet.add("livresEmpruntes", livresEmpruntesArray);

            lecteursArray.add(jsonObjet);
        }

        JsonObject racine = new JsonObject();
        racine.add("livres", livresArray);
        racine.add("lecteurs", lecteursArray);

        try (FileWriter writer = new FileWriter(cheminFichier)) {
            writer.write(racine.toString());
            System.out.println("✅ Données sauvegardées !");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // Méthode pour charger les données depuis un fichier JSON
    public static void chargerDonnees(String cheminFichier) {
        File fichier = new File(cheminFichier);
        if (!fichier.exists()) return;

        try {
            String contenu = new String(Files.readAllBytes(fichier.toPath()));
            JsonObject racine = JsonParser.parseString(contenu).getAsJsonObject();

            JsonArray livresArray = racine.getAsJsonArray("livres");
            for (JsonElement element : livresArray) {
                JsonObject obj = element.getAsJsonObject();
                String titre = obj.get("titre").getAsString();
                boolean disponible = obj.get("disponible").getAsBoolean();
                Livre livre = new Livre(titre) {
                    @Override
                    public void afficherDetails() {}
                };
                livre.setDisponible(disponible);
                catalogue.add(livre);
            }

            JsonArray lecteursArray = racine.getAsJsonArray("lecteurs");
            for (JsonElement element : lecteursArray) {
                JsonObject obj = element.getAsJsonObject();
                String nom = obj.get("nom").getAsString();
                Lecteur lecteur = new Lecteur(nom);

                if (obj.has("livresEmpruntes")) {
                    JsonArray livresEmpruntesArray = obj.getAsJsonArray("livresEmpruntes");
                    for (JsonElement livreElem : livresEmpruntesArray) {
                        String titreLivre = livreElem.getAsString();
                        for (Livre livre : catalogue) {
                            if (livre.getTitre().equals(titreLivre)) {
                                lecteur.emprunterlivre(livre);
                                livre.emprunter();
                                break;
                            }
                        }
                    }
                }
                lecteurs.add(lecteur);
            }
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Erreur lors du chargement des données : " + e.getMessage());
        }
    }
}

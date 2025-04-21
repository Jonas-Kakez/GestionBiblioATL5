package data;

import java.util.*;
import com.google.gson.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Lecteur> lecteurs = new ArrayList<>();
    private static final Bibliothecaire bibliothecaire = new Bibliothecaire("Mr Joseph");
    private static final Bibliotheque bibliotheque = new Bibliotheque("Bibliothèque de Lubumbashi", "Femme Katangaise", "Lundi au Vendredi, de 8h-19h");
    private static final EnregistrerJson logger = new EnregistrerJson("resultats.json");
    private static final DatabaseManager dbManager = new DatabaseManager(); // Instance pour gérer la base de données

    public static void main(String[] args) {
        chargerDonnees("donnees.json");
        System.out.println("Bienvenue à la " + bibliotheque.getNom());
        System.out.println("Adresse : " + bibliotheque.getAdresse());
        System.out.println("Horaires : " + bibliotheque.getHoraire());

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
                logger.log("Programme terminé. Données sauvegardées.");
                System.out.println("Merci d'avoir utilisé la bibliothèque.");
                logger.afficherLogs();
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

    private static void menuLecteur() {
        // Menu Lecteur à définir (si nécessaire)
    }

    private static void menuBibliothecaire() {
        while (true) {
            System.out.println("\n---MENU BIBLIOTHÉCAIRE---");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Ajouter un lecteur");
            System.out.println("3. Voir les lecteurs");
            System.out.println("4. Sanctionner un lecteur");
            System.out.println("5. Supprimer un livre");
            System.out.println("6. Retour au menu principal");
            System.out.print("Choisir une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> ajouterLivre();
                case 2 -> ajouterLecteur();
                case 3 -> afficherLecteurs();
                case 4 -> sanctionnerLecteur();
                case 5 -> supprimerLivre();
                case 6 -> { return; }
                default -> System.out.println("Option invalide");
            }
        }
    }

    private static void ajouterLivre() {
        System.out.println("Quel type de livre souhaitez-vous ajouter ?");
        System.out.println("1. Roman");
        System.out.println("2. Biographie");
        System.out.println("3. Science Fiction");
        System.out.println("4. Magazine");
        System.out.print("Choisir une option : ");

        int choixType = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Titre du livre : ");
        String titre = scanner.nextLine();

        Livre livre = null;

        switch (choixType) {
            case 1 -> {
                System.out.print("Prix littéraire (true/false) : ");
                boolean prixLitteraire = scanner.nextBoolean();
                scanner.nextLine();
                System.out.print("Genre du roman : ");
                String genre = scanner.nextLine();
                livre = new Roman(titre, prixLitteraire, genre);
            }
            case 2 -> {
                System.out.print("Personnage principal : ");
                String personnage = scanner.nextLine();
                System.out.print("Période historique : ");
                String periodeHistorique = scanner.nextLine();
                System.out.print("Est-ce une autobiographie (true/false) : ");
                boolean estAutobiographie = scanner.nextBoolean();
                scanner.nextLine();
                livre = new Biographie(titre, personnage, periodeHistorique, estAutobiographie);
            }
            case 3 -> {
                System.out.print("Univers de science-fiction : ");
                String univers = scanner.nextLine();
                System.out.print("Technologie fictive : ");
                String technologieFictive = scanner.nextLine();
                System.out.print("Année future : ");
                int anneeFuture = scanner.nextInt();
                scanner.nextLine();
                livre = new ScienceFiction(titre, univers, technologieFictive, anneeFuture);
            }
            case 4 -> {
                System.out.print("Édition du magazine : ");
                String edition = scanner.nextLine();
                System.out.print("Périodicité du magazine : ");
                String periodicite = scanner.nextLine();
                System.out.print("Nom du magazine : ");
                String nomMagazine = scanner.nextLine();
                livre = new Magazine(titre, edition, periodicite, nomMagazine);
            }
            default -> {
                System.out.println("Option invalide.");
                return;
            }
        }

        bibliotheque.ajouterLivre(livre);
        dbManager.addLivre(livre);  // Ajout du livre dans la base de données
        System.out.println("Livre ajouté : " + titre);
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
        dbManager.addLecteur(lecteur);  // Ajout du lecteur dans la base de données
        System.out.println("Lecteur ajouté : " + nom);
    }

    private static void afficherLecteurs() {
        for (Lecteur lecteur : lecteurs) {
            System.out.println(lecteur);
        }
    }

    private static void sanctionnerLecteur() {
        // Code pour sanctionner un lecteur (non détaillé ici)
    }

    private static void supprimerLivre() {
        System.out.print("Entrez le titre du livre à supprimer : ");
        String titre = scanner.nextLine();
        Livre livre = bibliotheque.getLivreParTitre(titre);
        if (livre != null) {
            bibliotheque.supprimerLivre(livre);
            dbManager.removeLivre(livre);  // Supprimer le livre de la base de données
            System.out.println("Livre supprimé : " + titre);
        } else {
            System.out.println("Livre non trouvé.");
        }
    }

    // Charger les données JSON à partir d'un fichier
    private static void chargerDonnees(String fichier) {
        try {
            String content = new String(Files.readAllBytes(new File(fichier).toPath()));
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            // Charger des livres et lecteurs depuis le JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sauvegarder les données dans le fichier JSON
    private static void sauvegarderDonnees(String fichier) {
        try {
            JsonObject json = new JsonObject();
            // Ajouter les livres et lecteurs dans le JSON
            FileWriter writer = new FileWriter(fichier);
            writer.write(json.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

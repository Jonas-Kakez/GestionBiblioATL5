package data;

import java.util.ArrayList;
import java.util.List;

public class Lecteur extends Personne {

    private int numAbonnement;
    private String adresse;
    private List<Livre> livresEmpruntes;

    public Lecteur(String nom, int numAbonnement, String adresse) {
        super(nom);
        this.numAbonnement = numAbonnement;
        this.adresse = adresse;
        this.livresEmpruntes = new ArrayList<>();
    }

    public Lecteur(String nom) {
        super(nom);
        this.livresEmpruntes = new ArrayList<>();
    }

    public void emprunterlivre(Livre livre) {
        livresEmpruntes.add(livre);
        System.out.println(nom + " a emprunté " + livre.getTitre());
    }

    public void retournerLivre(Livre livre) {
        if (livresEmpruntes.remove(livre)) {
            System.out.println(nom + " a retourné " + livre.getTitre());
        } else {
            System.out.println(nom + " n'a pas ce livre.");
        }
    }

    public void consulterCatalogue() {
        System.out.println("📚 Livres empruntés par " + nom + " :");
        if (livresEmpruntes.isEmpty()) {
            System.out.println("- Aucun livre emprunté.");
        } else {
            for (Livre livre : livresEmpruntes) {
                System.out.println("- " + livre.getTitre());
            }
        }
    }

    public List<Livre> getLivresEmprunts() {
        return livresEmpruntes;
    }

    public int getNombreEmprunts() {
        return livresEmpruntes.size();
    }


    public int getNumAbonnement() {
        return numAbonnement;
    }

    public String getAdresse() {
        return adresse;
    }
}

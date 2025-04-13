package data;

import java.util.ArrayList;
import java.util.List;

public class Bibliotheque extends Personne{
    private String adresse;
    private String horaire;
    private List<Livre>livres;


    public Bibliotheque(String nom, String adresse,String horaire) {
        super(nom);
        this.adresse=adresse;
        this.horaire=horaire;
        this.livres=new ArrayList<>();

    }
    public void ajouterLivre(Livre livre){
        livres.add(livre);
        System.out.println("Livre ajoute: "+ livre.getTitre());
    }
    public void afficherCatalogue(){
        System.out.println("Catalogue de la bibliotheque :");
        for (Livre livre : livres){
            System.out.println("-" + livre.getTitre()+ "(Disponible :" + livre.isDisponible()+")");
        }
    }
}

package data;

public class Bibliothecaire extends Personne {

    public Bibliothecaire(String nom) {

        super(nom);
    }
    public  void accueillirLecteur(Lecteur lecteur){
        System.out.println("Bienvenue " + lecteur.getNom() + " a la bibliotheque ");
    }
    public void gererLivre(Livre livre){

        System.out.println("Gestion du livre :" + livre.getTitre());
    }
    public void santionnerLecteur( Lecteur lecteur){
        System.out.println("Attention " + lecteur.getNom() + " respectez les regles");
    }

    public void sanctionnerLecteur(Lecteur lecteur) {
        System.out.println("Attention " + lecteur.getNom() + " respectez les regles");

    }
    public void supprimerLivre(Livre livre){
        System.out.println(livre.getTitre() + " a ete supprimer du catalogue.");
    }
}

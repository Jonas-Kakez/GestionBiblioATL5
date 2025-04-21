package data;

public class Roman extends Livre {
    private boolean prixLitteraire;
    private String genre;

    public Roman(String titre,boolean prixLitteraire,String genre) {
        super(titre);
        this.prixLitteraire = prixLitteraire;
        this.genre = genre;
    }



    @Override
    public void afficherDetails() {
        System.out.println("Romaon : " + titre + ",Genre : " + genre + ",Prix litteraire: " + prixLitteraire);

    }
}

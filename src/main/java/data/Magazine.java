package data;

import java.util.Date;

public class Magazine extends Livre{
    private String edition;
    private String periodicite;
    private String nomMagazine;

    public Magazine(String titre,String edition,String periodicite,String nomMagazine) {
        super(titre);
        this.edition = edition;
        this.periodicite = periodicite;
        this.nomMagazine = nomMagazine;
    }

    public Magazine(String titre, String auteur, String isbn, Date datePublication, boolean disponible) {
        super(titre, auteur, isbn, datePublication, disponible);
    }

    @Override
    public void afficherDetails() {
        System.out.println("Magazine: " + titre + ",Edition: " + edition+ ",Periodicite: "+ periodicite+ ",Nom : "+ nomMagazine);
    }
}

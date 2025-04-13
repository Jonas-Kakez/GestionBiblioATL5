package data;

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


    @Override
    public void afficherDetails() {
        System.out.println("Magazine: " + titre + ",Edition: " + edition+ ",Periodicite: "+ periodicite+ ",Nom : "+ nomMagazine);
    }
}

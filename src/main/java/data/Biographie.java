package data;

public class Biographie extends Livre{
    private String personnage;
    private String periodeHistorique;
    private boolean estAutobiographie;

    public Biographie(String titre,String personnage, String periodeHistorique, boolean estAutobiographie) {
        super(titre);
        this.personnage = personnage;
        this.periodeHistorique = periodeHistorique;
        this.estAutobiographie = estAutobiographie;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Biographie : "+ titre + ", Personnage: "+ personnage +", Periode: "+ periodeHistorique + ",Autobiographie: "+estAutobiographie);

    }
}

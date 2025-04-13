package data;

public class ScienceFiction extends Livre{
    private String univers;
    private String technologieFictive;
    private int anneeFuture;

    public ScienceFiction(String titre, String univers, String technologieFictive, int anneeFuture ) {
        super(titre);
        this.univers = univers;
        this.technologieFictive = technologieFictive;
        this.anneeFuture = anneeFuture;

    }


    @Override
    public void afficherDetails() {
        System.out.println("Science Fiction : " + titre + ",Univers : "+  univers + ",Technologie: "+ technologieFictive+ ",Annee: " + anneeFuture);
    }
}

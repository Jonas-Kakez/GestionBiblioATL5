package data;

import java.util.Date;

public abstract class Livre {

    protected String titre;
    private String auteur;
    private String isbn;
    private Date datePublication;
    protected boolean disponible = true;

    public Livre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }

    public boolean isDisponible() {
        return disponible;
    }
    public void emprunter(){
        if (disponible){
            disponible = false;
            System.out.println(titre + "a ete emprunte");
        }else {
            System.out.println(titre + " n'est pas disponible");
        }
    }
    public void retourner(){
        disponible = true;
        System.out.println(titre + " a ete retiurne");
    }
    public abstract void afficherDetails();

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

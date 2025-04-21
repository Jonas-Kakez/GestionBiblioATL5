package data;

import java.util.Date;

public abstract class Livre {

    protected String titre;
    private String auteur;
    private String isbn;
    private Date datePublication;

    public Date getDatePublication() {
        return datePublication;
    }

    protected boolean disponible = true;

    @Override
    public String toString() {
        return "Livre{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", isbn='" + isbn + '\'' +
                ", datePublication=" + datePublication +
                ", disponible=" + disponible +
                '}';
    }

    public Livre(String titre, String auteur, String isbn, Date datePublication, boolean disponible) {
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.datePublication = datePublication;
        this.disponible = disponible;
    }

    public Livre(String titre) {
        this.titre = titre;
    }

    public Livre(String titre, boolean disponible) {
    }


    public String getAuteur() {
        return auteur;
    }

    public String getIsbn() {
        return isbn;
    }


    public String getTitre() {
        return titre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void emprunter() {
        if (disponible) {
            disponible = false;
            System.out.println(titre + " a été emprunté");
        } else {
            System.out.println(titre + " n'est pas disponible");
        }
    }

    public void retourner() {
        disponible = true;
        System.out.println(titre + " a été retourné");
    }


    public abstract void afficherDetails();
}


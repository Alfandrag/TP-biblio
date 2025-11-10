package org.example.tpbiblio.entity;

public class Book_main {
    public String title;
    public String auteur;
    public String Editeur;
    public String annee_parution;
    public String Genres;
    public int nombre_copies;

    public Book_main(String titre, String auteur, String Editeur, String annee_parution,
                     String genre, int nombre_copies) {
        this.title = titre;
        this.auteur = auteur;
        this.annee_parution = annee_parution;
        this.Editeur = Editeur;
        this.Genres = genre;
        this.nombre_copies = nombre_copies;

    }

    public String getTitle() { return title; }
    public String getAuteur() { return auteur; }
    public String getEditeur() { return Editeur; }
    public String getAnnee_parution() { return annee_parution; }
    public String getGenres() { return Genres; }
    public int getNombre_copies() { return nombre_copies; }

}
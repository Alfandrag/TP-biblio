package org.example.tpbiblio.entity;

public class Book_loan {
    public String title;
    public String auteur;
    public String date_emprunt;
    public String date_retour_theorique;
    public String Etat;
    public String Editeur;
    public String loaner;

    public Book_loan(String titre, String auteur, String date_emprunt,
                     String date_retour_theorique, String Etat, String Editeur, String loaner) {
        this.title = titre;
        this.auteur = auteur;
        this.date_emprunt = date_emprunt;
        this.date_retour_theorique = date_retour_theorique;
        this.Etat = Etat;
        this.Editeur = Editeur;
        this.loaner = loaner;
    }

    public String getTitle() { return title; }
    public String getAuteur() { return auteur; }
    public String getDate_emprunt() { return date_emprunt; }
    public String getDate_retour() { return date_retour_theorique; }
    public String getEtat() { return Etat; }
    public String getEditeur() { return Editeur; }
    public String getLoaner() { return loaner; }
}

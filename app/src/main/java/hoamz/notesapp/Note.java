package hoamz.notesapp;

import java.io.Serializable;

public class Note implements Serializable {
    private String tittle,description,date;
    private int id;
    private int loveStar;

    public Note(int id,String tittle,String description,String date,int loveStar){
        this.description = description;
        this.tittle = tittle;
        this.id = id;
        this.date = date;
        this.loveStar = loveStar;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setLoveStar(int loveStar) {
        this.loveStar = loveStar;
    }

    public int getLoveStar() {
        return loveStar;
    }
}

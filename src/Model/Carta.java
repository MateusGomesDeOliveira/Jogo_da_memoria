package Model;

import javax.swing.*;
import static Model.Campo.cartas;

public class Carta {
    private final ImageIcon imageIcon;
    private final int id;
    public Carta(int id,String imageDire){
        this.id = id;
        imageIcon = new ImageIcon(imageDire);
        cartas.add(this);
    }
    public ImageIcon getImageIcon() {
        return imageIcon;
    }
    public int getId() {
        return id;
    }
}
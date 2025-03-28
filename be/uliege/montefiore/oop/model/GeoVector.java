package be.uliege.montefiore.oop.model;

public class GeoVector extends Coordinates{

    public GeoVector(Coordinates c1, Coordinates c2){
        super(c2.xpos - c1.xpos, c2.ypos - c1.ypos);
    }
}

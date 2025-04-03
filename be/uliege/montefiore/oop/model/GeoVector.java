package be.uliege.montefiore.oop.model;

public class GeoVector extends Coordinates{

    public GeoVector(Coordinates c1, Coordinates c2){
        super(c2.xpos - c1.xpos, c2.ypos - c1.ypos);
    }

    // function to verify that the vector goes through exactly one axis
    public boolean isSingleAxis(){
        if(xpos == 0 && ypos == 0)
            return false;

        return xpos == 0 || ypos == 0;
    }
}

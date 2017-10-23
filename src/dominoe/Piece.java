package dominoe;

import java.util.ArrayList;

public class Piece {

    private int left;
    private int rigth;

    public Piece(int left, int rigth) {
        this.left = left;
        this.rigth = rigth;
    }

    public int getLeft() {
        return left;
    }

    public int getRigth() {
        return rigth;
    }

    public ArrayList<Integer> getValues() {
        ArrayList values = new ArrayList<>();
        values.add(left);
        values.add(rigth);
        return values;
    }

    @Override
    public boolean equals(Object o) {
        Piece aux = (Piece) o;
        return aux.getLeft() == left && aux.getRigth() == rigth;
    }

    @Override
    public String toString() {
        return "[ " + left + " | " + rigth + " ]";
    }
}

package Lab4and5;

import java.util.Arrays;

public class Whole
{
    public double One;
    public String op;
    public double Two;

    public Whole(double One, String op, double Two)
    {
        this.One = One;
        this.op = op;
        this.Two = Two;
    }

    @Override
    public String toString()
    {
        return "Number1: " + One + "; Operation: " + op + "; Number2: " + Two;
    }
}

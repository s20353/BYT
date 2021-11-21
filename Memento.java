package Lab4and5;

import java.util.*;

public class Memento
{
    public static ArrayList<Double> list = new ArrayList<Double>();

    private static int command = 0;


    public void save(double x)
    {
        list.add(x);
        command++;
    }

    public double load()
    {
        double x =  list.get(command-1);
        list.remove(command-1);
        command--;
        return x;
    }


}

package Lab4and5;

import java.util.Scanner;

public class Main
{
    public static Whole zad;
    public static double result=0;
    public static boolean first = true;
    private static Addition adding = new Addition();
    private static Substraction substracting = new Substraction();
    private static Multiplication multiplying = new Multiplication();
    private static Division dividing = new Division();

    private static Memento changeling = new Memento();

    static
    {
        adding.setNextHandler(substracting);
        substracting.setNextHandler((multiplying));
        multiplying.setNextHandler(dividing);
    }

    public void runTheMath(Whole zad)
    {
        adding.doAFlip(zad);
    }

    public static boolean validateCommand(String input)
    {
        if (first && input.length()<3)
        {
            System.out.println("Command missing... Please enter an operation:");
            return false;
        }
        else if (!first && input.length()<2)
        {
            System.out.println("Command missing... Please enter an operation:");
            return false;
        }
        return true;
    }

    public static Whole detectFirstOP(String input)
    {
        String One = "";
        String Two = "";
        String op = "";
        String temp = "";

        //System.out.println("Input: " + input);
        One = detectNumber(input);
        //System.out.println("One detected: " + One);

        temp = input.substring(One.length());
        //System.out.println("temp after One: " + temp);

        op = op + temp.substring(0, 1);
        //System.out.println("Op: " + op);
        temp = temp.substring(1);
        //System.out.println("temp after op: " + temp);

        Two = detectNumber(temp);
        //System.out.println("Two: " + Two);

        Whole zad = new Whole(Double.parseDouble(One), op, Double.parseDouble(Two));
        return zad;
    }

    public static Whole nextOP(double x, String input)
    {
        String Two = "";
        String op = "";
        String temp = "";

        op = op + input.substring(0, 1);
        //System.out.println("Op: " + op);
        temp = input.substring(1);
        //System.out.println("temp after op: " + temp);

        Two = detectNumber(temp);
        //System.out.println("Two: " + Two);

        Whole zad = new Whole(x, op, Double.parseDouble(Two));
        return zad;
    }

    public static String detectNumber(String input)
    {
        String x = "";
        boolean doubled = false;

        input = input.replaceAll(",", ".");

        for (int i=0; i<input.length(); i++)
        {
            //minus na początku
            if((i==0 && (input.charAt(i) == '-')))
            {
                String val = input.substring(i, i+1);
                x = x + val;
                continue;
            }

            //ułamki i multikropki
            if (input.charAt(i) == '.' && !doubled)
            {
                doubled = true;
                String val = input.substring(i, i+1);
                x = x + val;
                continue;
            }
            else if (input.charAt(i) == '.' && doubled)
            {
                System.out.println("SYNTAX ERROR: multiple points in number! (Ignored)");
                continue;
            }


            char ch = input.charAt(i);
            if (Character.isDigit(ch))
            {
                String val = input.substring(i, i+1);
                x = x + val;
            }
            else
            {
                return x;
            }

        }

        return x;
    }

    public static void quicksave()
    {
        changeling.save(result);
    }

    public static void quickload()
    {
        result = changeling.load();
        System.out.println("Result is back to: " + result);
    }

    public static void printResult()
    {
        System.out.println(zad.One + zad.op + zad.Two + "=" + result);
    }

    public static void main(String[] args)
    {
        Scanner scannedLine = new Scanner(System.in);
        String input = scannedLine.nextLine();

        validateCommand(input);
        first = false;
        quicksave();
        zad = detectFirstOP(input);
        result = adding.doAFlip(zad);
        printResult();

        //continue
        while(true)
        {

            input = scannedLine.nextLine();
            validateCommand(input);
            if (input.equals("END"))
            {
                break;
            }
            else if (input.equals("PREV"))
            {
                quickload();
                continue;
            }

            quicksave();


            zad = nextOP(result, input);
            result = adding.doAFlip(zad);

            printResult();
        }

        System.out.println("Hoped you liked the calculator!");
    }
}

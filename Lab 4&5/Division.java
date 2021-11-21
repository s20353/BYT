package Lab4and5;

public class Division extends Handler
{
    public String symbol = "/";

    @Override
    public double doAFlip(Whole request)
    {
        //System.out.println("Entered Division");
        if (confirmOP(symbol, request))
        {
            showingOff(request.Two);
            return (request.One / request.Two);

        }
        else
        {
            System.out.println("ERROR: Detected an incorrect sign. Operation reverted.");
            return 0;
        }
    }

    //template
    @Override
    public void showingOff(double x)
    {
        if (x<1)
        {
            System.out.println("The result will be increased");
        }
        else
        {
            System.out.println("The result will be decreased");
        }

    }

}

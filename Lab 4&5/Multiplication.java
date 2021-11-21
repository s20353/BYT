package Lab4and5;

public class Multiplication extends Handler
{
    public String symbol = "*";

    @Override
    public double doAFlip(Whole request)
    {
        //System.out.println("Entered Multiplication");
        if (confirmOP(symbol, request))
        {
            showingOff(request.Two);
            return (request.One * request.Two);

        }
        else
        {
            return nextHandler.doAFlip(request);
        }
    }

    //template
    @Override
    public void showingOff(double x)
    {
        if (x>1)
        {
            System.out.println("The result will be increased");
        }
        else
        {
            System.out.println("The result will be decreased");
        }

    }

}

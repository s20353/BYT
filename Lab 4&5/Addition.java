package Lab4and5;

public class Addition extends Handler
{
    public String symbol = "+";

    @Override
    public double doAFlip(Whole request)
    {
        //System.out.println("Entered Addition");
        if (confirmOP(symbol, request))
        {
            showingOff(request.Two);
            return (request.One + request.Two);

        }
        else
        {
            return nextHandler.doAFlip(request);
        }
    }
}

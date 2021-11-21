package Lab4and5;

public abstract class Handler
{
    public String symbol;
    Handler nextHandler;

    public void setNextHandler(Handler justGoNext)
    {
        nextHandler = justGoNext;
    }

    public abstract double doAFlip(Whole request);

    public boolean confirmOP(String symbol, Whole given)
    {
        return given.op.equals(symbol);
    }

    //template method
    public void showingOff(double x)
    {
        if (x > 0)
        {
            System.out.println("The result will be increased");
        } else
        {
            System.out.println("The result will be decreased");
        }

    }

}

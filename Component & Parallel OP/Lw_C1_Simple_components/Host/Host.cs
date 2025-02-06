namespace Host;

public class Host : IFeeder
{
    public int GiveFood() => 1;
    public int Pet() => 1;
    public int ProvideCare() => 1;
    public int CallDog()
    {
        Dog.Dog myDog = new Dog.Dog();
        for (int i = 1; i < 3; i++)
        {
            myDog.Run();
            myDog.WagTail();
            myDog.DemandFood();
        }
        return 1;
    }
}

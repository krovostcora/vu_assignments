using System;

namespace Host
{
    class Program
    {
        static void Main()
        {
            Host owner = new Host();
            owner.CallDog();
            owner.Pet();
            owner.GiveFood();
        }
    }
}
using System;
namespace Dog
{
    public class Dog : IDog
    {
        public void Bark() => Console.WriteLine("Woof woof woof!");
        public void Growl() => Console.WriteLine("Grrrrr...");
        public int Bite(object target)
        {
            Random generator = new Random();
            return generator.Next(0, 2); // 0 – no bite, 1 – bite
        }
        public void Run() => Console.WriteLine("The dog is running");
        public void WagTail() => Console.WriteLine("The dog wags its tail");
        public void DemandFood() => Console.WriteLine("The dog comes to it's bowl and looks for food");
    }
}
namespace TemperatureSensor
{
    public class TemperatureSensor : ITemperatureSensor
    {
        private readonly Random _generator = new Random();

        public int GetTemperature()
        {
            return _generator.Next(-40, 45);
        }

        public bool IsRaining()
        {
            return _generator.Next(0, 2) == 1;
        }

        public bool IsSunny()
        {
            return _generator.Next(0, 2) == 1;
        }

        public int GetHumidity()
        {
            return _generator.Next(5, 80);
        }
    }
}
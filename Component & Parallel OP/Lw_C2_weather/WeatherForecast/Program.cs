using TemperatureSensor;
using WeatherForecast;

namespace WeatherForecastApp;

class Program
{
    static void Main()
    {
        ITemperatureSensor sensor = new TemperatureSensor.TemperatureSensor();
        IWeatherForecast forecast = new WeatherForecast.WeatherForecast(sensor);
        
        forecast.ShowTemperature();
        Console.WriteLine($"Humidity: {forecast.GetHumidity()}%");
        Console.WriteLine($"Raining: {forecast.IsRaining()}");
        Console.WriteLine($"Snowing: {forecast.IsSnowing()}");
        Console.WriteLine($"Cloudy: {forecast.IsCloudy()}");
        Console.WriteLine($"Sunny: {forecast.IsSunny()}");
    }
}
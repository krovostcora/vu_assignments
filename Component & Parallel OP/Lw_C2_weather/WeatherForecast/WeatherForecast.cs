using TemperatureSensor;

namespace WeatherForecast;

public class WeatherForecast : IWeatherForecast
{
    private readonly ITemperatureSensor _sensor;

    public WeatherForecast(ITemperatureSensor sensor)
    {
        _sensor = sensor;
    }

    public void ShowTemperature()
    {
        Console.WriteLine($"Current temperature: {_sensor.GetTemperature()}°C");
    }

    public int GetTemperature()
    {
        return _sensor.GetTemperature();
    }

    public bool IsSnowing()
    {
        return _sensor.GetTemperature() < 0 && _sensor.IsRaining();
    }

    public bool IsRaining()
    {
        return _sensor.IsRaining();
    }
    public bool IsCloudy()
    {
        return !_sensor.IsSunny();
    }
    public bool IsSunny()
    {
        return _sensor.IsSunny();
    }
    
    public int GetHumidity()
    {
        return _sensor.GetHumidity();
    }
}
using System;
using TemperatureSensor;

namespace WeatherForecast;

public interface IWeatherForecast
{
    void ShowTemperature();
    int GetTemperature();
    bool IsSnowing();
    bool IsRaining();
    bool IsCloudy();
    bool IsSunny();
    int GetHumidity();
}
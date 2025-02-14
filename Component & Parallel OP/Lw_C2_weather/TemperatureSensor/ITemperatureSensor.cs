namespace TemperatureSensor;

public interface ITemperatureSensor
{
    int GetTemperature();
    bool IsRaining();
    bool IsSunny();
    int GetHumidity();
}
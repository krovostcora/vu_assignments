using System;
using System.Globalization;
using System.Net.Sockets;
using System.Text;

class WeatherForecastClient
{
    static void Main()
    {
        var client = new TcpClient("localhost", 333);
        var stream = client.GetStream();
        var buffer = new byte[1024];
        var bytesRead = stream.Read(buffer, 0, buffer.Length);
        var temperatureData = Encoding.ASCII.GetString(buffer, 0, bytesRead);
        
        // Define English culture to ensure English day names
        var englishCulture = new CultureInfo("en-US");
        
        Console.WriteLine($"Temperature for: ");
        // Print temperature for each day
        for (int i = 0; i < 5; i++)
        {
            // Calculate the date (Today, Tomorrow, etc.)
            DateTime currentDate = DateTime.Today.AddDays(i);
            string formattedDate = currentDate.ToString("dddd, dd/MM", englishCulture);
            Console.WriteLine($"{formattedDate}: {temperatureData}°C");
        }
        client.Close();
    }
}
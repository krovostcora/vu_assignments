using System.Net;
using System.Net.Sockets;
using System.Text;

class TemperatureSensorServer
{
    static void Main()
    {
        var listener = new TcpListener(IPAddress.Any, 333);
        listener.Start();
        Console.WriteLine("Server started...");

        while (true)
        {
            var client = listener.AcceptTcpClient();
            var stream = client.GetStream();
            
            for (int i = 0; i < 5; i++)
            {
                var temperatureData = GetTemperatureData();
                var data = Encoding.ASCII.GetBytes(temperatureData.ToString());
                stream.Write(data, 0, data.Length);
                System.Threading.Thread.Sleep(1000); 
            }

            client.Close();
        }
    }

    static double GetTemperatureData()
    {
        Random generator = new Random();
        return generator.Next(-40, 45); 
    }
}
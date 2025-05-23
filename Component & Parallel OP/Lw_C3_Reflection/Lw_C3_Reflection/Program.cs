using System.Reflection;

namespace Lw_C3;

class Program
{
    private static void Main()
    {
        const string dllPath = "K3.dll"; 
        var assembly = Assembly.LoadFrom(dllPath);

        Console.WriteLine($"Loaded assembly: {assembly.GetName().Name}\n");
        
        var translation = new Dictionary<string, string> // I'm  sorry, I felt like I needed to do it
        {
            {"Kniaukti", "Meow"},
            {"Murkti", "Purr"},
            {"Selinti", "Sneak"},
            {"Begti", "Run"},
            {"VizgintiUodega", "WagTail"}
        };

        foreach (var type in assembly.GetTypes())
        {
            Console.WriteLine($"Class: {type.Name}");
            
            Console.WriteLine(" Methods:");
            
            Console.WriteLine("  Methods:");
            foreach (var method in type.GetMethods())
            {
                var translatedName = translation.TryGetValue(method.Name, out var value) ? value : method.Name;
                var parameters = string.Join(", ",
                    method.GetParameters().Select(p => $"{p.ParameterType.Name} {p.Name}"));
                Console.WriteLine($"    {translatedName} ({parameters})");
            }
        
            Console.WriteLine("Properties:");
            foreach (var prop in type.GetProperties(
                         BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance))
            {
                Console.WriteLine($"  {prop.Name} ({prop.PropertyType.Name})");
            }
            
            Console.WriteLine();
        }

        // call at least one method of that component with a parameter
        
        var targetType = assembly.GetType("Kate.Class1"); // class Class1
        if (targetType == null) return;

        var targetMethod = targetType.GetMethod("Equals"); // Equals method 
        if (targetMethod == null) return;
        {
            var instance = Activator.CreateInstance(targetType)!;
            var result = targetMethod.Invoke(instance, new object[] { "parameter" }); // pass the parameter
            Console.WriteLine($"Result of Equals: {result}");
        }

        
        var targetType2 = assembly.GetType("Kate.Class1"); // class Class1
        if (targetType2 == null) return;
        {
            var targetMethod2 = targetType2.GetMethod("GetHashCode"); // GetHashCode method 
            if (targetMethod2 == null) return;
            var instance = Activator.CreateInstance(targetType2)!;
            var result = targetMethod2.Invoke(instance, null); // null, bcs no parameters
            Console.WriteLine($"Result of GetHashCode: {result}");
        }
    }
    
}
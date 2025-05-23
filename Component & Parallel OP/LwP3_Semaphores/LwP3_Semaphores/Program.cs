namespace Lw3_Semaphores;

internal abstract class Program
{
    // Define the semaphore with a maximum of 2 threads allowed to access the critical section
    private static readonly Semaphore Semaphore = new Semaphore(2, 2);
    private static readonly Random Rand = new Random();
    private static string filePath = "RandomCharsOutput.txt";

    private static void Main()
    {
        // Number of threads that will write to the file
        Console.Write("How many chars you want to have: ");
        var numThreads = int.Parse(Console.ReadLine() ?? throw new InvalidOperationException());

        // Create and start threads
        var threads = new Thread[numThreads];
        for (var i = 0; i < numThreads; i++)
        {
            threads[i] = new Thread(WriteRandomCharacterToFile);
            threads[i].Start();
        }

        // Wait for all threads to finish
        foreach (var thread in threads)
        {
            thread.Join();
        }

        Console.WriteLine("Random characters have been written to the file.");
    }

    private static void WriteRandomCharacterToFile()
    {
        // Acquire the semaphore to ensure that only a limited number of threads write to the file at the same time
        Semaphore.WaitOne();

        try
        {
            // Write a random character to the file
            var randomChar = Rand.Next(0, 2) == 0
                ? (char)Rand.Next('A', 'Z' + 1)  
                : (char)Rand.Next('a', 'z' + 1); 
            
            using (var writer = new StreamWriter(filePath, append: true))
            {
                writer.Write(randomChar);
            }
            Console.WriteLine($"Written character: {randomChar}");
        }
        finally
        {
            // Release the semaphore, allowing another thread to proceed
            Semaphore.Release();
        }
    }
}
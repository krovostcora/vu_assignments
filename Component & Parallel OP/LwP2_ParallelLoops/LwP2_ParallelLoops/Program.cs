namespace LwP2_ParallelLoops;

internal abstract class Program
{
    private static void Main()
    {
        // Get the number of elements and threads from the user
        Console.Write("Enter the number of elements in the array: ");
        var numElements = int.Parse(Console.ReadLine() ?? string.Empty);
        
        Console.Write("Enter the number of threads: ");
        var numThreads = int.Parse(Console.ReadLine() ?? throw new InvalidOperationException());

        // Generate an array of random numbers
        var rand = new Random();
        var array = new int[numElements];
        for (var i = 0; i < numElements; i++)
        {
            array[i] = rand.Next(13, 133);
        }

        // Output the array (optional)
        Console.WriteLine("Array:");
        foreach (var num in array)
        {
            Console.Write(num + " ");
        }
        Console.WriteLine();

        // Sum the array elements in parallel using multiple threads
        var sum = ParallelSum(array, numThreads);

        // Output the result
        Console.WriteLine($"The sum of the array elements is: {sum}");
    }

    private static int ParallelSum(int[] array, int numThreads)
    {
        var length = array.Length;
        var chunkSize = (int)Math.Ceiling((double)length / numThreads);
        var sum = 0;

        // Perform parallel processing
        Parallel.For(0, numThreads, (i) =>
        {
            var startIndex = i * chunkSize;
            var endIndex = Math.Min(startIndex + chunkSize, length);
            var localSum = 0;

            for (var j = startIndex; j < endIndex; j++)
            {
                localSum += array[j];
            }

            // Use lock to update the sum safely
            lock (array)
            {
                sum += localSum;
            }
        });

        return sum;
    }
}
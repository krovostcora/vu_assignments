using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using System.Threading.Tasks;

internal class Program
{
    static string saveFile = "game_state.json";

    // list of questions, shuffled randomly
    private static List<(string Country, string Capital)> questions = new List<(string, string)>
    {
        ("Lithuania", "Vilnius"),
        ("Ukraine", "Kyiv"),
        ("Spain", "Madrid"),
        ("Croatia", "Zagreb"),
        ("Poland", "Warsaw"),
        ("France", "Paris"),
        ("Germany", "Berlin"),
        ("Italy", "Rome"),
        ("Japan", "Tokyo")
    }.OrderBy(_ => new Random().Next()).ToList();
    
    // reads user input with a timer
    private static async Task<string?> GetUserInp(int timeoutSeconds)
    {
        var tcs = new TaskCompletionSource<string>();
        
        _ = Task.Run(() => // in a separate task to avoid blocking
        {
            var input = Console.ReadLine();
            if (input != null) tcs.TrySetResult(input);
        });

        // wait for input or timeout
        var delayTask = Task.Delay(timeoutSeconds * 1000);
        var completedTask = await Task.WhenAny(tcs.Task, delayTask);

        if (completedTask == tcs.Task)
            return tcs.Task.Result; // user answered in time
    
        Console.WriteLine("Time's up!");
        return null; // time is out
    }

    // store game progress
    private class GameState
    {
        public int CurrentQuestion { get; set; } = 0;
        public int Score { get; set; } = 0;
    }

    private static async Task Main()
    {
        while (true)
        {
            var state = LoadGame();
        
            Console.WriteLine("\nWelcome to the Country-Capital Game!\nYou will have 20 seconds for each question.");
            Console.WriteLine("Type 'stop' to exit the game or 'restart' to start over.");

            while (state.CurrentQuestion < questions.Count)
            {
                Console.WriteLine($"Score: {state.Score}/{state.CurrentQuestion}");
                Console.WriteLine($"What is the capital of {questions[state.CurrentQuestion].Country}?");

                var answer = await GetUserInput(20);

                if (answer == null)
                {
                    // if time runs out, move to the next question
                }
                else if (answer.Equals("stop", StringComparison.OrdinalIgnoreCase))
                {
                    Console.WriteLine("Game stopped. Progress saved.");
                    SaveGame(state);
                    return;
                }
                else if (answer.Equals("restart", StringComparison.OrdinalIgnoreCase))
                {
                    Console.WriteLine("Restarting the game...");
                    File.Delete(saveFile); // reset progress
                    Main().Wait(); // restart the game
                    return;
                }
                else if (answer.Equals(questions[state.CurrentQuestion].Capital, StringComparison.OrdinalIgnoreCase))
                {
                    Console.WriteLine("Correct!");
                    state.Score++;
                }
                else
                {
                    Console.WriteLine($"Wrong! The correct answer is {questions[state.CurrentQuestion].Capital}.");
                }

                state.CurrentQuestion++;
                SaveGame(state);
            }

            Console.WriteLine($"Game over! Your final score: {state.Score}/{questions.Count}");
            File.Delete(saveFile); // Clear saved progress
            return;
        }
    }

    // reads user input with a timeout, stops waiting if input is provided earlier
    private static async Task<string?> GetUserInput(int timeoutSeconds)
    {
        var tcs = new TaskCompletionSource<string>();

        // run Console.ReadLine() in a separate task to avoid blocking
        _ = Task.Run(() =>
        {
            var input = Console.ReadLine();
            if (input != null) tcs.TrySetResult(input);
        });

        // wait for input or timeout
        var delayTask = Task.Delay(timeoutSeconds * 1000);
        var completedTask = await Task.WhenAny(tcs.Task, delayTask);

        if (completedTask == tcs.Task)
            return tcs.Task.Result; // user answered in time
    
        Console.WriteLine("Time's up!");
        return null; // time is out
    }

    // loads saved game state from a file
    private static GameState LoadGame()
    {
        if (!File.Exists(saveFile)) return new GameState();
        var json = File.ReadAllText(saveFile);
        return JsonSerializer.Deserialize<GameState>(json) ?? new GameState();
    }

    // saves game state to a file
    private static void SaveGame(GameState state)
    {
        var json = JsonSerializer.Serialize(state, new JsonSerializerOptions { WriteIndented = true });
        File.WriteAllText(saveFile, json);
    }
}

import java.util.Scanner;

class PerfectNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the upper limit: ");
        int n = scanner.nextInt();
        scanner.close();

        System.out.println("Perfect numbers less than " + n + ":");
        findPerfectNumbers(n);
    }

    private static void findPerfectNumbers(int n) {
        for (int i = 1; i < n; i++) {
            if (isPerfectNumber(i)) {
                System.out.println(i);
            }
        }
    }

    private static boolean isPerfectNumber(int number) {
        int sumOfDivisors = 0;
        for (int divisor = 1; divisor < number; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor;
            }
        }
        return sumOfDivisors == number;
    }
}
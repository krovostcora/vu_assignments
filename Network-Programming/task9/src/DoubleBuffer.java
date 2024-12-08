import java.nio.DoubleBuffer;

class FunctionBuffer {
    public static void main(String[] args) {
        // Create a DoubleBuffer of size 41 (for x = -10 to 10 with step 0.5, total 41 values)
        DoubleBuffer buf = DoubleBuffer.allocate(41);

        // Fill the buffer with f(x) = 5 * x^2 - 3 * x + 2
        double x = -10.0;
        for (int i = 0; i < buf.capacity(); i++) {
            double fx = 5 * x * x - 3 * x + 2;
            buf.put(fx);
            x += 0.5;
        }

        // Read subbuffer range [0, 5) using slice()
        buf.position(0).limit(6);
        DoubleBuffer subBuffer = buf.slice();
        x = -10.0; // Reset x for subbuffer
        System.out.println("Subbuffer values (x f(x)):");
        for (int i = 0; i < subBuffer.capacity(); i++) {
            System.out.printf("%5.1f %10.4f%n", x, subBuffer.get(i));
            x += 0.5;
        }

        // Read all values in the buffer for which x is an integer
        buf.clear(); // Reset buffer position for reading all values
        x = -10.0;
        System.out.println("\nValues for integer x (x f(x)):");
        for (int i = 0; i < buf.capacity(); i++) {
            if (x == Math.floor(x)) { // Check if x is an integer
                System.out.printf("%5.1f %10.4f%n", x, buf.get(i));
            }
            x += 0.5;
        }
    }
}

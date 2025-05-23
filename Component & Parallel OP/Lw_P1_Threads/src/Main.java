    public class Main extends Thread {
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getId() + " says: Hello!");
            System.out.println("Thread " + Thread.currentThread().getId() + " finished.");
        }

        public static void main(String[] args) {
            Main thread1 = new Main();
            Main thread2 = new Main();
            Main thread3 = new Main();

            thread1.start();
            thread2.start();
            thread3.start();
        }
    }

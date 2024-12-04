package thirdTask;

public class Test2 {
    public static void main(String[] args) {
        // Create an array of 5 Cone objects
        Cones[] cones = new Cones[5];
        cones[0] = new Cones(2, 3);
        cones[1] = new Cones(4, 5);
        cones[2] = new Cones(1, 2);
        cones[3] = new Cones(3, 6);
        cones[4] = new Cones(5, 1);

        // Find the cone with the smallest volume
        Cones smallestCones = cones[0];
        for (int i = 1; i < cones.length; i++) {
            if (cones[i].getVolume() < smallestCones.getVolume()) {
                smallestCones = cones[i];
            }
        }

        // Print the cone with the smallest volume
        System.out.println("Cone with the smallest volume:");
        smallestCones.print();
    }
}
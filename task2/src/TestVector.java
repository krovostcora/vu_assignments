public class TestVector {
    public static void main(String[] args) {
        try {
            Vector vector1 = new Vector(5);
            double[] array = {1.0, 2.0, 3.0, 4.0, 5.0};
            vector1.copyElements(array);
            vector1.print();

            Vector vector2 = new Vector(5);
            vector2.setElement(2, 10.0);
            vector2.print();

            // Calculate scalar product
            double scalarProduct = vector1.scalarProduct(vector2);
            System.out.println("Scalar product: " + scalarProduct);

            // Calculate vector product (if applicable)
            Vector vectorProduct = vector1.vectorProduct(vector2);
            System.out.println("Vector product: " + vectorProduct);
        } catch (VectorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
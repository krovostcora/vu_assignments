public class Vector {
    private double[] elements;
    private int length;
    public static final int MAX_LENGTH = 15;

    public Vector(int length) throws VectorException {
        if (length <= 0 || length > MAX_LENGTH) {
            throw new VectorException("Invalid length: must be between 1 and " + MAX_LENGTH);
        }
        this.length = length;
        elements = new double[length];
    }

    public Vector(double[] doubles) {
    }

    public void copyElements(double[] array) throws VectorException {
        if (array.length != length) {
            throw new VectorException("Array length must match vector length");
        }
        System.arraycopy(array, 0, elements, 0, length);
    }

    public void setElement(int i, double value) throws VectorException {
        if (i < 0 || i >= length) {
            throw new VectorException("Index out of bounds");
        }
        elements[i] = value;
    }

    public double getElement(int i) throws VectorException {
        if (i < 0 || i >= length) {
            throw new VectorException("Index out of bounds");
        }
        return elements[i];
    }

    public int get_l() {
        return length;
    }

    public void set_l(int length) throws VectorException {
        if (length <= 0 || length > MAX_LENGTH) {
            throw new VectorException("Invalid length: must be between 1 and " + MAX_LENGTH);
        }
        this.length = length;
        elements = new double[length];
    }

    public void print() {
        System.out.print("(");
        for (int i = 0; i < length; i++) {
            System.out.print(elements[i]);
            if (i < length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")");
    }

    public double scalarProduct(Vector other) throws VectorException {
        if (length != other.length) {
            throw new VectorException("Vectors must have the same length for scalar product");
        }
        double result = 0;
        for (int i = 0; i < length; i++) {
            result += elements[i] * other.elements[i];
        }
        return result;
    }

    public Vector vectorProduct(Vector other) throws VectorException {
        if (length != other.length || length != 3) {
            throw new VectorException("Vectors must be 3-dimensional for vector product");
        }
        double x = elements[1] * other.elements[2] - elements[2] * other.elements[1];
        double y = elements[2] * other.elements[0] - elements[0] * other.elements[2];
        double z = elements[0] * other.elements[1] - elements[1] * other.elements[0];
        return new Vector(new double[]{x, y, z});
    }
}
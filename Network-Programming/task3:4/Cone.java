package thirdTask;

class Cone extends Circle {
    private double height;

    // Constructor with default values for radius and height
    public Cone() {
        this(1, 1);
    }

    // Constructor with specified radius and height
    public Cone(double radius, double height) {
        super(radius); // Calls the constructor of the parent class (thirdTask.Circle)
        this.height = height;
    }

    // Getters and setters for height
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Overriding the getArea() method from thirdTask.Circle to calculate the cone's total surface area
    @Override
    public double getArea() {
        return Math.PI * radius * (radius + Math.sqrt(radius * radius + height * height));
    }

    // New method to calculate the cone's volume
    public double getVolume() {
        return (1.0 / 3.0) * Math.PI * radius * radius * height;
    }

    // Overriding the printNameShape() method to specify the shape
    @Override
    public void printNameShape() {
        System.out.println("thirdTask.Cone");
    }

    // Overriding the print() method to display cone information
    @Override
    public void print() {
        System.out.println("thirdTask.Cone with a radius " + getRadius() + " and a height " + height + ", has an area P=" + getArea() + " and a volume V=" + getVolume());
    }
}
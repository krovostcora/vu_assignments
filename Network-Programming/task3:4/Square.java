package thirdTask;

class Square extends Shape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public void printNameShape() {
        System.out.println("thirdTask.Square");
    }

    @Override
    public void print() {
        System.out.println("thirdTask.Square with a side " + side + ", has a perimeter L=" + getPerimeter() + " and an area P=" + getArea());
    }
}
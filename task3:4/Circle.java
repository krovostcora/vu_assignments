package thirdTask;

class Circle extends Shape {
    public double radius;

    // Конструктор за замовчуванням (радіус за замовчуванням 1)
    public Circle() {
        this(1);
    }

    // Конструктор з параметром
    public Circle(double radius) {
        this.radius = radius;
    }

    // Метод для отримання радіуса
    public double getRadius() {
        return radius;
    }

    // Метод для встановлення радіуса
    public void setRadius(double radius) {
        this.radius = radius;
    }

    // Перевизначення абстрактних методів з класу Shape

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius; // Довжина кола
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius; // Площа кола
    }

    @Override
    public void printNameShape() {
        System.out.println("thirdTask.Circle");
    }

    @Override
    public void print() {
        System.out.println("thirdTask.Circle with a radius " + radius + ", has a perimeter L=" + getPerimeter() + " and an area P=" + getArea());
    }
}
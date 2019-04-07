public class Product {
    String name;
    Double price;

    public Product(String name) {
        this.name = name;
    }

    public Product nextProduct() {
        String[] splittedName = this.name.split("#");
        splittedName[1] = String.valueOf(Integer.parseInt(splittedName[1]) + 1);
        String name = splittedName[0] + "#" + splittedName[1];
        return new Product(name);
    }

    public Product withPrice(Double price) {
        this.price = price;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

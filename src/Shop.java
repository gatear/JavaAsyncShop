import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

final class Shop {

    private Shop() {
    }

    //simulate long lasting operation
    static private void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static private double calculatePrice(Product that) {
        delay();
        return new Random().nextDouble();
    }

    //synchronous method
    static Product getPrice(Product that) {
        return that.withPrice(calculatePrice(that));
    }

    //asynchronous method
    static CompletableFuture<Product> getPriceAsync(Product that) {
        return CompletableFuture.supplyAsync(() -> getPrice(that));
    }

    static Optional<Product> findBestPrice(List<Product> products) {
        return products
                .parallelStream()
                .map(Shop::getPrice)
                .min((Comparator.comparing(Product::getPrice)));
    }
}

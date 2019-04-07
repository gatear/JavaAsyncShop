import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {

        Supplier<Stream<Product>> stockGenerator = () -> Stream.iterate(new Product("p#1"), Product::nextProduct);
        List<Product> batch = stockGenerator.get().limit(100).collect(toList());

        double t1 = System.currentTimeMillis();

        Shop.findBestPrice(batch).map(p -> {
            System.out.println("Product with best price " + p.name);
            return Optional.empty();
        });

        double t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) / 1000 + " seconds");

        Stream<CompletableFuture<Product>> ps = stockGenerator.get().limit(100).map(Shop::getPriceAsync);

        try {
            CompletableFuture
                    .anyOf(ps.toArray(CompletableFuture[]::new))
                    .whenComplete(
                            (product,error) ->
                                    System.out.println("Fastest price to get is for product " + ((Product)product).name ))
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

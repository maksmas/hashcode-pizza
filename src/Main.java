import dto.Slice;
import entities.Input;
import dto.Pattern;
import entities.Pizza;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Input input = new Reader().read(FileNames.big);
        Pizza pizza = new Pizza(input);

        List<Pattern> allPatterns = Patterns.get(input.getMinIngredients(), input.getMaxCells()).stream()
                .filter(pattern -> pattern.getDx() <= input.getCols() && pattern.getDy() <= input.getRows())
                .sorted((o1, o2) -> o2.getSize() - o1.getSize())
                .collect(Collectors.toList());

        while (!allPatterns.isEmpty()) {
            System.out.println(
                    "Cuts: " + pizza.getCuts().size() +
                    " Patterns: " + allPatterns.size() +
                    " t count: " + pizza.gettCount() +
                    " m count: " + pizza.getmCount());
            Optional<Slice> bestSlice = pizza.findBestSlice(allPatterns.get(0));

            if (bestSlice.isPresent()) {
                pizza.cut(bestSlice.get());
            } else {
                allPatterns.remove(0);
            }

        }

        new Writer().write(pizza.getCuts(), "big.out");
    }
}

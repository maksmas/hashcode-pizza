import entities.Input;
import dto.Pattern;
import entities.Pizza;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Input input = new Reader().read(FileNames.example);
        Pizza pizza = new Pizza(input);

        // todo debug
        System.out.println(pizza);

        List<Pattern> patterns = Patterns.get(input.getMinIngredients(), input.getMaxCells());

        patterns = patterns.stream()
                .filter(pattern -> pattern.getDx() <= input.getCols() && pattern.getDy() <= input.getRows())
                .collect(Collectors.toList());

        // todo debug
//        System.out.println(patterns);

        Pattern currentPattern = findBestPattern(patterns);

        System.out.println(currentPattern);

        System.out.println(pizza.findBestSlice(currentPattern));
    }

    private static Pattern findBestPattern(List<Pattern> availablePatterns) {
        return availablePatterns.stream().max(Comparator.comparingInt(Pattern::getSize))
                .orElseThrow(() -> new RuntimeException("Shouldn't happen"));
    }
}

import dto.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Patterns {
    static List<Pattern> get(int minIngredients, int maxCells) {
        int minCells = minIngredients * 2;

        List<Pattern> patterns = new ArrayList<>();

        for (int currCells = minCells; currCells <= maxCells; currCells++) {
            final int num = currCells;
            patterns.addAll(
                    factors(currCells)
                            .stream()
                            .map(factor -> new Pattern(factor, num / factor)).collect(Collectors.toList()));
        }

        return patterns;
    }

    private static List<Integer> factors(int num) {
        return IntStream.range(1, num + 1).filter(i -> num % i == 0).boxed().collect(Collectors.toList());
    }
}

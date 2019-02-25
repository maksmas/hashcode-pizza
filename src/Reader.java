import entities.Input;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Reader {
    Input read(String fileName) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(getClass().getResource(fileName).toURI()));

        Input.Builder builder = parseFirstLine(lines.get(0));

        lines.stream().skip(1).forEach(builder::addLine);

        return builder.build();
    }

    private Input.Builder parseFirstLine(String line) {
        String[] parts = line.split(" ");
        int rows = Integer.parseInt(parts[0]);
        int cols = Integer.parseInt(parts[1]);
        int minIngredients = Integer.parseInt(parts[2]);
        int maxCells = Integer.parseInt(parts[3]);

        return new Input.Builder(rows, cols, minIngredients, maxCells);
    }
}

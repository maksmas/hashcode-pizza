import dto.Slice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {
    void write(List<Slice> cuts, String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(String.valueOf(cuts.size()));
        bw.newLine();

        cuts.forEach(cut -> {
            try {
                bw.write(String.format(
                        "%d %d %d %d",
                        cut.y0,
                        cut.x0,
                        cut.y1-1,
                        cut.x1-1
                ));

                bw.newLine();
            } catch (IOException e) {
                // java exceptions are totally fine ...
            }
        });

        bw.flush();
        bw.close();
    }
}

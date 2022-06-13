import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class History {
    Path path;
    List<String> linesOfPath = new ArrayList<>();

    public History(File file) {
        path = Path.of(file.getPath());
    }

    public History(Path path) {
        this.path = path;
    }
}
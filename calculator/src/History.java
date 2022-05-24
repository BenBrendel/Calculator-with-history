import java.nio.file.Path;
import java.util.List;

public class History {
    Path path;
    List<String> linesOfPath;

    public History() {
    }

    public History(Path path) {
        this.path = path;
    }

    /**
     *
     * @param path Path of the History
     */
    public void loadHistory(Path path) {

    }

    /**
     *
     * @param path path where to save the current history
     */
    public void saveHistory(Path path) {

    }
}

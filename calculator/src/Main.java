import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("calculator");
        stage.setMinWidth(450);
        stage.setMinHeight(100);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane, 250, 150);
        stage.setScene(scene);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label label1 = new Label("Wählen Sie, ob Sie einen Verlauf laden oder ein neues Projekt erstellen wollen:");
        Button button1 = new Button("neues Projekt");
        TextField textfield1 = new TextField();
        textfield1.setPromptText("Verlaufs-Pfad:");

        label1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        textfield1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.add(label1, 0, 0);
        gridPane.add(button1, 0, 1);
        gridPane.add(textfield1, 0, 3);

        button1.setOnMouseClicked(k -> {

        });

        textfield1.setOnKeyPressed(k -> {
        });

        stage.show();
    }
}
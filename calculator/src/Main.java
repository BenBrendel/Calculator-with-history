import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("calculator");
        stage.setMinWidth(480);
        stage.setMinHeight(100);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane, 250, 150);
        stage.setScene(scene);

        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));


        Label label1 = new Label("Wählen Sie, ob Sie einen Verlauf laden oder eine neue Session erstellen wollen:");
        Button button1 = new Button("neue Session");
        TextField textField1 = new TextField();
        textField1.setPromptText("Verlaufs-Pfad:");

        label1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        textField1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.add(label1, 0, 0);
        gridPane.add(button1, 0, 1);
        gridPane.add(textField1, 0, 3);

        button1.setOnMouseClicked(k -> {
            Calculator calc = new Calculator();
            stage.setScene(calc.newCalc());
        });

        textField1.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) {
                Calculator calc = new Calculator();
                stage.setScene(calc.newCalc());
            }
        });

        stage.show();
    }
}
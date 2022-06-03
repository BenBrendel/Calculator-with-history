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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * creates starting scene
         */
        stage.setTitle("calculator");
        stage.setMinWidth(600);
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
        Button button2 = new Button("Verlaufs-Pfad");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        label1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.add(label1, 0, 0);
        gridPane.add(button1, 0, 1);
        gridPane.add(button2, 0, 3);
        /**
         * if button pressed -> create a new calculator and load it
         */
        button1.setOnAction(k -> {
            Calculator calc = new Calculator();
            stage.setScene(calc.newCalc());

        });
        /**
         * if enter is pressed in the textfield a Calculator is created with the path of the history
         */



            button2.setOnAction(k -> {

                File selectedFile = fileChooser.showOpenDialog(stage);
                History history = new History();
                Calculator calc = new Calculator(history);


                try {
                    history.linesOfPath = Files.readAllLines(Path.of(selectedFile.getPath()));


                    history.linesOfPath = Files.readAllLines(history.path);


                    stage.setScene(calc.newCalc());

                }catch (Exception ignored){

                }

            });
        stage.show();



    }

}
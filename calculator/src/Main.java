import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {
    static String zwischenVerlauf;
    public static void main(String[] args) {
        launch(args);
    }

    final ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(
            "Standardrechner",
            "Binärrechner",
            "Einheiten-Umwandler"
    ));

    public void modeSelection(Stage stage, History history) {
        VBox vbox = new VBox();
        Scene modeSelection = new Scene(vbox, 300, 100);

        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(222, 238, 246), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15));
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Wählen Sie Ihren Modus aus:");

        vbox.getChildren().add(label);
        vbox.getChildren().add(comboBox);

        stage.setScene(modeSelection);
        /**
         * Event Listener für Rechner auswahl
         */
        comboBox.setOnAction(e -> {
            if (history != null) {
                if (comboBox.getValue().equals("Standardrechner")) {
                    Calculator calc = new Calculator(history);
                    stage.setScene(calc.newCalc(stage));
                } else if (comboBox.getValue().equals("Binärrechner")) {
                    BinaryCalc binaryCalc = new BinaryCalc(history);
                    stage.setScene(binaryCalc.newCalc(stage));
                } else if (comboBox.getValue().equals("Einheiten-Umwandler")) {
                    EinheitenUmwandler einheitenUmwandler = new EinheitenUmwandler(history);
                    stage.setScene(einheitenUmwandler.newCalc(stage));
                }
            } else {
                if (comboBox.getValue().equals("Standardrechner")) {
                    Calculator calc = new Calculator();
                    stage.setScene(calc.newCalc(stage));
                } else if (comboBox.getValue().equals("Binärrechner")) {
                    BinaryCalc binaryCalc = new BinaryCalc();
                    stage.setScene(binaryCalc.newCalc(stage));
                } else if (comboBox.getValue().equals("Einheiten-Umwandler")) {
                    EinheitenUmwandler einheitenUmwandler = new EinheitenUmwandler();
                    stage.setScene(einheitenUmwandler.newCalc(stage));
                }
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        /**
         * creates starting scene
         */
        stage.setTitle("Calculator");
        stage.setMinWidth(300);
        stage.setMinHeight(100);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane, 600, 150);
        stage.setScene(scene);
        ImageView headerLogo = new ImageView();
        try {
            headerLogo = new ImageView(new Image(new FileInputStream("pic\\calc.png")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.getIcons().add(headerLogo.getImage());

        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(222, 238, 246), CornerRadii.EMPTY, Insets.EMPTY)));
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
            modeSelection(stage, null);
        });
        /**
         * if enter is pressed in the textfield a Calculator is created with the path of the history
         */

        button2.setOnAction(k -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            try {
                History history = new History(Path.of(selectedFile.getPath()));
                try {
                    history.linesOfPath = Files.readAllLines(Path.of(selectedFile.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    history.linesOfPath = Files.readAllLines(history.path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modeSelection(stage, history);
            } catch (Exception ignored) {
            }
        });

        stage.show();
    }
}
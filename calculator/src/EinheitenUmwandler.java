import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EinheitenUmwandler {
    History history;
    TextArea verlauf = new TextArea();

    public EinheitenUmwandler() {
    }

    public EinheitenUmwandler(History history) {
        this.history = history;
    }

    public void save(List<String> linesOfHistory) throws IOException {
        try {
            try (
                    BufferedWriter out = Files.newBufferedWriter((history.path), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            ) {

                for (String s : linesOfHistory) {

                    out.write(s + System.lineSeparator());

                }
            }
        } catch (Exception ignored) {

        }

    }

    /**
     * creates the scene of the calculator
     *
     * @return scene for the calculator
     */
    public Scene newCalc() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane, 900, 350);

        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        Label verlaufText = new Label("Verlauf:");
        Button[] buttons = new Button[]{
                new Button("="), new Button("C"), new Button("CE"), new Button("Save")
        };

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "kg",
                        "lb",
                        "Meter",
                        "ft",
                        "inches",
                        "Sekunden",
                        "Minuten",
                        "Stunden",
                        "Tage",
                        "Wochen",
                        "Monate",
                        "Jahre"
                );
        final ComboBox eingabeEinheit = new ComboBox(options);
        final ComboBox ausgabeEinheit = new ComboBox(options);
        TextField eingabe = new TextField();
        TextField ausgabe = new TextField();
        Label eingabeLabel = new Label("Eingabe:");
        Label ausgabeLabel = new Label("Ausgabe:");
        Label einheit1 = new Label("Einheit:");
        Label einheit2 = new Label("Einheit:");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
            buttons[i].setStyle("-fx-font-size:25");
        }

        verlauf.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        verlauf.setStyle("-fx-font-size:20");
        verlauf.setEditable(false);
        verlaufText.setStyle("-fx-font-size:25");
        einheit1.setStyle("-fx-font-size:20");
        einheit2.setStyle("-fx-font-size:20");
        eingabeLabel.setStyle("-fx-font-size:20");
        ausgabeLabel.setStyle("-fx-font-size:20");
        GridPane.setHalignment(verlaufText, HPos.CENTER);

        gridPane.requestFocus();

        gridPane.add(verlauf, 4, 2, 1, 4);
        gridPane.add(buttons[0], 0, 5);
        gridPane.add(buttons[1], 1, 5);
        gridPane.add(buttons[2], 2, 5);
        gridPane.add(buttons[3], 3, 5);
        gridPane.add(eingabe, 0, 2);
        gridPane.add(ausgabe, 2, 2);
        gridPane.add(eingabeEinheit, 1, 2);
        gridPane.add(ausgabeEinheit, 3, 2);
        gridPane.add(verlaufText, 4, 1);
        gridPane.add(einheit1, 1, 1);
        gridPane.add(einheit2, 3, 1);
        gridPane.add(eingabeLabel, 0, 1);
        gridPane.add(ausgabeLabel, 2, 1);

        try {
            for (int i = 0; i < history.linesOfPath.size(); i++) {
                verlauf.appendText(history.linesOfPath.get(i) + "\n");
            }
        } catch (Exception ignored) {
        }

        /**
         * evenListener for buttons
         */

        buttons[0].setOnAction(k -> {
            //=
            //muss angepasst werden, weil es ja kein calculate mehr gibt
        });

        buttons[1].setOnAction(k -> {
            //C
            ausgabe.clear();
        });

        buttons[2].setOnAction(k -> {
            //CE
            verlauf.clear();
        });

        buttons[3].setOnAction(k -> {
            //Save
            List<String> allLineOfHistory = Arrays.asList(verlauf.getText().split("\n"));
            if (this.history == null) {
                GridPane saveScreen = new GridPane();
                saveScreen.setAlignment(Pos.CENTER);
                Scene saveScene = new Scene(saveScreen, 280, 100);
                Stage saveStage = new Stage();
                saveStage.setTitle("save");
                saveStage.setScene(saveScene);
                saveScreen.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), CornerRadii.EMPTY, Insets.EMPTY)));
                saveScreen.setHgap(5);
                saveScreen.setVgap(5);
                saveScreen.setPadding(new Insets(5));
                Label label = new Label("Speichere deinen Verlauf als eine .txt Datei ab:");
                Button save = new Button("Pfad-Auswahl");
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt")
                );
                fileChooser.setInitialFileName("newHistory.txt");
                saveScreen.add(label, 1, 0);
                saveScreen.add(save, 1, 1);

                save.setOnAction(e -> {
                    File verlaufsDatei = fileChooser.showSaveDialog(saveStage); //File mit Verlauf
                    try {
                        Files.createFile(Paths.get(verlaufsDatei.getPath()));
                        try (
                                BufferedWriter out = Files.newBufferedWriter(Path.of(verlaufsDatei.getPath()), StandardCharsets.UTF_8);
                        ) {
                            for (String s : allLineOfHistory) {
                                out.write(s + System.lineSeparator());
                            }

                        } catch (Exception ignored) {

                        }
                    } catch (Exception ignored) {

                    }

                    saveStage.close();
                });

                saveStage.show();
            } else {
                List<String> linesOfHistory = Arrays.asList(verlauf.getText().split("\n"));
                try {
                    this.save(linesOfHistory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * activating buttons with keys
         */
        scene.setOnKeyPressed(k -> {
            if (k.getCode() == KeyCode.ENTER) {
                buttons[10].fire();
            }
        });

        return scene;
    }
}
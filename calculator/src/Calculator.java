import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Calculator {
    History history;

    public Calculator() {
    }

    public Calculator(History history) {
        this.history = history;
    }

    public void save(List<String> linesOfHistory) throws IOException {
        try (
                BufferedWriter out = Files.newBufferedWriter((history.path), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        ) {

            for (String s : linesOfHistory) {
                out.write(s + System.lineSeparator());
            }
        }
    }

    public double calculate(String rechnung) {
        String[] splittedRechnungArray = rechnung.split(" ");
        List<String> splittedRechnung = new ArrayList<>(splittedRechnungArray.length);
        for (int i = 0; i < splittedRechnungArray.length; i++) {
            splittedRechnung.add(splittedRechnungArray[i]);
        }
        System.out.println(splittedRechnung.toString());
        double erg = 0;
        double zwischenErgebnis = 0;
        for (int i = 0; i < splittedRechnung.size(); i++) {
            if (splittedRechnung.get(i).contains("*")) {

                zwischenErgebnis = Double.parseDouble(splittedRechnung.get(i - 1)) * Double.parseDouble(splittedRechnung.get(i + 1));
                splittedRechnung.set(i, zwischenErgebnis + "");
                splittedRechnung.remove(i + 1);
                splittedRechnung.remove(i - 1);


            } else if (splittedRechnung.get(i).contains("/")) {

                zwischenErgebnis = Double.parseDouble(splittedRechnung.get(i - 1)) / Double.parseDouble(splittedRechnung.get(i + 1));
                splittedRechnung.set(i, zwischenErgebnis + "");
                splittedRechnung.remove(i + 1);
                splittedRechnung.remove(i - 1);


            }
        }

        if (splittedRechnung.size() == 1) {
            return Double.parseDouble(splittedRechnung.get(0));
        }

        if ((rechnung.contains("+") || rechnung.contains("-")) && (!(rechnung.contains("*") && rechnung.contains("/")))) {
            erg = Double.parseDouble(splittedRechnung.get(0));
            for (int i = 1; i < splittedRechnung.size(); i++) {
                if (splittedRechnung.get(i).contains("+")) {
                    try {
                        erg += Double.parseDouble(splittedRechnung.get(i + 1));
                    } catch (Exception ignored) {

                    }

                } else if (splittedRechnung.get(i).contains("-")) {
                    try {
                        erg -= Double.parseDouble(splittedRechnung.get(i + 1));
                    } catch (Exception ignored) {

                    }
                }
            }
        }

        return erg;
    }

    /**
     * creates the scene of the calculator
     *
     * @return scene for the calculator
     */
    public Scene newCalc() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane, 820, 500);

        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5));

        Label verlaufText = new Label("Verlauf:");
        Button[] buttons = new Button[]{new Button("0"), new Button("1"), new Button("2"), new Button("3"), new Button("4"), new Button("5"), new Button("6"), new Button("7"), new Button("8"), new Button("9"),
                new Button("="), new Button("+"), new Button("-"), new Button("/"), new Button("*"), new Button("C"), new Button("."), new Button("Back"), new Button("+/-"), new Button("CE"), new Button("Save")
        };
        TextArea ausgabe = new TextArea();
        TextArea verlauf = new TextArea();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Standardrechner",
                        "Binärrechner",
                        "Einheiten-Umwandler"
                );
        final ComboBox comboBox = new ComboBox(options);
        comboBox.getSelectionModel().select(0);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
            buttons[i].setStyle("-fx-font-size:25");
        }
        ausgabe.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        ausgabe.setStyle("-fx-font-size:20");
        ausgabe.setEditable(false);
        verlauf.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        verlauf.setStyle("-fx-font-size:20");
        verlauf.setEditable(false);
        verlaufText.setStyle("-fx-font-size:25");
        GridPane.setHalignment(verlaufText, HPos.CENTER);

        gridPane.add(ausgabe, 0, 1, 4, 1);
        gridPane.add(verlauf, 4, 2, 1, 4);
        gridPane.add(comboBox, 0, 0);
        gridPane.add(buttons[0], 1, 6);
        gridPane.add(buttons[1], 0, 5);
        gridPane.add(buttons[2], 1, 5);
        gridPane.add(buttons[3], 2, 5);
        gridPane.add(buttons[4], 0, 4);
        gridPane.add(buttons[5], 1, 4);
        gridPane.add(buttons[6], 2, 4);
        gridPane.add(buttons[7], 0, 3);
        gridPane.add(buttons[8], 1, 3);
        gridPane.add(buttons[9], 2, 3);
        gridPane.add(buttons[10], 3, 6);
        gridPane.add(buttons[11], 3, 5);
        gridPane.add(buttons[12], 3, 4);
        gridPane.add(buttons[13], 3, 2);
        gridPane.add(buttons[14], 3, 3);
        gridPane.add(buttons[15], 1, 2);
        gridPane.add(buttons[16], 2, 6);
        gridPane.add(buttons[17], 2, 2);
        gridPane.add(buttons[18], 0, 6);
        gridPane.add(buttons[19], 0, 2);
        gridPane.add(buttons[20], 4, 6);
        gridPane.add(verlaufText, 4, 1);

        /**
         * evenListener for buttons
         */
        buttons[0].setOnMouseClicked(k -> {
            //0
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("0");
        });

        buttons[1].setOnMouseClicked(k -> {
            //1
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("1");
        });

        buttons[2].setOnMouseClicked(k -> {
            //2
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("2");
        });

        buttons[3].setOnMouseClicked(k -> {
            //3
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("3");
        });

        buttons[4].setOnMouseClicked(k -> {
            //4
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("4");
        });

        buttons[5].setOnMouseClicked(k -> {
            //5
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("5");
        });

        buttons[6].setOnMouseClicked(k -> {
            //6
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("6");
        });

        buttons[7].setOnMouseClicked(k -> {
            //7
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("7");
        });

        buttons[8].setOnMouseClicked(k -> {
            //8
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("8");
        });

        buttons[9].setOnMouseClicked(k -> {
            //9
            if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
                ausgabe.clear();
            }
            ausgabe.appendText("9");
        });

        buttons[10].setOnMouseClicked(k -> {
            //=
            String Rechnung = ausgabe.getText();
            if (!(Rechnung.contains("=")) && Rechnung.length() != 0 && Rechnung.charAt(Rechnung.length() - 1) != ' ') {
                if (this.calculate(Rechnung) == Double.POSITIVE_INFINITY || this.calculate(Rechnung) == Double.NEGATIVE_INFINITY) {
                    verlauf.clear();
                    ausgabe.setText("Error! Division durch 0");
                } else {
                    verlauf.appendText(ausgabe.getText() + " = " + this.calculate(Rechnung) + "\n"); //Ergebnis einfügen
                    ausgabe.clear();
                    ausgabe.appendText("= " + this.calculate(Rechnung));
                }
            }


        });

        buttons[11].setOnMouseClicked(k -> {
            //+
            if (ausgabe.getText().length() != 0 && ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                ausgabe.appendText(" + ");
            }
        });

        buttons[12].setOnMouseClicked(k -> {
            //-
            if (ausgabe.getText().length() != 0 && ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                ausgabe.appendText(" - ");
            }
        });

        buttons[13].setOnMouseClicked(k -> {
            ///
            if (ausgabe.getText().length() != 0 && ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                ausgabe.appendText(" / ");
            }
        });

        buttons[14].setOnMouseClicked(k -> {
            //*
            if (ausgabe.getText().length() != 0 && ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                ausgabe.appendText(" * ");
            }
        });

        buttons[15].setOnMouseClicked(k -> {
            //C
            ausgabe.clear();
        });

        buttons[16].setOnMouseClicked(k -> {
            //.

            if (ausgabe.getText().charAt(ausgabe.getText().length() - 1) >= '0' && ausgabe.getText().charAt(ausgabe.getText().length() - 1) <= '9' && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                ausgabe.appendText(".");
            }
        });


        buttons[17].setOnMouseClicked(k -> {
            //Back
            if (ausgabe.getText().length() != 0 && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                if (ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().contains("="))) {

                    ausgabe.setText(ausgabe.getText().substring(0, ausgabe.getText().length() - 1));
                } else if (!(ausgabe.getText().contains("="))) {
                    ausgabe.setText(ausgabe.getText().substring(0, ausgabe.getText().length() - 3));
                }
            }
        });

        buttons[18].setOnMouseClicked(k -> {
            //+/-
            if (!(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                String rechnung = ausgabe.getText();
                String[] numbers = rechnung.split(" ");
                rechnung = rechnung.substring(0, rechnung.length() - numbers[numbers.length - 1].length());

                numbers[numbers.length - 1] = "-" + numbers[numbers.length - 1];
                ausgabe.setText(rechnung + numbers[numbers.length - 1]);

            }
        });

        buttons[19].setOnMouseClicked(k -> {
            //CE
            ausgabe.clear();
            verlauf.clear();
        });

        buttons[20].setOnMouseClicked(k -> {
            //Save
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

                save.setOnMouseClicked(e -> {
                    File verlaufsDatei = fileChooser.showSaveDialog(saveStage); //File mit Verlauf
                    saveStage.close();
                });

                saveStage.show();
            } else {
                List<String> linesOfHistory = Arrays.asList(verlauf.getText().split("\n"));
                try {
                    this.save(linesOfHistory);
                    ausgabe.clear();
                    ausgabe.setText("Speichern erfolgreich!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        gridPane.setOnKeyPressed(k -> {
        });

        return scene;
    }
}
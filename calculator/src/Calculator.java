import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Calculator extends Main {
    History history;
    TextArea ausgabe = new TextArea();
    TextArea verlauf = new TextArea();

    public Calculator() {
    }

    public Calculator(History history) {
        this.history = history;
    }

    /**
     * Die Methode wird aufgerufen wenn ein Button mit einer "Zahl" gedrückt wird
     *
     * @param Number = Die Zahl die gedrückt wird
     * @param button = der Button der gedrückt wird
     */

    public void pressNumber(char Number, Button button) {
        if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
            ausgabe.clear();
        }
        ausgabe.appendText("" + Number);
        button.requestFocus();
    }

    /**
     * Die Methode wird aufgerufen wenn ein Button mit einem "Rechenzeichen" gedrückt wird
     *
     * @param Sign   = das Rechenzeichen welches ausgeführt wird
     * @param button = Der Button der gedrückt wurde
     */
    public void pressOperationSign(char Sign, Button button) {

        if (ausgabe.getText().length() != 0 && ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern")) && !ausgabe.getText().contains("=")) {
            ausgabe.appendText(String.format(" %c ", Sign));
        }
        button.requestFocus();
    }

    public void save(List<String> linesOfHistory) throws IOException {
        try {
            try (
                    BufferedWriter out = Files.newBufferedWriter((history.path), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            ) {
                List<String> allLinesOfHistory = Files.readAllLines(history.path);
                for (int i = allLinesOfHistory.size(); i < linesOfHistory.size(); i++) {

                    out.write(linesOfHistory.get(i) + System.lineSeparator());

                }
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * @param rechnung rechnung als String
     * @return das fertige Ergebnis
     */
    public double calculate(String rechnung) {
        String[] splittedRechnungArray = rechnung.split(" ");
        List<String> splittedRechnung = new ArrayList<>(splittedRechnungArray.length);
        for (int i = 0; i < splittedRechnungArray.length; i++) {
            splittedRechnung.add(splittedRechnungArray[i]);
        }

        double erg = 0;
        double zwischenErgebnis = 0;
        for (int i = 0; splittedRechnung.contains("*") || splittedRechnung.contains("/"); i++) {
            if (i == splittedRechnung.size() - 1) {
                i = 0;
            }
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

        if ((rechnung.contains("+") || rechnung.contains("-"))) {
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
    public Scene newCalc(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane, 820, 500);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5));

        Label verlaufText = new Label("Verlauf:");
        Button[] buttons = new Button[]{new Button("0"), new Button("1"), new Button("2"), new Button("3"), new Button("4"), new Button("5"), new Button("6"), new Button("7"), new Button("8"), new Button("9"),
                new Button("="), new Button("+"), new Button("-"), new Button("/"), new Button("*"), new Button("C"), new Button("."), new Button("Back"), new Button("+/-"), new Button("CE"), new Button("Save")
        };
        comboBox.getSelectionModel().select(0);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].getStyleClass().add("button");
            buttons[i].setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
            buttons[i].setStyle("-fx-font-size:25");
        }
        ausgabe.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        ausgabe.setStyle("-fx-font-size:20");
        verlauf.setText(zwischenVerlauf);
        ausgabe.setEditable(false);
        verlauf.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        verlauf.setStyle("-fx-font-size:20");
        verlauf.setEditable(false);
        verlaufText.setStyle("-fx-font-size:25");
        GridPane.setHalignment(verlaufText, HPos.CENTER);

        gridPane.requestFocus();

        gridPane.add(comboBox, 0, 0);
        gridPane.add(ausgabe, 0, 1, 4, 1);
        gridPane.add(verlauf, 4, 2, 1, 4);
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

        gridPane.getStyleClass().add("gridPane");

        try {
            for (int i = 0; i < history.linesOfPath.size(); i++) {
                verlauf.appendText(history.linesOfPath.get(i) + "\n");
            }
        } catch (Exception ignored) {
        }

        /**
         * evenListener for buttons
         */

        /**
         * eventlistener für button "0"
         */
        buttons[0].setOnAction(k -> {
            pressNumber('0', buttons[10]);
        });

        /**
         * eventlistener für button "1"
         */
        buttons[1].setOnAction(k -> {

            pressNumber('1', buttons[10]);
        });

        /**
         * eventlistener für button "2"
         */
        buttons[2].setOnAction(k -> {
            pressNumber('2', buttons[10]);
        });

        /**
         * eventlistener für button "3"
         */
        buttons[3].setOnAction(k -> {

            pressNumber('3', buttons[10]);
        });

        /**
         * eventlistener für button "4"
         */
        buttons[4].setOnAction(k -> {
            pressNumber('4', buttons[10]);
        });

        /**
         * eventlistener für button "5"
         */
        buttons[5].setOnAction(k -> {

            pressNumber('5', buttons[10]);
        });

        /**
         * eventlistener für button "6"
         */
        buttons[6].setOnAction(k -> {
            pressNumber('6', buttons[10]);
        });

        /**
         * eventlistener für button "7"
         */
        buttons[7].setOnAction(k -> {
            pressNumber('7', buttons[10]);
        });

        /**
         * eventlistener für button "8"
         */
        buttons[8].setOnAction(k -> {

            pressNumber('8', buttons[10]);
        });

        /**
         * eventlistener für button "9"
         */
        buttons[9].setOnAction(k -> {
            pressNumber('9', buttons[10]);
        });

        /**
         * eventlistener für button "="
         */
        buttons[10].setOnAction(k -> {
            String Rechnung = ausgabe.getText();
            if (!(Rechnung.contains("=")) && Rechnung.length() != 0 && Rechnung.charAt(Rechnung.length() - 1) != ' ' && !Rechnung.contains("Speichern erfolgreich!") && !Rechnung.contains("Es gab einen Fehler beim Speichern!")) {
                if (this.calculate(Rechnung) == Double.POSITIVE_INFINITY || this.calculate(Rechnung) == Double.NEGATIVE_INFINITY || Double.isNaN(this.calculate(Rechnung))) {
                    verlauf.clear();
                    ausgabe.setText("Error! Division durch 0");
                } else {
                    String[] splittedErg = (" " + this.calculate(Rechnung)).split("\\.");
                    if (Double.parseDouble(splittedErg[1]) == 0) {
                        verlauf.appendText(ausgabe.getText() + " = " + splittedErg[0] + "\n");
                        ausgabe.clear();
                        ausgabe.appendText("= " + splittedErg[0]);
                    } else {
                        verlauf.appendText(ausgabe.getText() + " = " + this.calculate(Rechnung) + "\n"); //Ergebnis einfügen
                        ausgabe.clear();
                        ausgabe.appendText("= " + this.calculate(Rechnung));
                    }
                }
                Main.zwischenVerlauf = verlauf.getText();
            }
        });
        /**
         * eventlistener für button "+"
         */
        buttons[11].setOnAction(k -> {
            pressOperationSign('+', buttons[10]);
        });

        /**
         * eventlistener für button "-"
         */
        buttons[12].setOnAction(k -> {
            pressOperationSign('-', buttons[10]);
        });

        /**
         * eventlistener für button "/"
         */
        buttons[13].setOnAction(k -> {
            pressOperationSign('/', buttons[10]);
        });

        /**
         * eventlistener für button "*"
         */
        buttons[14].setOnAction(k -> {
            pressOperationSign('*', buttons[10]);
        });

        /**
         * eventlistener für button "C"
         */
        buttons[15].setOnAction(k -> {
            ausgabe.clear();
            buttons[10].requestFocus();
        });

        /**
         * eventlistener für button "."
         */
        buttons[16].setOnAction(k -> {
            if (ausgabe.getText().length() != 0) {
                String[] splitRechnung = ausgabe.getText().split(" ");
                if (!ausgabe.getText().contains("=")) {
                    if (ausgabe.getText().charAt(ausgabe.getText().length() - 1) >= '0' && ausgabe.getText().charAt(ausgabe.getText().length() - 1) <= '9' && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                        if (!(splitRechnung[splitRechnung.length - 1].contains("."))) {
                            ausgabe.appendText(".");
                        }
                    }
                }
            }
            buttons[10].requestFocus();
        });


        /**
         * eventlistener für button "Back"
         */
        buttons[17].setOnAction(k -> {
            //Back
            if (ausgabe.getText().length() != 0 && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                if (ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().contains("="))) {

                    ausgabe.setText(ausgabe.getText().substring(0, ausgabe.getText().length() - 1));
                } else if (!(ausgabe.getText().contains("="))) {
                    ausgabe.setText(ausgabe.getText().substring(0, ausgabe.getText().length() - 3));
                }
            }
            buttons[10].requestFocus();
        });

        /**
         * eventlistener für button "+/-"
         */
        buttons[18].setOnAction(k -> {
            try {
                if (!(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().contains("=")) && ausgabe.getText().charAt(ausgabe.getText().length() - 1) >= '0' && ausgabe.getText().charAt(ausgabe.getText().length() - 1) <= '9') {
                    String rechnung = ausgabe.getText();
                    String[] numbers = rechnung.split(" ");

                    rechnung = rechnung.substring(0, rechnung.length() - numbers[numbers.length - 1].length());
                    if (numbers[numbers.length - 1].charAt(0) != '-') {
                        numbers[numbers.length - 1] = "-" + numbers[numbers.length - 1];
                    } else {
                        numbers[numbers.length - 1] = numbers[numbers.length - 1].substring(1);
                    }
                    ausgabe.setText(rechnung + numbers[numbers.length - 1]);
                }
            } catch (Exception ignored) {

            }
            buttons[10].requestFocus();
        });

        /**
         * eventlistener für button "CE"
         */
        buttons[19].setOnAction(k -> {
            ausgabe.clear();
            verlauf.clear();
            buttons[10].requestFocus();
        });

        /**
         * eventlistener für button "Save"
         */
        buttons[20].setOnAction(k -> {
            try {


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
                        if (verlaufsDatei != null) {
                            ausgabe.setText("Speichern erfolgreich!");
                            this.history = new History(verlaufsDatei);
                        } else {
                            ausgabe.setText("Es gab einen Fehler beim Speichern!");
                        }

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
            } catch (Exception ignored) {

            }
        });

        /**
         * activating buttons with keys
         */
        scene.setOnKeyTyped(k -> {
            if (k.getCharacter().equals("0")) {
                buttons[0].fire();
            } else if (k.getCharacter().equals("1")) {
                buttons[1].fire();
            } else if (k.getCharacter().equals("2")) {
                buttons[2].fire();
            } else if (k.getCharacter().equals("3")) {
                buttons[3].fire();
            } else if (k.getCharacter().equals("4")) {
                buttons[4].fire();
            } else if (k.getCharacter().equals("5")) {
                buttons[5].fire();
            } else if (k.getCharacter().equals("6")) {
                buttons[6].fire();
            } else if (k.getCharacter().equals("7")) {
                buttons[7].fire();
            } else if (k.getCharacter().equals("8")) {
                buttons[8].fire();
            } else if (k.getCharacter().equals("9")) {
                buttons[9].fire();
            } else if (k.getCharacter().equals("+")) {
                buttons[11].fire();
            } else if (k.getCharacter().equals("-")) {
                buttons[12].fire();
            } else if (k.getCharacter().equals("*")) {
                buttons[14].fire();
            } else if (k.getCharacter().equals("/")) {
                buttons[13].fire();
            } else if (k.getCharacter().equals(".") || k.getCharacter().equals(",")) {
                buttons[16].fire();
            }
        });

        scene.setOnKeyPressed(k -> {
            if (k.getCode() == KeyCode.BACK_SPACE) {
                buttons[17].fire();
            } else if (k.getCode() == KeyCode.ENTER) {
                buttons[10].fire();
            }
        });

        ausgabe.setOnMouseClicked(k -> {
                    buttons[10].requestFocus();
                }
        );

        verlauf.setOnMouseClicked(k -> {
                    buttons[10].requestFocus();
                }
        );

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

        return scene;
    }
}
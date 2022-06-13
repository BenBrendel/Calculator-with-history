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
import java.util.Objects;

public class BinaryCalc extends Main {
    History history;
    TextArea ausgabe = new TextArea();
    TextArea verlauf = new TextArea();

    public BinaryCalc() {
    }

    public BinaryCalc(History history) {
        this.history = history;
    }

    public void pressNumber(char Number, Button button) {
        if (ausgabe.getText().startsWith("=") || ausgabe.getText().startsWith("Error") || ausgabe.getText().startsWith("Speichern")) {
            ausgabe.clear();
        }
        ausgabe.appendText("" + Number);
        button.requestFocus();
    }

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
    public String calculate(String rechnung) {

        String[] splittedRechnungArray = rechnung.split(" ");
        List<String> splittedRechnung = new ArrayList<>(splittedRechnungArray.length);
        for (int i = 0; i < splittedRechnungArray.length; i++) {
            splittedRechnung.add(splittedRechnungArray[i]);
        }
        int erg = 0;
        int zwischenErgebnis = 0;
        for (int i = 0; splittedRechnung.contains("*") || splittedRechnung.contains("/"); i++) {
            if (i == splittedRechnung.size() - 1) {
                i = 0;
            }
            if (splittedRechnung.get(i).contains("*")) {
                int firstNumber = Integer.parseInt(splittedRechnung.get(i - 1), 2);
                int secondNumber = Integer.parseInt(splittedRechnung.get(i + 1), 2);
                zwischenErgebnis = firstNumber * secondNumber;
                splittedRechnung.set(i, Integer.toBinaryString(zwischenErgebnis));
                splittedRechnung.remove(i + 1);
                splittedRechnung.remove(i - 1);


            } else if (splittedRechnung.get(i).contains("/")) {
                int firstNumber = Integer.parseInt(splittedRechnung.get(i - 1), 2);
                int secondNumber = Integer.parseInt(splittedRechnung.get(i + 1), 2);
                try {
                    zwischenErgebnis = firstNumber / secondNumber;
                    splittedRechnung.set(i, Integer.toBinaryString(zwischenErgebnis));
                    System.out.println(splittedRechnung.get(i));
                    splittedRechnung.remove(i + 1);
                    splittedRechnung.remove(i - 1);
                } catch (ArithmeticException e) {
                    throw new ArithmeticException();
                }
            }

        }

        if (splittedRechnung.size() == 1) {
            return "" + Integer.toBinaryString(Integer.parseInt(splittedRechnung.get(0), 2));
        }

        if ((rechnung.contains("+") || rechnung.contains("-"))) {
            erg = (int) Integer.parseInt(splittedRechnung.get(0), 2);
            for (int i = 1; i < splittedRechnung.size(); i++) {
                if (splittedRechnung.get(i).contains("+")) {
                    try {
                        erg += Integer.parseInt(splittedRechnung.get(i + 1), 2);
                    } catch (Exception ignored) {

                    }

                } else if (splittedRechnung.get(i).contains("-")) {
                    try {
                        erg -= Integer.parseInt(splittedRechnung.get(i + 1), 2);
                    } catch (Exception ignored) {

                    }
                }
            }
        }

        return Integer.toBinaryString(erg);
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
        Button[] buttons = new Button[]{new Button("0"), new Button("1"), new Button("="), new Button("+"), new Button("-"), new Button("/"), new Button("*"), new Button("C"), new Button("Back"), new Button("+/-"), new Button("CE"), new Button("Save")
        };
        comboBox.getSelectionModel().select(1);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].getStyleClass().add("button");
            buttons[i].setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
            buttons[i].setStyle("-fx-font-size:25");
        }
        ausgabe.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        ausgabe.setStyle("-fx-font-size:20");
        ausgabe.setEditable(false);
        verlauf.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        verlauf.setStyle("-fx-font-size:20");
        verlauf.setEditable(false);
        verlauf.setText(Main.zwischenVerlauf);
        verlaufText.setStyle("-fx-font-size:25");
        GridPane.setHalignment(verlaufText, HPos.CENTER);

        gridPane.requestFocus();

        gridPane.add(comboBox, 0, 0);
        gridPane.add(ausgabe, 0, 1, 4, 1);
        gridPane.add(verlauf, 4, 2, 1, 4);
        gridPane.add(buttons[0], 0, 3, 3, 1);
        gridPane.add(buttons[1], 0, 4, 3, 1);
        gridPane.add(buttons[2], 1, 5);
        gridPane.add(buttons[3], 3, 5);
        gridPane.add(buttons[4], 3, 4);
        gridPane.add(buttons[5], 3, 2);
        gridPane.add(buttons[6], 3, 3);
        gridPane.add(buttons[7], 1, 2);
        gridPane.add(buttons[8], 2, 2);
        gridPane.add(buttons[9], 0, 5);
        gridPane.add(buttons[10], 0, 2);
        gridPane.add(buttons[11], 2, 5);
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

            pressNumber('0', buttons[2]);
        });

        /**
         * eventlistener für button "1"
         */
        buttons[1].setOnAction(k -> {
            pressNumber('1', buttons[2]);
        });

        /**
         * eventlistener für button "="
         */
        buttons[2].setOnAction(k -> {
            String Rechnung = ausgabe.getText();
            if (!(Rechnung.contains("=")) && Rechnung.length() != 0 && Rechnung.charAt(Rechnung.length() - 1) != ' ' && !Rechnung.contains("Speichern erfolgreich!") && !Rechnung.contains("Es gab einen Fehler beim Speichern!")) {
                try {
                    verlauf.appendText(ausgabe.getText() + " = " + this.calculate(Rechnung) + "\n");
                    ausgabe.clear();
                    ausgabe.appendText("= " + this.calculate(Rechnung));
                    Main.zwischenVerlauf = verlauf.getText();
                } catch (ArithmeticException e) {
                    ausgabe.clear();
                    ausgabe.setText("Error! Division durch 0");
                }

            }
        });

        /**
         * eventlistener für button "+"
         */
        buttons[3].setOnAction(k -> {

            pressOperationSign('+', buttons[2]);
        });

        /**
         * eventlistener für button "-"
         */
        buttons[4].setOnAction(k -> {

            pressOperationSign('-', buttons[2]);
        });

        /**
         * eventlistener für button "/"
         */
        buttons[5].setOnAction(k -> {
            pressOperationSign('/', buttons[2]);
        });

        /**
         * eventlistener für button "*"
         */
        buttons[6].setOnAction(k -> {
            pressOperationSign('*', buttons[2]);
        });

        /**
         * eventlistener für button "C"
         */
        buttons[7].setOnAction(k -> {
            ausgabe.clear();
            buttons[2].requestFocus();
        });


        /**
         * eventlistener für button "Back"
         */
        buttons[8].setOnAction(k -> {
            if (ausgabe.getText().length() != 0 && !(ausgabe.getText().startsWith("Error")) && !(ausgabe.getText().startsWith("Speichern"))) {
                if (ausgabe.getText().charAt(ausgabe.getText().length() - 1) != ' ' && !(ausgabe.getText().contains("="))) {

                    ausgabe.setText(ausgabe.getText().substring(0, ausgabe.getText().length() - 1));
                } else if (!(ausgabe.getText().contains("="))) {
                    ausgabe.setText(ausgabe.getText().substring(0, ausgabe.getText().length() - 3));
                }
            }
            buttons[2].requestFocus();
        });

        /**
         * eventlistener für button "+/-"
         */
        buttons[9].setOnAction(k -> {
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
            buttons[2].requestFocus();
        });

        /**
         * eventlistener für button "CE"
         */
        buttons[10].setOnAction(k -> {
            ausgabe.clear();
            verlauf.clear();
            buttons[2].requestFocus();
        });
        /**
        * eventlistener für button "Save"
        */
        buttons[11].setOnAction(k -> {

            /**
             *  wenn der Calculator noch nicht gespeichert wurde dann wird ein neues Fenster aufgerufen
             *  in dem man den Speicherort auswählen kann. Wurde schonmal gespeichert werden die neuen Verlaufszeilen in die
             *  vorhandene Datei angehängt.
             */
            if (this.history == null) {
                List<String> allLineOfHistory = Arrays.asList(verlauf.getText().split("\n"));
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
                    if(verlaufsDatei != null) {
                        ausgabe.setText("Speichern erfolgreich!");
                        this.history = new History(verlaufsDatei);
                    }else {
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
        });

        /**
         * activating buttons with keys
         */
        scene.setOnKeyTyped(k -> {
            if (k.getCharacter().equals("0")) {
                buttons[0].fire();
            } else if (k.getCharacter().equals("1")) {
                buttons[1].fire();
            } else if (k.getCharacter().equals("+")) {
                buttons[3].fire();
            } else if (k.getCharacter().equals("-")) {
                buttons[4].fire();
            } else if (k.getCharacter().equals("*")) {
                buttons[5].fire();
            } else if (k.getCharacter().equals("/")) {
                buttons[6].fire();
            } else if (k.getCharacter().equals(".") || k.getCharacter().equals(",")) {
                buttons[7].fire();
            }
        });

        scene.setOnKeyPressed(k -> {
            if (k.getCode() == KeyCode.BACK_SPACE) {
                buttons[8].fire();
            } else if (k.getCode() == KeyCode.ENTER) {
                buttons[2].fire();
            }
        });

        ausgabe.setOnMouseClicked(k -> {
                    buttons[2].requestFocus();
                }
        );

        verlauf.setOnMouseClicked(k -> {
                    buttons[2].requestFocus();
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
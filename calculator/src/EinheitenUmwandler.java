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

    public String removeDecimalPoint(String doubleNumber) {
        String[] splitDoubleNumber = doubleNumber.split("\\.");
        if (Double.parseDouble(splitDoubleNumber[1]) > 0) {
            return doubleNumber;
        } else {
            return splitDoubleNumber[0];
        }
    }

    public String calculate(String ausgangsEinheit, double Wert, String endEinheit) {
        String erg = "";
        if (ausgangsEinheit.equals("kg")) {
            erg += removeDecimalPoint(String.valueOf(Wert * 2.20462));
        } else if (ausgangsEinheit.equals("lb")) {
            erg += removeDecimalPoint(String.valueOf(Wert / 2.20462));
        } else if (ausgangsEinheit.equals("Meter")) {
            if (endEinheit.equals("ft")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 3.28084));
            } else if (endEinheit.equals("inches")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 39.3701));
            }

        } else if (ausgangsEinheit.equals("ft")) {
            if (endEinheit.equals("Meter")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 3.28084));
            } else if (endEinheit.equals("inches")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 12));
            }

        } else if (ausgangsEinheit.equals("inches")) {
            if (endEinheit.equals("Meter")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 39.3701));
            } else if (endEinheit.equals("ft")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 12));
            }

        } else if (ausgangsEinheit.equals("Sekunden")) {
            if (endEinheit.equals("Minuten")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 60));

            } else if (endEinheit.equals("Stunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 3600));

            } else if (endEinheit.equals("Tage")) {
                erg += removeDecimalPoint(String.valueOf(Wert * (1.1574 * Math.pow(10, -5))));

            } else if (endEinheit.equals("Wochen")) {
                erg += removeDecimalPoint(String.valueOf(Wert * (1.6534 * Math.pow(10, -6))));

            } else if (endEinheit.equals("Monate")) {
                erg += removeDecimalPoint(String.valueOf(Wert * (3.80517 * Math.pow(10, -7))));
            } else if (endEinheit.equals("Jahre")) {
                erg += removeDecimalPoint(String.valueOf(Wert * (3.17098 * Math.pow(10, -8))));
            }

        } else if (ausgangsEinheit.equals("Minuten")) {
            if (endEinheit.equals("Sekunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 60));
            } else if (endEinheit.equals("Stunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 60));
            } else if (endEinheit.equals("Tage")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 1440));
            } else if (endEinheit.equals("Wochen")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 10080));
            } else if (endEinheit.equals("Monate")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 43800));
            } else if (endEinheit.equals("Jahre")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 525600));
            }

        } else if (ausgangsEinheit.equals("Stunden")) {
            if (endEinheit.equals("Sekunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 3600));

            } else if (endEinheit.equals("Minuten")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 60));

            } else if (endEinheit.equals("Tage")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 24));
            } else if (endEinheit.equals("Wochen")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 168));
            } else if (endEinheit.equals("Monate")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 730));

            } else if (endEinheit.equals("Jahre")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 8760));

            }

        } else if (ausgangsEinheit.equals("Tage")) {
            if (endEinheit.equals("Sekunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 86400));
            } else if (endEinheit.equals("Minuten")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 1440));

            } else if (endEinheit.equals("Stunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 24));

            } else if (endEinheit.equals("Wochen")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 7));

            } else if (endEinheit.equals("Monate")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 30.417));

            } else if (endEinheit.equals("Jahre")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 365));

            }

        } else if (ausgangsEinheit.equals("Wochen")) {
            if (endEinheit.equals("Sekunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 604800));

            } else if (endEinheit.equals("Minuten")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 10080));

            } else if (endEinheit.equals("Stunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 168));

            } else if (endEinheit.equals("Tage")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 7));

            } else if (endEinheit.equals("Monate")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 4.345));

            } else if (endEinheit.equals("Jahre")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 52));
            }

        } else if (ausgangsEinheit.equals("Monate")) {

            if (endEinheit.equals("Sekunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * (2.628 * Math.pow(10, 6))));

            } else if (endEinheit.equals("Minuten")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 43800));

            } else if (endEinheit.equals("Stunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 730));

            } else if (endEinheit.equals("Tage")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 30.417));

            } else if (endEinheit.equals("Wochen")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 4.345));

            } else if (endEinheit.equals("Jahre")) {
                erg += removeDecimalPoint(String.valueOf(Wert / 12));

            }
        } else if (ausgangsEinheit.equals("Jahre")) {
            if (endEinheit.equals("Sekunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * (3.154 * Math.pow(10, 7))));

            } else if (endEinheit.equals("Minuten")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 525600));

            } else if (endEinheit.equals("Stunden")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 87600));

            } else if (endEinheit.equals("Tage")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 365));

            } else if (endEinheit.equals("Wochen")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 52));

            } else if (endEinheit.equals("Monate")) {
                erg += removeDecimalPoint(String.valueOf(Wert * 12));
            }

        }

        return erg;
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
        
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        Label verlaufText = new Label("Verlauf:");
        Button[] buttons = new Button[]{
                new Button("="), new Button("C"), new Button("CE"), new Button("Save")
        };

        ObservableList<String> EingabeOptions =
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
        ObservableList<String> AusgabeOptions =
                FXCollections.observableArrayList(
                        " "
                );
        final ComboBox eingabeEinheit = new ComboBox(EingabeOptions);
        final ComboBox ausgabeEinheit = new ComboBox(AusgabeOptions);
        TextField eingabe = new TextField();
        TextField ausgabe = new TextField();
        ausgabe.setEditable(false);
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

        eingabeEinheit.setOnAction(k -> {
            ausgabe.clear();
            ;
            String auswahl = (String) eingabeEinheit.getValue();
            if (auswahl.equals("kg")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "lb"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("lb")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "kg"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Stunden")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Sekunden", "Minuten", "Tage", "Wochen", "Monate", "Jahre"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Meter")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "ft", "inches"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Sekunden")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Minuten", "Stunden", "Tage", "Wochen", "Monate", "Jahre"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Minuten")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Sekunden", "Stunden", "Tage", "Wochen", "Monate", "Jahre"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Tage")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Sekunden", "Minuten", "Stunden", "Wochen", "Monate", "Jahre"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Wochen")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Sekunden", "Minuten", "Stunden", "Tage", "Monate", "Jahre"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Monate")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Sekunden", "Minuten", "Stunden", "Tage", "Wochen", "Jahre"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("Jahre")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Sekunden", "Minuten", "Stunden", "Tage", "Wochen", "Monate"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("inches")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Meter", "ft"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            } else if (auswahl.equals("ft")) {
                ObservableList<String> ausgabeOptions =
                        FXCollections.observableArrayList(
                                "Meter", "inches"
                        );
                ausgabeEinheit.setItems(ausgabeOptions);
            }
        });

        buttons[0].setOnAction(k -> {
            //=
            //muss angepasst werden, weil es ja kein calculate mehr gibt
            if (!eingabe.getText().equals("") && eingabeEinheit.getValue() != null && ausgabeEinheit.getValue() != null) {
                try {
                    int commaCounter = 0;
                    String eingabeText = eingabe.getText();
                    for (int i = 0; i < eingabeText.length(); i++) {
                        if (eingabe.getText().charAt(i) == ',') {
                            commaCounter++;
                            eingabeText = eingabeText.replace(',', '.');
                        } else if (eingabe.getText().charAt(i) == '.') {
                            commaCounter++;
                        }
                    }


                    ausgabe.setText(calculate(String.valueOf(eingabeEinheit.getValue()), Double.parseDouble(eingabeText), String.valueOf(ausgabeEinheit.getValue())));
                    verlauf.appendText("" + eingabeText + " " + eingabeEinheit.getValue() + " = " + ausgabe.getText() + " " + ausgabeEinheit.getValue() + "\n");
                } catch (Exception e) {
                    eingabe.clear();
                    eingabe.setPromptText("Error! Ungültige Eingabe");
                }

            } else if (eingabe.getText().equals("")) {
                eingabe.clear();
                eingabe.setPromptText("Bitte Zahlen eingeben!");
            } else if (eingabeEinheit.getValue() == null && ausgabeEinheit.getValue() == null) {
                eingabe.setPromptText("Bitte Einheiten auswählen!");
            }
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
                buttons[0].fire();
            }
        });

        return scene;
    }
}
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Calculator {
    History history;

    public void loadHistory() {

    }

    public void saveHistory() {

    }

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

        buttons[0].setOnMouseClicked(k -> {
            //0
        });

        buttons[1].setOnMouseClicked(k -> {
            //1
        });

        buttons[2].setOnMouseClicked(k -> {
            //2
        });

        buttons[3].setOnMouseClicked(k -> {
            //3
        });

        buttons[4].setOnMouseClicked(k -> {
            //4
        });

        buttons[5].setOnMouseClicked(k -> {
            //5
        });

        buttons[6].setOnMouseClicked(k -> {
            //6
        });

        buttons[7].setOnMouseClicked(k -> {
            //7
        });

        buttons[8].setOnMouseClicked(k -> {
            //8
        });

        buttons[9].setOnMouseClicked(k -> {
            //9
        });

        buttons[10].setOnMouseClicked(k -> {
            //=
        });

        buttons[11].setOnMouseClicked(k -> {
            //+
        });

        buttons[12].setOnMouseClicked(k -> {
            //-
        });

        buttons[13].setOnMouseClicked(k -> {
            ///
        });

        buttons[14].setOnMouseClicked(k -> {
            //*
        });

        buttons[15].setOnMouseClicked(k -> {
            //C
        });

        buttons[16].setOnMouseClicked(k -> {
            //.
        });

        buttons[17].setOnMouseClicked(k -> {
            //Back
        });

        buttons[18].setOnMouseClicked(k -> {
            //+/-
        });

        buttons[19].setOnMouseClicked(k -> {
            //CE
        });

        buttons[20].setOnMouseClicked(k -> {
            //Save
        });

        return scene;
    }
}
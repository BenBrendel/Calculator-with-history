import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Scene scene = new Scene(gridPane, 600, 400);

        gridPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5));

        TextArea textarea1 = new TextArea();
        Button button0 = new Button("0");
        Button button1 = new Button("1");
        Button button2 = new Button("2");
        Button button3 = new Button("3");
        Button button4 = new Button("4");
        Button button5 = new Button("5");
        Button button6 = new Button("6");
        Button button7 = new Button("7");
        Button button8 = new Button("8");
        Button button9 = new Button("9");
        Button button10 = new Button("=");
        Button button11 = new Button("+");
        Button button12 = new Button("-");
        Button button13 = new Button("9");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        textarea1.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button0.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button1.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button2.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button3.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button4.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button5.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button6.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button7.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button8.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        button9.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());

        gridPane.add(textarea1, 0, 0, 3, 1);
        gridPane.add(button0, 1, 1);
        gridPane.add(button1, 0, 2);
        gridPane.add(button2, 1, 2);
        gridPane.add(button3, 2, 2);
        gridPane.add(button4, 0, 3);
        gridPane.add(button5, 1, 3);
        gridPane.add(button6, 2, 3);
        gridPane.add(button7, 0, 4);
        gridPane.add(button8, 1, 4);
        gridPane.add(button9, 2, 4);
        return scene;
    }
}
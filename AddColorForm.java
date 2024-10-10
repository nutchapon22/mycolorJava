import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class AddColorForm extends Application {

    private int userId;

    public AddColorForm(int userId) {
        this.userId = userId;
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Add New Color");
        titleLabel.getStyleClass().add("title-label");

        Label nameLabel = new Label("Color Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter color name");
        nameField.getStyleClass().add("input-field");

        Label colorLabel = new Label("Select Color:");
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.getStyleClass().add("color-picker");

        Button saveButton = new Button("Save Color");
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnAction(click -> {
            String colorName = nameField.getText();
            Color selectedColor = colorPicker.getValue();
            String hexColor = "#" + selectedColor.toString().substring(2, 8).toUpperCase();

            DBcon db = new DBcon();
            db.addcolor(hexColor, colorName, userId);
            db.CloseDb();

            ColorCard colorCard = new ColorCard();
            colorCard.start(primaryStage, userId);
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-button");
        cancelButton.setOnAction(click -> {
            ColorCard colorCard = new ColorCard();
            colorCard.start(primaryStage, userId);
        });

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox formBox = new VBox(15, titleLabel, nameLabel, nameField, colorLabel, colorPicker, buttonBox);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(20));
        formBox.getStyleClass().add("form-box");

        BorderPane root = new BorderPane();
        root.setCenter(formBox);

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style/addcolorform.css").toExternalForm());

        primaryStage.setTitle("Add New Color");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

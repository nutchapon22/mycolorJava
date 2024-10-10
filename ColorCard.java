import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class ColorCard extends Application {

    public void start(Stage primaryStage, int userId) {
        // สร้าง GridPane สำหรับเก็บรายการสี
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        DBcon db = new DBcon();
        try {
            ResultSet resultSet = db.getcolorUser(userId);
            int row = 0;
            int col = 0;

            while (resultSet.next()) {
                final int colorid = resultSet.getInt("colorid");
                String colorName = resultSet.getString("colorname");
                String colorHex = resultSet.getString("wheelid");

                Rectangle colorCard = new Rectangle(100, 100);
                colorCard.setFill(Color.web(colorHex));
                colorCard.getStyleClass().add("color-card");

                Label hexlabel = new Label(colorHex);
                hexlabel.getStyleClass().add("hex-label");

                Label colorLabel = new Label(colorName);
                colorLabel.getStyleClass().add("color-label");

                VBox colorBox = new VBox(20, colorCard, hexlabel, colorLabel);
                colorBox.setAlignment(Pos.CENTER);
                colorBox.getStyleClass().add("color-box");

                Button deleteButton = new Button("Delete");
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setOnAction(click -> {
                    try {
                        db.deletecolor(colorid);
                        colorBox.setVisible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                colorBox.getChildren().add(deleteButton);
                grid.add(colorBox, col, row);

                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.CloseDb();
        }

        // ห่อ GridPane ด้วย ScrollPane
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        // ปุ่มเพิ่มสี
        Button addButton = new Button("Add Color");
        addButton.getStyleClass().add("add-button");
        addButton.setOnAction(click -> {
            AddColorForm addColorForm = new AddColorForm(userId);
            addColorForm.start(primaryStage);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("logout-button");
        logoutButton.setOnAction(click -> {
            LoginForm loginForm = new LoginForm();
            loginForm.start(primaryStage);
        });

        // สร้าง HBox สำหรับปุ่ม
        HBox topRightBox = new HBox(addButton, logoutButton);
        topRightBox.setAlignment(Pos.TOP_RIGHT);
        topRightBox.setPadding(new Insets(10, 10, 0, 0));

        // จัด Layout ด้วย BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setTop(topRightBox);

        // กำหนด Scene
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style/colorcard.css").toExternalForm());

        primaryStage.setTitle("Color Card App");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // ปิดการ resize หน้าต่าง
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        // ไม่ใช้งานในกรณีนี้
    }

    public static void main(String[] args) {
        launch(args);
    }
}

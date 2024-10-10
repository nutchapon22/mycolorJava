import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ColorCard extends Application {

    public void start(Stage primaryStage, int userId) {
        GridPane grid = new GridPane();
        try {
            System.out.println("from ColorCard: " + userId);
            DBcon db = new DBcon();
            ResultSet resultSet = db.getcolorUser(userId);

            int row = 0;
            while (resultSet.next()) {
                String colorName = resultSet.getString("colorname");
                String colorHex = resultSet.getString("wheelid");
                Rectangle colorCard = new Rectangle(100, 100);
                colorCard.setFill(Color.web(colorHex));
                Label colorLabel = new Label(colorName);
                VBox colorBox = new VBox(colorCard, colorLabel);
                grid.add(colorBox, 0, row);
                row++;
            }

            // แสดง userId
            row++;

            db.CloseDb();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setTitle("Color Card App");
        primaryStage.setScene(scene);
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

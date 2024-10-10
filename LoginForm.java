import java.sql.ResultSet;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        DBcon db = new DBcon();
        // สร้าง label และ input fields
        Label welcomeLabel = new Label("Welcome To My Color!");
        Label ErrorLabel = new Label("");
        ErrorLabel.setStyle("-fx-text-fill: red;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // ปุ่ม login
        Button loginButton = new Button("Login");
        loginButton.setOnAction(click -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (db.Login(username, password)) {
                System.out.println("Login Success");
                ResultSet rb = db.GetUserData(username, password);
                int userid = -1; // Initialize userid
                try {
                    while (rb.next()) {
                        userid = rb.getInt("userid");
                        System.out.println(userid);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (userid != -1) { // Check if userid was set
                    ColorCard mainPage = new ColorCard();
                    mainPage.start(primaryStage, userid);
                } else {
                    System.out.println("User ID not found");
                    ErrorLabel.setText("User ID not found");
                }
            } else {
                System.out.println("Login Failed");
                ErrorLabel.setText("Login Failed");
            }
        });

        // ปุ่ม Register
        Button registerButton = new Button("Register");
        registerButton.setOnAction(click -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.start(primaryStage);
        });
        registerButton.setStyle("-fx-background-color: #ffcc00;");
        registerButton.setPrefWidth(100);

        VBox vbox = new VBox(10, welcomeLabel, usernameField, passwordField, loginButton, registerButton, ErrorLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #f0f4f8;");

        // ใช้ HBox ในการจัดกล่องกลาง
        HBox hbox = new HBox(vbox);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #336699;");

        // จัดการ Scene
        Scene scene = new Scene(hbox, 400, 500);

        // โหลดสไตล์จาก CSS
        scene.getStylesheets().add(getClass().getResource("style/loginform.css").toExternalForm());

        // ตั้งค่าหน้าต่าง
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

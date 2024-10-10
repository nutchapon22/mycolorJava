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

        Label welcomeLabel = new Label("Welcome To My Color!");
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(click -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (db.Login(username, password)) {
                System.out.println("Login Success");
                ResultSet resultSet = db.GetUserData(username, password);
                int userId = -1;
                try {
                    while (resultSet.next()) {
                        userId = resultSet.getInt("userid");
                        System.out.println(userId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (userId != -1) {
                    ColorCard mainPage = new ColorCard();
                    mainPage.start(primaryStage, userId);
                } else {
                    System.out.println("User ID not found");
                    errorLabel.setText("User ID not found");
                }
            } else {
                System.out.println("Login Failed");
                errorLabel.setText("Login Failed");
            }
        });

        Button registerButton = new Button("Register");
        registerButton.setOnAction(click -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.start(primaryStage);
        });
        registerButton.setStyle("-fx-background-color: #ffcc00;");
        registerButton.setPrefWidth(100);

        VBox vbox = new VBox(10, welcomeLabel, usernameField, passwordField, loginButton, registerButton, errorLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #f0f4f8;");

        HBox hbox = new HBox(vbox);
        hbox.setAlignment(Pos.CENTER);
        // hbox.setStyle("-fx-background-color: #336699;");

        Scene scene = new Scene(hbox, 400, 500);
        scene.getStylesheets().add(getClass().getResource("style/loginform.css").toExternalForm());

        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

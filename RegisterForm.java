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

public class RegisterForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        DBcon db = new DBcon();

        Label welcomeLabel = new Label("Register");
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button registerButton = new Button("Register");
        registerButton.setOnAction(click -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            if (db.Register(username, email, password)) {
                System.out.println("Register Success");
                LoginForm loginForm = new LoginForm();
                loginForm.start(primaryStage);
            } else {
                System.out.println("Register Failed");
                errorLabel.setText("Register Failed");
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #ff4d4d;");
        backButton.setOnAction(click -> {
            LoginForm loginForm = new LoginForm();
            loginForm.start(primaryStage);
        });

        VBox vbox = new VBox(10, welcomeLabel, usernameField, emailField, passwordField, errorLabel, registerButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #f0f4f8;");

        HBox hbox = new HBox(vbox);
        hbox.setAlignment(Pos.CENTER);
        // hbox.setStyle("-fx-background-color: #336699;");

        Scene scene = new Scene(hbox, 400, 500);
        scene.getStylesheets().add(getClass().getResource("style/loginform.css").toExternalForm());

        primaryStage.setTitle("Register Form");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

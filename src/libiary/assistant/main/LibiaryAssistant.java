package libiary.assistant.main;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import libiary.assistant.DataBase.Database;
import libiary.assistant.Util.MessageBox;

public class LibiaryAssistant extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try {
            Database db = Database.getInstance();
        } catch (SQLException e) {
            MessageBox.showAndWaitErrorMessage("Can't Connected to DataBase");
            e.printStackTrace();
        }
        
        
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.getIcons().add(new Image("/libiary/assistant/Icon/book.png"));
        stage.setTitle("Libary Assistant");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

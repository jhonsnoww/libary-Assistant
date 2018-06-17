package libiary.assistant.dbconfig;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import libiary.assistant.Util.DataBaseConfigManeger;
import libiary.assistant.Util.DataBaseProperty;


public class DatabaseConfigController implements Initializable {

    @FXML
    private TextField hostField;
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passFileld;
    @FXML
    private JFXButton saveBtn;
private DataBaseConfigManeger dbManger;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbManger = new DataBaseConfigManeger();
        
        DataBaseProperty dbProp = dbManger.getDatabaseProperry();
        
        hostField.setText(dbProp.getHost());
        nameField.setText(dbProp.getUser());
        passFileld.setText(dbProp.getPassword());
                
                
        SpinnerValueFactory<Integer> valueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3320,Integer.parseInt(dbProp.getPort()));
        portSpinner.setValueFactory(valueFactory);
    }    

    @FXML
    private void saveDataBaseConfig(ActionEvent event) throws IOException {
        
        String host = hostField.getText();
        String port = portSpinner.getValue().toString();
        String user = nameField.getText();
        String password = passFileld.getText();
        
        DataBaseProperty dbProperty = new DataBaseProperty(host, port, user, password);
        dbManger.saveDatabaseProperty(dbProperty);
        
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
       
        
    }
    
}

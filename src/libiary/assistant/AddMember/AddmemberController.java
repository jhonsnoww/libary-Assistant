package libiary.assistant.AddMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import libiary.assistant.Model.Member;
import libiary.assistant.dao.MemberDao;

public class AddmemberController implements Initializable {

    @FXML
    private JFXTextField memberIdField;
    @FXML
    private JFXTextField nameidField;
    @FXML
    private JFXTextField EmailFiled;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXButton saveId;

    private MemberDao mdao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mdao = new MemberDao();
    }

    @FXML
    private void saveMemberBtn(ActionEvent event) throws ClassNotFoundException {
        String id = memberIdField.getText();
        String name = nameidField.getText();
        String email = EmailFiled.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        System.out.println("ID : " + id + "Name : " + name + "Email : " + email + "Mobile : " + mobile + "Address : " + address);

        Member member = new Member(id, name, email, mobile, address);
        try {
            mdao.saveMember(member);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success !");
            alert.setHeaderText(null);
            alert.setContentText("Member Adding Success....");
            alert.show();

        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error !");
            alert.setHeaderText(null);
            alert.setContentText("Member Adding Faild....");
            alert.show();

        }
    }

}

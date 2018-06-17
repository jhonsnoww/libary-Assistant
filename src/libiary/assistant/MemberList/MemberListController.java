package libiary.assistant.MemberList;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import libiary.assistant.Model.Member;
import libiary.assistant.booklist.BookListController;
import libiary.assistant.dao.MemberDao;

public class MemberListController implements Initializable {

    @FXML
    private TableColumn<Member, String> idcolumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> emailColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> addressColumn;
    @FXML
    private TableView<Member> memberTable;

    private MemberDao mdao;
    @FXML
    private MenuItem editBtn;
    @FXML
    private MenuItem deleteBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mdao = new MemberDao();
        initColumn();
        loadTableMember();
    }

    private void initColumn() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

    }

    private void loadTableMember() {

        ObservableList<Member> list;
        try {
            list = mdao.getMember();
            memberTable.getItems().setAll(list);

        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void editmember(ActionEvent event) {
    }

    @FXML
    private void deletemember(ActionEvent event) throws SQLException {
        Member m = memberTable.getSelectionModel().getSelectedItem();
        
        if(m!=null){
            try {
                mdao.deleteMember(m.getId());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            memberTable.getItems().remove(m);
        }
    }

}

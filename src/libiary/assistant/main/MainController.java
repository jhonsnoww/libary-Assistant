package libiary.assistant.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import libiary.assistant.Model.Book;
import libiary.assistant.Model.Member;
import libiary.assistant.Util.MessageBox;
import libiary.assistant.dao.BookDao;
import libiary.assistant.dao.IssueInfoDao;
import libiary.assistant.dao.MemberDao;

public class MainController implements Initializable {

    @FXML
    private JFXButton addBookBtn;
    @FXML
    private JFXButton homebtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private TabPane homeView;
    @FXML
    private JFXButton addmemberid;
    @FXML
    private JFXButton booklistid;
    @FXML
    private JFXButton memberid;
    @FXML
    private JFXTextField bookidField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;

    private BookDao bdao;
    @FXML
    private JFXTextField memberidField;
    @FXML
    private Text name;
    @FXML
    private Text emal;
    @FXML
    private Text mobile;
    @FXML
    private Text address;

    private MemberDao mdao;
    @FXML
    private Text availableText;
    @FXML
    private JFXButton issueBtn;

    private IssueInfoDao issueinfodao;
    @FXML
    private JFXTextField issueBookIdField;
    @FXML
    private Text mID;
    @FXML
    private Text mNameText;
    @FXML
    private Text mEmailText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisher;
    @FXML
    private Text issueDate;
    @FXML
    private Text rEnewCount;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton subBtn;
    @FXML
    private MenuItem configitem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bdao = new BookDao();
        mdao = new MemberDao();
        issueinfodao = new IssueInfoDao();
    }

    @FXML
    private void loadAddBookView(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/libiary/assistant/AddBook/addBook.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);

    }

    @FXML
    private void homeView(ActionEvent event) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(homeView);
    }

    @FXML
    private void btnAddmember(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/libiary/assistant/AddMember/addmember.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void btnBookList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/libiary/assistant/booklist/BookList.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void btnMemberList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/libiary/assistant/MemberList/memberList.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void setBookInfo(ActionEvent event) {

        clearBookCache();

        String id = bookidField.getText();

        if (id.isEmpty()) {
            System.out.println("Please Enter Book ID First !!! ");
            return;
        }

        try {
            Book book = bdao.getBook(id);

            if (book != null) {
                titleText.setText(book.getTitle());
                authorText.setText(book.getAuthor());
                publisherText.setText(book.getPublisher());
                boolean available = book.isAvailable();
                if (available) {
                    availableText.setText("Available");
                } else {
                    availableText.setText("Not Available");
                }

            } else {
               MessageBox.showErrorMessage("Can't find any Book for This id ...");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void setMemberInfo(ActionEvent event) {

        clearMemberInfo();
        String id = memberidField.getText();

        if (id.isEmpty()) {
            System.out.println("Please Enter member ID First !!! ");
            return;
        }

        try {
            Member m = mdao.getMemberinfo(id);

            if (m != null) {
                name.setText(m.getName());
                emal.setText(m.getEmail());
                mobile.setText(m.getMobile());
                address.setText(m.getAddress());
            } else {
               MessageBox.showErrorMessage("Can't find for this members");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearBookCache() {

        titleText.setText("-");
        authorText.setText("-");
        publisherText.setText("-");
        availableText.setText("-");
    }

    private void clearMemberInfo() {
        name.setText("-");
        emal.setText("-");
        address.setText("-");
        mobile.setText("-");

    }

    @FXML
    private void issueBook(ActionEvent event) throws ClassNotFoundException, SQLException {

        String bookId = bookidField.getText();
        String memberID = memberidField.getText();

        if (bookId.isEmpty() || memberID.isEmpty()) {
            System.out.println("Enter BookId And Member Id ");
            return;

        }

        System.out.println("Book Id :" + bookId + "Member ID : " + memberID);

        Book book = bdao.getBook(bookId);

        if (book.isAvailable()) {

            issueinfodao.saveIssueInfo(new IssueInfo(memberID, bookId));
            bdao.updateAvailablity(bookId, false);

            System.out.println("Book Issue Success. ");
        } else {
            System.out.println("This book is Already issued. ");
        }
    }

    @FXML
    private void setIssueBookInfo(ActionEvent event) {

        clearIssueInfo();

        String bookID = issueBookIdField.getText();
        if (bookID.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter BOOkID First....");
            alert.showAndWait();
            return;
        }
        try {
            IssueInfo issueInfo = issueinfodao.getIssueInfo(bookID);
            if (issueInfo != null) {

                Book book = bdao.getBook(bookID);

                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                bPublisher.setText(book.getPublisher());

                Member member = mdao.getMemberinfo(issueInfo.getMamberId());

                mNameText.setText(member.getName());
                mEmailText.setText(member.getEmail());
                mMobileText.setText(member.getMobile());
                mAddressText.setText(member.getAddress());
                
                mID.setText(issueInfo.getMamberId());
                issueDate.setText("Issue Date : " + issueInfo.getIssueDate().toString());
                rEnewCount.setText("Renew Count : " + issueInfo.getRenewCount());
            }
           
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void reNewBook(ActionEvent event) {
        String bookid = issueBookIdField.getText();

        if (bookid.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter BOOkID First....");
            alert.showAndWait();
            return;
        }

        try {
            IssueInfo issueInfo = issueinfodao.getIssueInfo(bookid);

            if (issueInfo != null) {
                issueinfodao.updateReNewCount(bookid);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void submitBook(ActionEvent event) {

        String bookid = issueBookIdField.getText();

        if (bookid.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter BOOkID First....");
            alert.showAndWait();
            return;
        }

        try {
            IssueInfo issueInfo = issueinfodao.getIssueInfo(bookid);

            if (issueInfo != null) {
                issueinfodao.deleteIssueInfo(bookid);
                bdao.updateAvailablity(bookid, true);
            } else {
                System.out.println("Can't Find Any Book ....");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearIssueInfo() {

        mID.setText("-");
        mNameText.setText("-");
        mEmailText.setText("-");
        mMobileText.setText("-");
        mAddressText.setText("-");

        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisher.setText("-");

        issueDate.setText("-");
        rEnewCount.setText("-");

    }

    @FXML
    private void loadDatabaseConfigView(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/libiary/assistant/dbconfig/databaseConfig.fxml"));
       
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.initOwner(centerPane.getScene().getWindow());
         stage.initModality(Modality.WINDOW_MODAL);
         stage.showAndWait();
         
         MessageBox.showAndWaitMessage("Please restart your app");
         
         Stage currentStage = (Stage) centerPane.getScene().getWindow();
         currentStage.close();
         
        
    }

}

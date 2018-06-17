package libiary.assistant.AddBook;

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
import libiary.assistant.Model.Book;
import libiary.assistant.dao.BookDao;

public class AddBookController implements Initializable {

    @FXML
    private JFXButton saveId;
    @FXML
    private JFXTextField bookidField;
    @FXML
    private JFXTextField bookTitlefield;
    @FXML
    private JFXTextField authoridfield;
    @FXML
    private JFXTextField publicheridfield;
    private BookDao bookDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDao = new BookDao();
    }

    @FXML
    private void saveBookBtn(ActionEvent event) throws ClassNotFoundException {

        String id = bookidField.getText();
        String title = bookTitlefield.getText();
        String author = authoridfield.getText();
        String publicher = publicheridfield.getText();

        System.out.println("ID : " + id + "Title : " + title + "Author : " + author + "publicer : " + publicher);

        Book book = new Book(id, title, author, publicher);
        try {
            bookDao.saveBook(book);
            bookidField.clear();
            bookTitlefield.clear();
            authoridfield.clear();
            publicheridfield.clear();
            
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success !");
            alert.setHeaderText(null);
            alert.setContentText("Book Adding Success....");
            alert.show();
           
        } catch (SQLException ex) {
            
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !");
            alert.setHeaderText(null);
            alert.setContentText("Book Adding Faild....");
            alert.show();
           
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

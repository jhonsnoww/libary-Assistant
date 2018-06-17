package libiary.assistant.editbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import libiary.assistant.Model.Book;
import libiary.assistant.dao.BookDao;

public class EditbookController implements Initializable {

    @FXML
    private JFXTextField bookidField;
    @FXML
    private JFXTextField bookTitlefield;
    @FXML
    private JFXTextField authoridfield;
    @FXML
    private JFXTextField publicheridfield;
    @FXML
    private JFXButton updatebtn;
    @FXML
    private JFXButton canclebtn;

    private BookDao bdao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bdao = new BookDao();
    }

    @FXML
    private void updatebook(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        
        String id = bookidField.getText();
        String title = bookTitlefield.getText();
        String author = authoridfield.getText();
        String publisher = publicheridfield.getText();

        if (id.isEmpty() || title.isEmpty()) {
            System.out.println("Plaese Fill in all Fields");
            return;
        }
        Book book = new Book(id, title, author, publisher);
        bdao.updateBook(book);
        Stage stage = (Stage) updatebtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) updatebtn.getScene().getWindow();
        stage.close();

    }

    public void setBookInfo(Book selectedbook) {

        bookidField.setText(selectedbook.getId());
        bookTitlefield.setText(selectedbook.getTitle());
        authoridfield.setText(selectedbook.getAuthor());
        publicheridfield.setText(selectedbook.getPublisher());

    }

}

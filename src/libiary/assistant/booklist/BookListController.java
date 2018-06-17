package libiary.assistant.booklist;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import libiary.assistant.Model.Book;
import libiary.assistant.Util.MessageBox;
import libiary.assistant.dao.BookDao;
import libiary.assistant.editbook.EditbookController;

public class BookListController implements Initializable {

    @FXML
    private TableView<Book> booktable;
    @FXML
    private TableColumn<Book, String> idcolumn;
    @FXML
    private TableColumn<Book, String> titlecolumn;
    @FXML
    private TableColumn<Book, String> authorcolumn;
    @FXML
    private TableColumn<Book, String> publishercolumn;

    private BookDao bookdao;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private MenuItem edititem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookdao = new BookDao();
        initColumn();
        loadData();

    }

    private void initColumn() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publishercolumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }

    private void loadData() {

        ObservableList<Book> list;
        try {
            list = bookdao.getBooks();
            booktable.getItems().setAll(list);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void delelebook(ActionEvent event) {

        Book selectBook = booktable.getSelectionModel().getSelectedItem();
        if (selectBook != null) {

           Optional<ButtonType> selectedoption = MessageBox.showConfimMessage("Are You sure you want to delete this book");
            
            if(selectedoption.get() == ButtonType.OK){
                 ////////////////
            try {
                bookdao.deleteBook(selectBook.getId());
                //  loadData();
                booktable.getItems().remove(selectBook);
            } catch (SQLException ex) {
                Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
            /////////////////
          
            }
           
        }
        System.out.println(selectBook);
    }

    @FXML
    private void editbook(ActionEvent event) throws IOException {

        Book selectedbook = booktable.getSelectionModel().getSelectedItem();

        if (selectedbook != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/libiary/assistant/editbook/editbook.fxml"));
            Parent root = loader.load();
            EditbookController edcontroler = loader.getController();
            edcontroler.setBookInfo(selectedbook);

            Stage stage = new Stage();
            stage.initOwner(booktable.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
            loadData();
        }

    }

}

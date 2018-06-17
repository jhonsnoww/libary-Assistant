/**
 *  DAO = DataAccess Object
 */
package libiary.assistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libiary.assistant.DataBase.Database;
import libiary.assistant.Model.Book;

public class BookDao {

    public void saveBook(Book book) throws SQLException, ClassNotFoundException {

        Connection con = Database.getInstance().getConnection();

        String sql = "insert into lbdb.books (id,title,author,publisher) value (?,?,?,?)";

        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, book.getId());
        stmt.setString(2, book.getTitle());
        stmt.setString(3, book.getAuthor());
        stmt.setString(4, book.getPublisher());

        stmt.execute();

    }

    public ObservableList<Book> getBooks() throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();

        String sql = "select * from lbdb.books";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ObservableList<Book> list = FXCollections.observableArrayList();

        while (rs.next()) {
            String id = rs.getString("id");
            String titel = rs.getString("title");
            String author = rs.getString("author");
            String publisher = rs.getString("publisher");

            Book book = new Book(id, titel, author, publisher);
            list.add(book);

        }

        return list;
    }

    public Book getBook(String id) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();

        String sql = "select * from lbdb.books where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();

        Book book = null;
        if (rs.next()) {
            String titel = rs.getString("title");
            String author = rs.getString("author");
            String publisher = rs.getString("publisher");
            boolean available = rs.getBoolean("available");

            book = new Book(id, titel, author, publisher, available);

        }

        return book;
    }

    public void updateAvailablity(String id, boolean available) throws SQLException, ClassNotFoundException {

        Connection con = Database.getInstance().getConnection();

        String sql = "update lbdb.books set available=? where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setBoolean(1, available);
        stmt.setString(2, id);
        stmt.execute();

    }

    public void deleteBook(String id) throws SQLException, ClassNotFoundException {

        Connection con = Database.getInstance().getConnection();

        String sql = "delete from lbdb.books where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.execute();
    }

    public void updateBook(Book book) throws SQLException, ClassNotFoundException {
       
        Connection con = Database.getInstance().getConnection();

        String sql = "update lbdb.books set title=?, author=?, publisher=? where id=?";

        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getPublisher());
        stmt.setString(4, book.getId());

        stmt.execute();

    }

}

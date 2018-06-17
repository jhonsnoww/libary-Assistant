package libiary.assistant.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import libiary.assistant.Util.DataBaseConfigManeger;
import libiary.assistant.Util.DataBaseProperty;

public class Database {

    private static Database db;

    private Connection con;

    private Database() throws ClassNotFoundException, SQLException {
        connect();
        createDataBase();
        createTables();
    }

    public static Database getInstance() throws SQLException, ClassNotFoundException {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataBaseConfigManeger dbManger = new DataBaseConfigManeger();
        DataBaseProperty dbPropety = dbManger.getDatabaseProperry();

        con = DriverManager.getConnection("jdbc:mysql://" + dbPropety.getHost() + ":" + dbPropety.getPort() + "/", dbPropety.getUser(), dbPropety.getPassword());
        System.out.println("Connected to DataBase Successfully");

    }

    public void createDataBase() {
        try {
            Statement stmt = con.createStatement();
            stmt.execute("create database if not exists lbdb");
            System.out.println("Created DataBase Success");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createTables() throws SQLException {
        Statement stmt1 = con.createStatement();
        Statement stmt2 = con.createStatement();
        Statement stmt3 = con.createStatement();

        stmt1.execute("create table if not exists lbdb.books (Id varchar(44) primary key,Title varchar(225),Author varchar(44),publisher varchar(44),available boolean default true)");
        stmt2.execute("create table if not exists lbdb.Members (Id varchar(44) primary key,Name varchar(225),Email varchar(44),Address varchar(44),Mobile varchar(44))");
        stmt3.execute("create table if not exists lbdb.issue (member_id varchar(44),book_id varchar(44),issue_date date,renew_count int,foreign key (member_id) references members(id),foreign key (book_id) references books(id))");
        System.out.println("create table success");
    }

    public Connection getConnection() {
        return con;
    }

}

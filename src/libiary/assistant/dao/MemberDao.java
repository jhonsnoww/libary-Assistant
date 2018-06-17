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
import libiary.assistant.Model.Member;

public class MemberDao {

    public void saveMember(Member member) throws SQLException, ClassNotFoundException {

        Connection con = Database.getInstance().getConnection();

        String sql = "insert into lbdb.members (id,name,email,mobile,address) value (?,?,?,?,?)";

        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, member.getId());
        stmt.setString(2, member.getName());
        stmt.setString(3, member.getEmail());
        stmt.setString(4, member.getMobile());
        stmt.setString(5, member.getAddress());

        stmt.execute();

    }

    public ObservableList<Member> getMember() throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();

        String sql = "select * from lbdb.members";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ObservableList<Member> list = FXCollections.observableArrayList();

        while (rs.next()) {
            String id = rs.getString("id");
            String titel = rs.getString("name");
            String email = rs.getString("email");
            String mobile = rs.getString("mobile");
            String address = rs.getString("address");

            Member m = new Member(id, titel, email, mobile, address);

            list.add(m);

        }

        return list;

    }

    public Member getMemberinfo(String id) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();

        String sql = "select * from lbdb.members where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        Member m = null;

        if (rs.next()) {
            String name = rs.getString("name");
            String email = rs.getString("email");
            String mobile = rs.getString("mobile");
            String address = rs.getString("address");

            m = new Member(id, name, email, mobile, address);

        }

        return m;
    }

    public void deleteMember(String id) throws SQLException, ClassNotFoundException {
 
        Connection con = Database.getInstance().getConnection();

        String sql = "delete from lbdb.members where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.execute();

    }

}

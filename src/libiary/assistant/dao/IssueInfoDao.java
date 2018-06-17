/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libiary.assistant.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import libiary.assistant.DataBase.Database;
import libiary.assistant.main.IssueInfo;

public class IssueInfoDao {

    public void saveIssueInfo(IssueInfo issueinfo) throws SQLException, ClassNotFoundException {

        Connection con = Database.getInstance().getConnection();

        String sql = "insert into lbdb.issue (member_id,book_id,issue_date,renew_count) value (?,?,now(),0)";

        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, issueinfo.getMamberId());
        stmt.setString(2, issueinfo.getBookId());

        stmt.execute();
    }

    public IssueInfo getIssueInfo(String bookId) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();

        String sql = "select * from lbdb.issue where book_id=?";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, bookId);
        ResultSet rs = stmt.executeQuery();

        IssueInfo issueInfo = null;
        if (rs.next()) {

            String memberId = rs.getString("member_id");
            Date issueDate = rs.getDate("issue_date");
            int renewCount = rs.getInt("renew_count");

            issueInfo = new IssueInfo(memberId, bookId, issueDate, renewCount);
        }

        return issueInfo;
    }

    public void deleteIssueInfo(String id) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();

        String sql = "delete from lbdb.issue where book_id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.execute();

    }

    public void updateReNewCount(String id) throws SQLException, ClassNotFoundException {
         Connection con = Database.getInstance().getConnection();

        String sql = "update lbdb.issue set renew_count=renew_count+1 where book_id=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.execute();
    }

}

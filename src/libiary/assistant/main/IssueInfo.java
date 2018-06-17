
package libiary.assistant.main;

import java.sql.Date;


public class IssueInfo {
    
    private String mamberId;
    private String bookId;
    private Date issueDate;
    private int renewCount;

    public IssueInfo(String mamberId, String bookId) {
        this.mamberId = mamberId;
        this.bookId = bookId;
    }

    public IssueInfo(String mamberId, String bookId, Date issueDate, int renewCount) {
        this.mamberId = mamberId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.renewCount = renewCount;
    }

    public String getMamberId() {
        return mamberId;
    }

    public void setMamberId(String mamberId) {
        this.mamberId = mamberId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }
    
    
    
}

package vo;

import org.opensaml.xml.signature.P;

import java.io.Serializable;
import java.util.Date;

public class UserLog implements Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private Date loginTime;
    private Date logOutTime;

    public UserLog() {
    }

    public UserLog(Long id, Long userId, String userName, Date loginTime, Date logOutTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.loginTime = loginTime;
        this.logOutTime = logOutTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogOutTime() {
        return logOutTime;
    }

    public void setLogOutTime(Date logOutTime) {
        this.logOutTime = logOutTime;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", loginTime=" + loginTime +
                ", logOutTime=" + logOutTime +
                '}';
    }
}

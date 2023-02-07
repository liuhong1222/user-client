package cn.entity;

import java.io.Serializable;
import java.util.Date;

public class LoginLog implements Serializable{

	private static final long serialVersionUID = 669307881761478553L;

	private Integer id;
	
	private String userName;
	
	private Date logindate;
  	
	private String loginIp;
	
	private Date creteTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getCreteTime() {
		return creteTime;
	}

	public void setCreteTime(Date creteTime) {
		this.creteTime = creteTime;
	}
}

package cn.service;

import main.java.cn.common.BackResult;

public interface EmailService{
	
	BackResult<String> sendMail(String userName, String email,String mailCode,String userId);
}

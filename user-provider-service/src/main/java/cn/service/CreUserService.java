package cn.service;

import java.util.Map;

import main.java.cn.common.BackResult;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

public interface CreUserService{
		
	BackResult<ApiAccountInfoDomain> getApiAccountInfoByUserName(String userName);
	
	BackResult<CreUserDomain> findByUserName(String userName);
	
	BackResult<CreUserDomain> findById(String id);
	
	BackResult<Map<String,Object>> findCreUserInfobyUserName(String userName);
	
	BackResult<String> bindCreUserMail(String userId,String email);
	
	BackResult<String> getResultPwd(String creUserId);
	
	BackResult<Boolean> consumeAccount(String creUserId,int count);
}

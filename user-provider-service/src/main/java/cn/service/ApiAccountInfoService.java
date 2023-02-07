package cn.service;

import main.java.cn.common.BackResult;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

public interface ApiAccountInfoService{

	BackResult<String> updateApiAccountInfo(ApiAccountInfoDomain apiAccountInfoDomain);
	
	BackResult<CreUserDomain> findCreUserBymail(String email);
	
	BackResult<ApiAccountInfoDomain> findByUserName(String userName);
}

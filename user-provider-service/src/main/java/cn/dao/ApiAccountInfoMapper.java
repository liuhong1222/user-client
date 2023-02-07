package cn.dao;

import org.apache.ibatis.annotations.Mapper;

import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

@Mapper
public interface ApiAccountInfoMapper {
	
	int updateApiAccountInfo(ApiAccountInfoDomain info);
	
	CreUserDomain findCreUserBymail(String email);
	
	ApiAccountInfoDomain findByUserName(String userName);
	
	ApiAccountInfoDomain findByUserId(String userId);
	
	int saveApiAccountInfo(ApiAccountInfoDomain info);
}

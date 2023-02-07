package cn.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

@Mapper
public interface CreUserMapper {

	ApiAccountInfoDomain getApiAccountInfoByUserName(String userName);
	
	int updateCreUserLoginIp(Map<String,String> param);
	
	CreUserDomain findByUserName(String userName);
	
	CreUserDomain findById(String id);
	
	Map<String,Object> findCreUserInfobyUserName(String userName);
	
	int bindCreUserMail(Map<String,String> param);
	
	long findCountByMail(String mail);
	
	Map<String,Object> findUserNameByMail(Map<String,Object> param);
	
	String getResultPwd(String creUserId);
	
	int consumeAccount(Map<String,Object> param);
}

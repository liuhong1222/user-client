package cn.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.LoginLog;

@Mapper
public interface LoginLogMapper {
		
	int saveLoginLog(LoginLog loginLog);
	
}

package cn.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.MailLog;

@Mapper
public interface MailLogMapper {
		
	int saveMailLog(MailLog mailLog);
	
}

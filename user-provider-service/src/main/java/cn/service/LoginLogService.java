package cn.service;

import java.util.Map;

import main.java.cn.common.BackResult;

public interface LoginLogService{
	
	BackResult<String> saveLoginLog(Map<String,String> param);
}

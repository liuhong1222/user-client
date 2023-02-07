package cn.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.dao.CreUserMapper;
import cn.dao.LoginLogMapper;
import cn.entity.LoginLog;
import cn.service.LoginLogService;
import main.java.cn.common.BackResult;

@Service
public class LoginLogServiceImpl implements LoginLogService {

	private final static Logger logger = LoggerFactory.getLogger(LoginLogServiceImpl.class);

	@Autowired
	private LoginLogMapper loginLogMapper;
	
	@Autowired
	private CreUserMapper creUserMapper;

	@Override
	public BackResult<String> saveLoginLog(Map<String, String> param) {
		BackResult<String> result = new BackResult<String>();
		String userName = param.get("userName").toString();
		String lastLoginIp = param.get("lastLoginIp").toString();
		LoginLog loginLog = new LoginLog();
		loginLog.setLoginIp(lastLoginIp);
		loginLog.setUserName(userName);
		loginLogMapper.saveLoginLog(loginLog);
		creUserMapper.updateCreUserLoginIp(param);
		result.setResultObj("success");
		return result;
	}

	

}

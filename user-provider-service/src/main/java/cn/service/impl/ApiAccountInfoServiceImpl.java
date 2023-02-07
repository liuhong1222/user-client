package cn.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.dao.ApiAccountInfoMapper;
import cn.service.ApiAccountInfoService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

@Service
public class ApiAccountInfoServiceImpl implements ApiAccountInfoService {

	private final static Logger logger = LoggerFactory.getLogger(ApiAccountInfoServiceImpl.class);

	@Autowired
	private ApiAccountInfoMapper apiAccountInfoMapper;

	@Override
	public BackResult<String> updateApiAccountInfo(ApiAccountInfoDomain apiAccountInfoDomain) {
		BackResult<String> result = new BackResult<String>();
		ApiAccountInfoDomain domain = apiAccountInfoMapper.findByUserId(apiAccountInfoDomain.getCreUserId().toString());
		if(domain != null) {
			int counts = apiAccountInfoMapper.updateApiAccountInfo(apiAccountInfoDomain);
			if(counts != 1){
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				result.setResultMsg("操作失败，数据不存在");
				logger.info("用户【" + apiAccountInfoDomain.getCreUserId() + "】修改账户信息失败，信息内容为："  + JSONObject.toJSONString(apiAccountInfoDomain));
				return result;
			}			
		}else {
			int counts = apiAccountInfoMapper.saveApiAccountInfo(apiAccountInfoDomain);
			if(counts != 1){
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				result.setResultMsg("操作失败，数据库异常");
				logger.info("用户【" + apiAccountInfoDomain.getCreUserId() + "】修改账户信息失败，信息内容为："  + JSONObject.toJSONString(apiAccountInfoDomain));
				return result;
			}
		}
		
		logger.info("用户【" + apiAccountInfoDomain.getCreUserId() + "】修改账户信息成功，信息内容为："  + JSONObject.toJSONString(apiAccountInfoDomain));
		result.setResultObj("success");
		return result;
	}

	@Override
	public BackResult<CreUserDomain> findCreUserBymail(String email) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		CreUserDomain creuser = apiAccountInfoMapper.findCreUserBymail(email);
		if(creuser == null){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("操作失败，数据不存在");
			return result;
		}
		
		result.setResultObj(creuser);
		return result;
	}

	@Override
	public BackResult<ApiAccountInfoDomain> findByUserName(String userName) {
		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();
		ApiAccountInfoDomain apiAccountInfoDomain = apiAccountInfoMapper.findByUserName(userName);
		if(apiAccountInfoDomain == null){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("登录失败，帐号不存在");
			return result;
		}
		
		result.setResultObj(apiAccountInfoDomain);
		return result;
	}
}

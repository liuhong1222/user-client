package cn.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.CreUserMapper;
import cn.redis.RedisClient;
import cn.service.CreUserService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

@Service
public class CreUserServiceImpl implements CreUserService {

	private final static Logger logger = LoggerFactory.getLogger(CreUserServiceImpl.class);

	@Autowired
	private CreUserMapper creUserMapper;
	
	@Autowired  
    protected RedisClient redisClinet;  
	
	@Transactional
	public BackResult<ApiAccountInfoDomain> getApiAccountInfoByUserName(String userName) {
		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();
		ApiAccountInfoDomain apiAccountInfoDomain = creUserMapper.getApiAccountInfoByUserName(userName);
		if(apiAccountInfoDomain == null){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("用户不存在");
			return result;
		}
		
		result.setResultObj(apiAccountInfoDomain);
		return result;
	}

	@Override
	public BackResult<CreUserDomain> findByUserName(String userName) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		CreUserDomain creUserDomain = creUserMapper.findByUserName(userName);
		if(creUserDomain == null){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("用户不存在");
			return result;
		}
		
		result.setResultObj(creUserDomain);
		return result;
	}

	@Override
	public BackResult<CreUserDomain> findById(String id) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		CreUserDomain creUserDomain = creUserMapper.findById(id);
		if(creUserDomain == null){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("用户不存在");
			return result;
		}
		
		result.setResultObj(creUserDomain);
		return result;
	}

	@Override
	public BackResult<Map<String, Object>> findCreUserInfobyUserName(String userName) {
		BackResult<Map<String, Object>> result = new BackResult<Map<String, Object>>();
		Map<String, Object> creUserInfo = creUserMapper.findCreUserInfobyUserName(userName);
		if(creUserInfo == null){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("用户不存在");
			return result;
		}
		
		result.setResultObj(creUserInfo);
		return result;
	}

	@Override
	public BackResult<String> bindCreUserMail(String userId, String email) {
		BackResult<String> result = new BackResult<String>();
		long creUsers = creUserMapper.findCountByMail(email);
		if(creUsers > 0){
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("操作失败，邮箱已被使用");
			logger.info("用户【" + userId + "】绑定邮箱失败，邮箱已被绑定");
			return result;
		}
		Map<String,String> param = new HashMap<>();
		param.put("userId", userId);
		param.put("email", email);
		int counts = creUserMapper.bindCreUserMail(param);
		if(counts != 1){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("操作失败，数据不存在");
			logger.info("用户【" + userId + "】绑定邮箱失败");
			return result;
		}
		
		result.setResultObj("success");
		logger.info("用户【" + userId + "】绑定邮箱成功，邮箱为："  + email);
		return result;
	}

	@Override
	public BackResult<String> getResultPwd(String creUserId) {
		BackResult<String> result = new BackResult<String>();
		String resultPwd = creUserMapper.getResultPwd(creUserId);
		if(StringUtils.isBlank(resultPwd)){
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("操作失败，数据不存在");
			return result;
		}
		
		result.setResultObj(resultPwd);
		return result;
	}

	@Override
	public BackResult<Boolean> consumeAccount(String creUserId, int count) {
		BackResult<Boolean> result = new BackResult<Boolean>();
		Map<String,Object> param = new HashMap<>();
		param.put("creUserId", creUserId);
		param.put("count", count);
		int counts = creUserMapper.consumeAccount(param);
		result.setResultObj(counts==1?true:false);
		return result;
	}
}

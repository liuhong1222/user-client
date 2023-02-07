package cn.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.service.CreUserService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private CreUserService creUserService;
	
	@RequestMapping(value = "/getApiAccountInfoByUserName", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<ApiAccountInfoDomain> getApiAccountInfoByUserName(HttpServletRequest request, HttpServletResponse response,String userName) {
		return creUserService.getApiAccountInfoByUserName(userName);
	}
	
	@RequestMapping(value = "/findByUserName", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<CreUserDomain> findByUserName(HttpServletRequest request, HttpServletResponse response,String userName) {
		return creUserService.findByUserName(userName);
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<CreUserDomain> findById(HttpServletRequest request, HttpServletResponse response,String id) {
		return creUserService.findById(id);
	}
	
	@RequestMapping(value = "/findCreUserInfobyUserName", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Map<String,Object>> findCreUserInfobyUserName(HttpServletRequest request, HttpServletResponse response,String userName) {
		return creUserService.findCreUserInfobyUserName(userName);
	}
	
	@RequestMapping(value = "/bindCreUserMail", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<String> bindCreUserMail(HttpServletRequest request, HttpServletResponse response,String userId,String email) {
		return creUserService.bindCreUserMail(userId,email);
	}
	
	@RequestMapping(value = "/getResultPwd")
	public BackResult<String> getResultPwd(String creUserId) {
		return creUserService.getResultPwd(creUserId);
	}
	
	@RequestMapping(value = "/consumeAccount")
	public BackResult<Boolean> consumeAccount(String creUserId,int count) {
		return creUserService.consumeAccount(creUserId,count);
	}
}

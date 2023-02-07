package cn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.service.ApiAccountInfoService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

@RestController
@RequestMapping("/apiAccountInfo")
public class ApiAccountInfoController {
	private final static Logger logger = LoggerFactory.getLogger(ApiAccountInfoController.class);
	
	@Autowired
	private ApiAccountInfoService apiAccountInfoService;
	
	@RequestMapping("/updateApiAccountInfo")
	public BackResult<String> updateApiAccountInfo(@RequestBody ApiAccountInfoDomain domain) {
		return apiAccountInfoService.updateApiAccountInfo(domain);
	}
	
	@RequestMapping("/findCreUserBymail")
	public BackResult<CreUserDomain> findCreUserBymail(String email) {
		return apiAccountInfoService.findCreUserBymail(email);
	}
	
	@RequestMapping("/findByUserName")
	public BackResult<ApiAccountInfoDomain> findByUserName(String userName) {
		return apiAccountInfoService.findByUserName(userName);
	}
}

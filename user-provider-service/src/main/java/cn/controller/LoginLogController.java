package cn.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.LoginLogService;
import main.java.cn.common.BackResult;

@RestController
@RequestMapping("/loginLog")
public class LoginLogController {
	
	@Autowired
	private LoginLogService loginLogService;
	
	@RequestMapping(value = "/saveLoginLog", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<String> saveLoginLog(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String,String> param) {
		return loginLogService.saveLoginLog(param);
	}
}

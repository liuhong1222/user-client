package cn.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.mail.util.MailSSLSocketFactory;

import cn.dao.ApiAccountInfoMapper;
import cn.dao.CreUserMapper;
import cn.dao.MailLogMapper;
import cn.entity.MailLog;
import cn.service.EmailService;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.domain.CreUserDomain;

@Service
public class EmailServiceImpl implements EmailService {

	private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private MailLogMapper mailLogMapper;
	
	@Autowired
	private CreUserMapper creUserMapper;
	
	@Autowired
	private ApiAccountInfoMapper apiAccountInfoMapper;
	
	@Value("${toEmail}")
    private String toEmail;
	
	@Value("${toEmailPassword}")
    private String toEmailPassword;

	@Override
	public BackResult<String> sendMail(String userName, String email, String mailCode,String userId) {
		BackResult<String> result = new BackResult<String>();
		String tempResult = "";
		if(StringUtils.isBlank(userId)) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userName", userName);
			param.put("email", email);
			Map<String,Object> userInfo = creUserMapper.findUserNameByMail(param);
			if(userInfo == null) {
				result.setResultCode(ResultCode.RESULT_SENDMAIL_FAILED);
				result.setResultMsg("邮箱输入错误或还未绑定邮箱，请重新输入");
				return result;
			}
			userId = userInfo.get("userId").toString();
			tempResult = userInfo.get("userName").toString();
		}else {
			CreUserDomain creUserDomain = creUserMapper.findById(userId);
			if(creUserDomain == null) {
				result.setResultCode(ResultCode.RESULT_SENDMAIL_FAILED);
				result.setResultMsg("操作失败，帐号不存在");
				return result;
			}
			if(StringUtils.isNotBlank(creUserDomain.getEmail()) && !email.equals(creUserDomain.getEmail())) {
				result.setResultCode(ResultCode.RESULT_SENDMAIL_FAILED);
				result.setResultMsg("邮箱输入错误或还未绑定邮箱，请重新输入");
				return result;
			}
			
			ApiAccountInfoDomain apiAccountInfoDomain = apiAccountInfoMapper.findByUserId(userId);
			if(apiAccountInfoDomain == null) {
				result.setResultCode(ResultCode.RESULT_SENDMAIL_FAILED);
				result.setResultMsg("操作失败，帐号不存在");
				return result;
			}
			tempResult = apiAccountInfoDomain.getUserName();
		}
		
		Boolean sendMailResult = sendMailToRecive(userName,email,mailCode);
		if(!sendMailResult){
			result.setResultCode(ResultCode.RESULT_SENDMAIL_FAILED);
			result.setResultMsg("邮件发送失败，请重试");
			return result;
		}
		
		MailLog mailLog = new MailLog();
		mailLog.setUserId(Integer.parseInt(userId));
		mailLog.setRecipients(email);
		mailLog.setTitle("号码检测帐号 - 邮箱安全验证");
		mailLog.setMessage(getEmailContent(mailCode));
		mailLogMapper.saveMailLog(mailLog);
		result.setResultObj(tempResult);
		return result;
	}

	
	private Boolean sendMailToRecive(String userName,String email, String mailCode){
		Boolean result = Boolean.FALSE;
		Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "SMTP");
        //服务器
        prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        //端口
        prop.setProperty("mail.smtp.port", "465");
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
            return result;
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //
        //获取Session对象
        Session s = Session.getDefaultInstance(prop,new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication(toEmail, toEmailPassword);
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
        s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress(toEmail,toEmail));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //设置主题
            mimeMessage.setSubject("号码检测帐号 - 邮箱安全验证");
            mimeMessage.setSentDate(new Date());
            mimeMessage.setContent(getEmailContent(mailCode), "text/html;charset=UTF-8");
            //设置内容
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
            result = true;
            logger.info("用户【" + userName + "】发送验证邮件成功，验证码为：" + mailCode);
        } catch (MessagingException e) {
        	logger.info("用户【" + userName + "】发送验证邮件异常，验证码为：" + mailCode + "，异常信息为：" + e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	logger.info("用户【" + userName + "】发送验证邮件异常，验证码为：" + mailCode + "，异常信息为：" + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String getEmailContent(String mailCode){
		String result = "亲爱的用户：<br>您好！感谢您使用号码在线检测服务，您正在使用邮箱验证，本次请求的验证码为： <br>" + mailCode + "(为了保障您帐号的安全性，请在1小时内完成验证。)<br><br><br><br><br>号码检测团队<br>" + DateUtils.getToday();
		return result;		
	}
}

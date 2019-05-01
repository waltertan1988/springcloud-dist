package org.walter.base.webapp.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.walter.base.admin.controller.BaseController;
import org.walter.base.security.authenticate.filter.SmsValidationCodeFilter;

@Controller
@RequestMapping("/sms")
public class SmsLoginController extends BaseController {

	@GetMapping("/getValidationCode/{mobile}")
	@ResponseBody
	public String getCaptcha(HttpServletRequest request, @PathVariable("mobile") String mobile) {
		final int MIN = 0;
		final int MAX = 999999;

		String validationCode = String.format("%06d", new Random().nextInt((MAX - MIN) + 1) + MIN);
		
		// 将验证码保存到Session中
		HttpSession session = request.getSession();
		session.setAttribute(SmsValidationCodeFilter.SESSION_KEY_SMS_VALIDATION_CODE, validationCode);

		System.out.println("生成短信验证码为：" + validationCode);
		return validationCode;
	}
}

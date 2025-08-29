package kr.co.iei.etc.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.util.SendEmail;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
	@Autowired
	private SendEmail sendEmail;
	
	@ResponseBody
	@GetMapping(value="/emailCode")
	public String emailCode(String receiver) {
		String emailTitle = "인증번호";
		
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<6; i++) {
			int flag = r.nextInt(3); //0 = 숫자 / 1 = 대문자 / 2 = 소문자
			if(flag == 0) {
				int randomCode = r.nextInt(10);
				sb.append(randomCode);
			}else if(flag == 1) {
				char randomCode = (char)(r.nextInt(26) + 65);
				sb.append(randomCode);
			}else if(flag == 2) {
				char randomCode = (char)(r.nextInt(26) + 97);
				sb.append(randomCode);
			}
		}
		String emailContent = "<h1>인증코드</h1>";
		emailContent += "<h3>인증번호는 [";
		emailContent += "<span style='color:red;'>";
		emailContent += sb.toString();
		emailContent += "</span>";
		emailContent += "]입니다.</h3>";
		sendEmail.sendMail(emailTitle, receiver, emailContent);
		
		return sb.toString();
	}
}



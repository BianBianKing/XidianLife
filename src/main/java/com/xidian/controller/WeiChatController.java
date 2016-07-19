package com.xidian.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xidian.weichat.SHA1;

@Controller
public class WeiChatController {
	@RequestMapping(value = "WeiChat", method = { RequestMethod.POST,RequestMethod.GET })
	public void testWeiChat(HttpServletRequest request,HttpServletResponse response) throws IOException {
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if (isGet) {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");

			String TOKEN = new String("xd_mushroom");

			String[] str = { TOKEN, timestamp, nonce };
			Arrays.sort(str); // 字典序排序
			String bigStr = str[0] + str[1] + str[2];
			// SHA1加密
			String digest = new SHA1().getDigestOfString(bigStr.getBytes())
					.toLowerCase();

			// 确认请求来至微信
			if (digest.equals(signature)) {
				response.getWriter().print(echostr);
			}
		} else {
			// 进入POST聊天处理  
			try {  
                // 接收消息并返回消息  
                acceptMessage(request, response);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
		}
	}
	
	private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
		response.setCharacterEncoding("UTF-8");
		String str = new String("你好！欢迎光临西电小蘑菇！");
		response.getWriter().write(str);  
    }  
}

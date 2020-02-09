package com.action.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.action.WeixinConfig;
import com.pojo.Question;
import com.pojo.Yuyue;
import com.service.weixin.WeixinUserYuyueTopayService;
import com.util.URLManager;

import weixin.popular.api.MessageAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.Token;
import weixin.popular.bean.pay.PayNotify;
import weixin.popular.bean.templatemessage.TemplateMessage;
import weixin.popular.bean.templatemessage.TemplateMessageItem;
import weixin.popular.util.JSSDKUtil;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

/**
 * 常见问题
 * @author dell
 *
 */
@Controller
@RequestMapping("/question.do")
public class WeixinQuestionAction {
	
	private static List<Question> list = new ArrayList<Question>();
	
//  支付
	@RequestMapping(params="p=all")
	public String queryAll(HttpServletRequest request,HttpServletResponse response){
		if(list.size()==0) {
			Question q=new Question();
			q.setId(1);
			q.setTitle("发货之后怎么查看我的物流信息?快递一直没收到怎么办?");
			q.setContent("222222");
			list.add(q);
		}
		
		request.setAttribute("questions",list);
		return "/weixin/questions.jsp";
	}
}

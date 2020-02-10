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
	
	
	@RequestMapping(params="p=all")
	public String queryAll(HttpServletRequest request,HttpServletResponse response){
		if(list.size()==0) {
			Question q1=new Question();
			q1.setId(1);
			q1.setTitle("申请之后多久发货?");
			q1.setContent("工作日申请后第二天发货,周末及节假日申请后在下个工作日发出。");
			
			
			
			Question q2=new Question();
			q2.setId(2);
			q2.setTitle("发货之后怎么查看我的物流信息?快递一直没收到怎么办?");
			q2.setContent("发货之后我们会发送一条包含快递单号的短信,您可以使用搜索引擎搜索查看物流信息，物流信息由我司委托第三方物流公司寄送,如出现物流问题可致电物流客服询问,如没有回应或者没有发货则可以致电我司客服咨询：17009603333。");
			
			
			Question q3=new Question();
			q3.setId(3);
			q3.setTitle("115挪车码收到后怎么用?");
			q3.setContent("快递件里面有一张挪车卡片介绍使用和一张挪车贴,收货后使用微信扫码绑定编辑车主联络手机号和车牌号,绑定后将二维码贴置在挡风玻右下角处。");
			
			Question q4=new Question();
			q4.setId(4);
			q4.setTitle("115挪车贴粘贴后可以移动吗?会不会留下胶水痕迹?");
			q4.setContent("挪车贴采用新型不干胶材质在粘贴挡风玻后可揭下重复粘贴使用,不会残留胶水印，防水防晒。");
			
			Question q5=new Question();
			q5.setId(5);
			q5.setTitle("绑定信息可以修改吗?如果不想用了可不可以解绑?");
			q5.setContent("绑定信息可由开启该挪车码的微信账号扫码修改且不限次数,若挪车码物料发生破损或其他情况导致无法使用,可以致电客服要求我司工作人员解绑或者重新申请一张挪车码。");
			
			Question q6=new Question();
			q6.setId(6);
			q6.setTitle("使用中会不会产生额外费用?");
			q6.setContent("虚拟号码仅是转接功能,保护隐私，隐藏号码，我司不收取通话费用，运营商会收取你的通话费用。");
			
			Question q7=new Question();
			q7.setId(7);
			q7.setTitle("虚拟号码非本地号码和非本机号码?");
			q7.setContent("虚拟号码是由我司提供的全国虚拟号码不分地区，拨打的号码为虚拟号码,是一个通话中间号码,保障了通话双方隐私和信息安全。");
			
			Question q8=new Question();
			q8.setId(8);
			q8.setTitle("对方不接电话怎么办？对方接了半天不来挪车怎么办？");
			q8.setContent("... ... （弱小无助又可怜 ）只能求助警察叔叔帮忙了。");
			
			
			Question q9=new Question();
			q9.setId(9);
			q9.setTitle("如何保障车主的隐私不泄露?");
			q9.setContent("每当有人扫码拨打车主电话的时候,我们是通过一个中间号码来为拨号的双方建立联系。对通话双方而看到的都是中间号码,无法获得对方的真实号码。保护双方的隐私。同时,这样一来,即使车主的车停在公共场合,别有用心的人也无法通过扫码的方式,获取车主的电话号码,保护车主的隐私。");
			
			Question q10=new Question();
			q10.setId(10);
			q10.setTitle("115挪车码合作渠道?");
			q10.setContent("如有意愿合作请致电17009603333");
			
			Question q11=new Question();
			q11.setId(11);
			q11.setTitle("115挪车码客服电话是多少?");
			q11.setContent("官方客服电话:0551-67892532,17009603333");
			
			list.add(q1);
			list.add(q2);
			list.add(q3);
			list.add(q4);
			list.add(q5);
			list.add(q6);
			list.add(q7);
			list.add(q8);
			list.add(q9);
			list.add(q10);
			list.add(q11);
		}
		
		request.setAttribute("questions",list);
		return "/weixin/questions.jsp";
	}
	
	@RequestMapping(params="p=detail")
	public String queryDetail(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		Question question = list.get(Integer.valueOf(id)-1);
		request.setAttribute("question",question);
		return "/weixin/questions_detail.jsp";
	}
}

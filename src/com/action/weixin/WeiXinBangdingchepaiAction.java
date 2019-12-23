package com.action.weixin;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.action.JccsAction;
import com.action.WeixinConfig;
import com.pojo.User;
import com.service.weixin.WeiXinApplyForQrcodeService;
import com.service.weixin.WeiXinBangdingService;
import com.service.weixin.WeiXinMoveCarService;
import com.util.URLManager;

import weixin.popular.api.OpenIdAPI;
import weixin.popular.bean.AccessToken;
import weixin.popular.util.JSSDKUtil;
@Controller
@RequestMapping("/weixinbangding.do")	
public class WeiXinBangdingchepaiAction {
	@Autowired
	private HttpServletRequest request; 
	@Autowired
	private WeiXinBangdingService weixinbangdingservice;  
	@Autowired 
	private WeiXinApplyForQrcodeService applyForQrcodeService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(params = "p=bangding")
	public String moveCar(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String qrid =(String)request.getSession().getAttribute("qrid");//request.getParameter("qrid");
		String weixinhao =(String)request.getSession().getAttribute("uweixinhao");// request.getParameter("uweixinhao");
		if (qrid==null || weixinhao==null)
		{
			return "/weixin/bangdingshibai.jsp";
		}
		String chepaihao  = request.getParameter("chepaihao");
		String name  = request.getParameter("name");
		String tel  = request.getParameter("tel");
		User user = weixinbangdingservice.bangding(qrid,chepaihao, name, tel,weixinhao);
		//System.out.println("绑定成功");


		String url3_3 = URLManager.getServerURL(request)
				+ "/personCenter.do?method=index";
		String newUrl3_3 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ WeixinConfig.APPID
				+ "&redirect_uri="
				+ URLEncoder.encode(url3_3, "utf-8")
				+ "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
		return "redirect:"+newUrl3_3;

	}
	@RequestMapping(params = "p=bangding_buy")
	@ResponseBody
	public String bangding_buy(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String qrid =(String)request.getSession().getAttribute("qrid");//request.getParameter("qrid");
		String weixinhao =(String)request.getSession().getAttribute("uweixinhao");// request.getParameter("uweixinhao");
		request.getSession().setAttribute("openid", weixinhao);
		logger.info("先绑定后支付：qrid:"+qrid+"--weixinhao："+weixinhao);
		if (qrid==null || weixinhao==null)
		{
			logger.error("先绑定后支付：参数为空");
			return "/weixin/bangdingshibai.jsp";
		}
		String chepaihao  = request.getParameter("chepaihao");
		String name  = request.getParameter("name");
		String tel  = request.getParameter("tel");
		
		//  生成订单号
		String orderid=applyForQrcodeService.getAllDingdanhao();
		
		
		User user = weixinbangdingservice.bangding_buy(qrid,chepaihao, name, tel,weixinhao,orderid);
		logger.info("绑定信息存储成功");


		//购买的数量
		int account = 1;
		//  总金额
		double sum =Double.parseDouble(JccsAction.getQrcodeprice());
//		//  收件人姓名
//		String name = request.getParameter("name");
//		//  电话
//		String tel = request.getParameter("tel");
//		//  地址
		String address = "绑定车牌中";
	
		//  微信订单号
		//创建时间--生成订单时间

		//更新时间--用户付款时间
		//结束时间--用户收货时间
		//状态 0未发货，1未付款，2已付款，3已发货，4已收货，默认为0
		//运费 0yuan
		//卡费
		//总金额
		// 返回创建订单的时间戳
//		String weixinhao = (String) request.getSession().getAttribute("weixinhao");
		Timestamp createtime = applyForQrcodeService.doAddOrder(account,name,tel,address,orderid,sum,weixinhao);
		logger.info("绑定后支付：订单号："+orderid);
		////System.out.println("account:"+account);
		////System.out.println("name:"+name);
		////System.out.println("tel:"+tel);
		////System.out.println("address:"+address);
		////System.out.println("orderid:"+orderid);
		request.getSession().setAttribute("name", name);
		request.getSession().setAttribute("tel", tel);
		request.getSession().setAttribute("address", address);
		request.getSession().setAttribute("orderid", orderid);
		request.getSession().setAttribute("createtime", createtime);
		request.getSession().setAttribute("account", account);
		request.getSession().setAttribute("sum", sum);
		//return "/weixin/showOrder.jsp";
		return "yes";
	}


}

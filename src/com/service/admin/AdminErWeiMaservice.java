package com.service.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.hql.ast.HqlASTFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.HqlDAO;
import com.dao.ProxyCardDAO;
import com.dao.ProxyDAO;
import com.dao.UserDAO;
import com.pojo.Admin;
import com.pojo.Proxy;
import com.pojo.ProxyCard;
import com.pojo.ProxyCash;
import com.pojo.User;
import com.util.StringUtil;
import com.util.T2DMa;

import jxl.write.DateTime;

/**
 * pc端 超级管理员后台 二维码管理
 * 
 * @author 唐仁鑫
 */
@Service
public class AdminErWeiMaservice {

	@Autowired
	HqlDAO hqlDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	ProxyDAO proxyDAO;
	@Autowired
	ProxyCardDAO proxyCardDAO;

	public int findEndNum() {
		String hql = "select max(id) from User order by id desc";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public String[] getUUidNum(int num) {
		String[] code = new String[num];
		for (int i = 0; i < num; i++) {
			code[i] = UUID.randomUUID().toString().replaceAll("-", "");
		}
		return code;
	}

	public boolean savecode(String[] code, int num) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String time = dateFormat.format(date);
		for (int i = 0; i < num; i++) {

			String sql = "insert into user(qrcode,created) values('" + code[i]
					+ "','" + time + "');";
			// 还原377版本后取消注释
			hqlDAO.executeSql(sql);
		}
		return true;
	}

	public Map findActivatedQrcodeFenye(int size, int page, String keywords) {
		StringBuffer sb = new StringBuffer(" where 1=1");
		List paramList = new ArrayList();
		if (StringUtil.isNotNull(keywords) && keywords.trim().length() > 0) {
			sb.append(" and proxy.realname like ?");
			paramList.add("%" + keywords + "%");
			sb.append(" or name like ?");
			paramList.add("%" + keywords + "%");
		}
		String hqlsum = "select count(*) from User" + sb.toString()
				+ " and overdue = 2";
		int sum = (int) hqlDAO.unique(hqlsum, paramList.toArray());
		int count = sum % size == 0 ? sum / size : sum / size + 1;
		if (page < 1)
			page = 1;
		if (page > count)
			page = count;
		String hql = "from User" + sb.toString() + " and overdue = 2";
		List<ProxyCash> list = hqlDAO.pageQuery(hql, size, page, paramList
				.toArray());
		Map map = new HashMap();
		map.put("size", size);
		map.put("page", page);
		map.put("count", count);
		map.put("sum", sum);
		map.put("list", list);
		return map;
	}

	public Map findOutofdateQrcodeFenye(int size, int page, String keywords) {
		StringBuffer sb = new StringBuffer(" where 1=1");
		List paramList = new ArrayList();
		if (StringUtil.isNotNull(keywords) && keywords.trim().length() > 0) {
			sb.append(" and proxy.realname like ?");
			paramList.add("%" + keywords + "%");
			sb.append(" or name like ?");
			paramList.add("%" + keywords + "%");
		}
		String hqlsum = "select count(*) from User" + sb.toString()
				+ " and overdue = 3";
		int sum = (int) hqlDAO.unique(hqlsum, paramList.toArray());
		int count = sum % size == 0 ? sum / size : sum / size + 1;
		if (page < 1)
			page = 1;
		if (page > count)
			page = count;
		String hql = "from User" + sb.toString() + " and overdue = 3";
		List<ProxyCash> list = hqlDAO.pageQuery(hql, size, page, paramList
				.toArray());
		Map map = new HashMap();
		map.put("size", size);
		map.put("page", page);
		map.put("count", count);
		map.put("sum", sum);
		map.put("list", list);
		return map;
	}

	public Map findNoActivatedQrcodeFenye(int size, int page, String keywords) {
		StringBuffer sb = new StringBuffer(" where 1=1");
		List paramList = new ArrayList();
		if (StringUtil.isNotNull(keywords) && keywords.trim().length() > 0) {
			sb.append(" and proxy.realname like ?");
			paramList.add("%" + keywords + "%");
			sb.append(" or name like ?");
			paramList.add("%" + keywords + "%");
		}
		String hqlsum = "select count(*) from User" + sb.toString()
				+ " and overdue = 1";
		int sum = (int) hqlDAO.unique(hqlsum, paramList.toArray());
		int count = sum % size == 0 ? sum / size : sum / size + 1;
		if (page < 1)
			page = 1;
		if (page > count)
			page = count;
		String hql = "from User" + sb.toString() + " and overdue = 1";
		List<ProxyCash> list = hqlDAO.pageQuery(hql, size, page, paramList
				.toArray());
		Map map = new HashMap();
		map.put("size", size);
		map.put("page", page);
		map.put("count", count);
		map.put("sum", sum);
		map.put("list", list);
		return map;
	}

	public int findusableNum() {
		String hql = "select count(*) from User ";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public int findusableNum2() {
		String hql = "select count(*) from User where overdue in(0,1,2,3)";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public int findkeling() {
		String hql = "select count(*) from User where overdue = 0";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public List<User> getUsers(int beginid, int num) {
		int beginid1 = beginid - 1;
		int endid = beginid + num;
		System.out.println("beginid1:" + beginid1 + "endid:" + endid);
		String hql = " from User where " + " id > ? and id < ? ";
		List<User> users = hqlDAO.findByHQL(hql, beginid1, endid);
		return users;
	}

	public boolean doCreate(String encoderContent, List<User> users, String path) {
		T2DMa ma = new T2DMa();
		for (User user : users) {
			String imgPath = path + "/" + user.getId() + ".jpg";
			String encoderContent1 = encoderContent + user.getQrcode();
			ma.encoderQRCode(encoderContent1, imgPath, "jpg");
		}
		return true;
	}

	public boolean updatestatue(int beginid, int num) {

		int endid = beginid + num;
		beginid--;
		String hql = "update User set overdue = 0 where overdue =-1 and id > ? and id < ? ";
		hqlDAO.zsg(hql, beginid, endid);
		return true;

	}

	public List<Proxy> findProxy() {
		String hql = "from Proxy where level = 1";
		List<Proxy> list = hqlDAO.findByHQL(hql);
		return list;
	}

	public boolean doDailiTakeQrcode(int beginid, int proxyid, int endid,
			int adminid) {
		Date date = new Date();
		beginid--;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String time = dateFormat.format(date);
		String hql = "update User u set overdue = 4,u.proxy.id=" + proxyid
				+ " , u.admin.id=" + adminid + " , u.takeTime='" + time
				+ "' where id > " + beginid + " and id < " + endid;
		int n = hqlDAO.update(hql, null);
		if (n != 0)
			return true;
		return false;
	}

	public int findstartid() {
		String hql = "select min(id) from User where overdue in(0,1,2,3)";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public int findstartid2() {
		String hql = "select min(id) from User where overdue = 0";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public int findEndNumid() {
		String hql = "select max(id) from User where overdue in(0,1,2,3)";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public int findEndNumid2() {
		String hql = "select max(id) from User where overdue =0";
		int i = (int) hqlDAO.unique(hql);
		return i;
	}

	public void updateProxy(int proxyid, int num) {
		Proxy proxy = proxyDAO.findById(proxyid);
		int sumCard = 0;
		if (proxy.getSumCard() != null)
			sumCard = proxy.getSumCard();
		int noActive = 0;
		if (proxy.getNoActive() != null)
			noActive = proxy.getNoActive();
		proxy.setSumCard(sumCard + num);
		proxy.setNoActive(noActive + num);
		proxyDAO.save(proxy);
	}

	public void addProxyCard(int proxyid, Admin admin, int num, float price) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ProxyCard proxyCard = new ProxyCard();
		proxyCard.setAdmin(admin.getId());
		proxyCard.setNum(num);
		proxyCard.setCreated(timestamp);
		proxyCard.setProxyId(proxyid);
		proxyCard.setFee(num * price);
		proxyCardDAO.save(proxyCard);
	}

	/**
	 * 
	 * 
	 * @param  beginid  起始编号
	 * @param  num      数量
	 * @return 用户
	 */
	public List<Object[]> getUserBetween(int beginid, int num) {
		String hql = "select id,qrcode from User where id>=?";
		List list = hqlDAO.pageQuery(hql, num, 1, beginid);
		return list;
	}
}

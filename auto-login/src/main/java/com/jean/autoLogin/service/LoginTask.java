package com.jean.autoLogin.service;

import java.util.HashMap;

import com.jean.autoLogin.entity.User;
import com.jean.autoLogin.utils.HttpUtil;
import com.jean.autoLogin.utils.StringUtil;

import javafx.concurrent.Task;

public class LoginTask extends Task<Boolean> {

	String url;
	Boolean rememberMe;
	User user;

	public LoginTask(String url, User user, Boolean rememberMe) {
		this.url = url;
		this.user = user;
		this.rememberMe = rememberMe;
	}

	@Override
	protected Boolean call() throws Exception {
		if (user == null || StringUtil.isBlack(user.getUsername()) || StringUtil.isBlack(user.getPassword())) {
			updateMessage("用户名或密码不能为空");
			return false;
		}

		HashMap<String, Object> formparams = new HashMap<String, Object>();
		formparams.put("username", user.getUsername());
		formparams.put("password", user.getPassword());
		formparams.put("pwd", user.getPassword());
		formparams.put("secret", "true");
		String httpEntity = HttpUtil.post(url, formparams);
		if (httpEntity == null || httpEntity.isEmpty()) {
			httpEntity = HttpUtil.get("http://www.baidu.com");
			if (httpEntity == null || httpEntity.isEmpty() || httpEntity.contains("上海商业会计学校上网认证系统 - 登录")) {
				updateMessage("登陆失败");
			} else {
				if (httpEntity.contains("百度")) {
					updateMessage("登陆成功");
					return true;
				}
			}
		} else {
			httpEntity = httpEntity.substring(httpEntity.indexOf("<script>"), httpEntity.indexOf("</script>"));
			httpEntity = httpEntity.substring(httpEntity.indexOf("var tips="));
			httpEntity = httpEntity.replace("var tips=", "");
			httpEntity = httpEntity.replace("<br>IP", "【IP");
			httpEntity = httpEntity.replace("<br>该", "】该");
			updateMessage(httpEntity);
		}
		return false;
	}

	@Override
	protected void scheduled() {
		updateMessage("正在登陆...");
	}

	@Override
	protected void failed() {
		updateMessage("登陆失败，请重试");
	}
}

package yes.config;

import yes.controller.BlogController;
import yes.interceptor.GlobalInterceptor;
import yes.model.Blog;
import zj.jfinalExt.PropertyConfig;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class MyJFinalConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载数据库配置文件
		PropertyConfig.me().loadPropertyFile("db.properties");
		// 设定为开发者模式
		me.setDevMode(PropertyConfig.me()
				.getPropertyToBoolean("devMode", false));
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/blog", BlogController.class);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		PropertyConfig config = PropertyConfig.me();
		String jdbcUrl = config.getProperty("jdbcUrl");
		String user = config.getProperty("user");
		String password = config.getProperty("password");
		C3p0Plugin c3p0Plugin = new C3p0Plugin(jdbcUrl, user, password);
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("blog", Blog.class); // 映射blog 表到 Blog模型
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new GlobalInterceptor());

	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
	}

}

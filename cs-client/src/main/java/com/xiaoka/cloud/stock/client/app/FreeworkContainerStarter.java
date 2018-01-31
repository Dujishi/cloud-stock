package com.xiaoka.cloud.stock.client.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoka.freework.container.app.ContainerMain;
import com.xiaoka.freework.container.spring.config.ConfigCenter;
import com.xiaoka.freework.container.spring.config.ConfigCenterEvent;
import com.xiaoka.freework.container.spring.config.ConfigCenterEvent.EventType;
import com.xiaoka.freework.help.event.EventBus;
import com.xiaoka.freework.help.event.SubscribeEvent;
import com.xiaoka.freework.utils.Utils;

import javafx.application.Platform;

public class FreeworkContainerStarter extends ContainerMain {
	private static Logger logger = LoggerFactory.getLogger(FreeworkContainerStarter.class);
	private CSMainWindow window;

	private FreeworkContainerStarter(CSMainWindow window) {
		this.window = window;
		// 注册事件
		Utils.get(EventBus.class).register(this);
	}

	// 监听ConfigCenterEvent事件：初始化当前TestCase的 appName，覆盖 container.conf 中的定义
	@SubscribeEvent(sync = true)
	public void onConfigLoaedEvent(ConfigCenterEvent event) {
		// 配置加载完成时
		if (event.getEventType() == EventType.LOADED) {
			String mainUrl = ConfigCenter.global().getString("app.main.url");
			logger.debug("加载首页：{}", mainUrl);
			window.loadUrl(mainUrl);
		}
	}

	@Override
	public String[] getLocations() {
		return new String[] { "classpath*:spring/*.xml" };
	}

	// @Override
	// public boolean isBeanSupport() {
	// return false;
	// }

	@Override
	public void start(String[] args) throws Exception {
		// 容器启动成功后执行
		logger.info("框架容器启动成功");
	}

	static void startContainer(CSMainWindow window) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					FreeworkContainerStarter starter = new FreeworkContainerStarter(window);
					ContainerMain.startup(starter, null, false);
				} catch (Exception e) {
					logger.error("框架容器启动失败", e);
				}
			}
		});
	}

}

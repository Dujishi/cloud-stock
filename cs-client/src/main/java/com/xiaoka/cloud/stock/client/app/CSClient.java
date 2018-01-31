
package com.xiaoka.cloud.stock.client.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * From here you start the application
 * 
 * @author winner
 */
public class CSClient extends Application {

	/**
	 * 启动程序
	 */
	@Override
	public void start(Stage primaryStage) {
		// 启动界面
		CSMainWindow window = new CSMainWindow(this);
		window.start(primaryStage);
		// 启动容器
		FreeworkContainerStarter.startContainer(window);
	}

	/**
	 * Main入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}

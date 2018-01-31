package com.xiaoka.cloud.stock.client.app;

import org.apache.commons.lang3.BooleanUtils;

import com.xiaoka.cloud.stock.client.app.util.UIViewUtils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CSMainWindow {
	 private Application application;
	private CSBrowser browser;

	public CSMainWindow(Application application) {
		 this.application = application;
	}

	public void start(Stage primaryStage) {
		// Root
		BorderPane root = new BorderPane();
		browser = new CSBrowser(null, getParameterDebug());
		root.setCenter(browser.getWebView());

		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		menuBar.setUseSystemMenuBar(true);
		root.setTop(menuBar);

		// File menu - new, save, exit
		Menu fileMenu = new Menu("仓数");
		MenuItem newMenuItem = new MenuItem("关于");
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());

		fileMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), exitMenuItem);
		menuBar.getMenus().add(fileMenu);

		// Scene
		Scene scene = new Scene(root, UIViewUtils.getVisualScreenWidth() / 1.2, UIViewUtils.getVisualScreenHeight() / 1.2);
		// Prepare the Stage
		primaryStage.setTitle("仓数中心");
		// primaryStage.getIcons().add(InfoTool.getImageFromResourcesFolder("logo.png"));
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(cl -> System.exit(0));
		primaryStage.show();
	}
	
	private boolean getParameterDebug() {
		return BooleanUtils.toBoolean(application.getParameters().getNamed().get("debug"));
	}


	public void loadUrl(String url) {
		browser.loadUrl(url);
	}

}

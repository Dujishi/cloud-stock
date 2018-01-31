/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: FuxiAdminWildcardPermissionController.java
 * Author:   lansi
 * Date:    2017/6/16 14:55
 * Description:
 */
package com.xiaoka.cloud.stock.admin.core.permission;

import com.google.common.base.Joiner;
import com.xiaoka.freework.container.action.AdminWildcardPermissionController;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.ups.client.UpsClient;
import com.xiaoka.ups.client.dto.NodeDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class CSAdminWildcardPermissionController extends AdminWildcardPermissionController {
	private static Pattern INDEX_PATTERN = Pattern.compile("/?(index)?");
	private static String DEFAULT_PATH = "/web";
	private static String DEFAULT_PAGE = "/main.html";

	@RequestMapping({"/**"})
	public ModelAndView pageForward(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = StringUtils
				.removeEnd(StringUtils.removeStart(request.getRequestURI(), request.getContextPath()), "/");
//		String param = StringUtils.removeStart(path,"?");
		logger.debug("进入通配默认页面跳转: {}", path);
		boolean isIndex = false;
		if(INDEX_PATTERN.matcher(path).matches() || StringUtils.equalsIgnoreCase(this.index, path)) {
			path = this.index;
			isIndex = true;
		}

		String finalLayout = this.selectLayout(path);
		path = Joiner.on("").join(path, this.extension, new Object[0]);
		String filePath = request.getServletContext().getRealPath(path.equals(DEFAULT_PAGE) ? path : DEFAULT_PATH + path);
		if(!StringUtils.isBlank(filePath) && (new File(filePath)).exists()) {
			ModelAndView mav = new ModelAndView();
			List<NodeDto> nodeDtoList = Utils.get(UpsClient.class).getBreadCrumb();
			if(CollectionUtils.isNotEmpty(nodeDtoList)){
				mav.addObject("breadcrumb", Jackson.mobile().writeValueAsString(nodeDtoList));
			}else{
				mav.addObject("breadcrumb", false);
			}
			mav.addObject("isIndex", Boolean.valueOf(isIndex));
			mav.addObject("menuPage", this.menu);
			mav.addObject("defaultPath", DEFAULT_PATH);
			mav.addObject("bodyPage", path);
			mav.addObject("welcomePage", this.welcome);
//			mav.addObject("useAngular", useAngularLayout(path));
			mav.addObject("user", CSUserContext.getUser());
			mav.setViewName(Joiner.on("").join("layouts/", finalLayout, new Object[0]));
			return mav;
		} else {
			response.sendError(404, String.format("访问的页面 [%s] 不存在", new Object[]{path}));
			return null;
		}
	}

	private String selectLayout(String path) {
		String layoutFromMap = this.layoutMapper.get(path);
		return layoutFromMap != null?layoutFromMap:this.layout;
	}
	
//	private boolean useAngularLayout(String path) {
//		return StringUtils.startsWith(path, "/config/center/") || StringUtils.startsWith(path, "/core/permission/");
//	}
}
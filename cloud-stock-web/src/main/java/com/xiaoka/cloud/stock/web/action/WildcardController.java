package com.xiaoka.cloud.stock.web.action;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by yinhuadong on 2017/11/10.
 */
@RequestMapping({"/"})
public class WildcardController implements InitializingBean {
    protected static Logger logger = LoggerFactory.getLogger(WildcardController.class);

    public void setExtension(String extension) {
        this.extension = extension;
    }

    protected String extension = ".html";

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @RequestMapping({"/**"})
    public ModelAndView pageForward(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String path = StringUtils.removeEnd(StringUtils.removeStart(request.getRequestURI(), request.getContextPath()), "/");
        logger.debug("进入通配默认页面跳转: {}", path);
        path = Joiner.on("").join(path, this.extension, new Object[0]);
        String filePath = request.getServletContext().getRealPath(path);
        ModelAndView mav = new ModelAndView();
        if(!StringUtils.isBlank(filePath) && (new File(filePath)).exists()) {
            mav.addObject("bodyPage", path);
        } else {
            mav.addObject("bodyPage", null);
        }
        mav.setViewName(Joiner.on("").join("layouts/", "layout", new Object[0]));
        return mav;
    }
}

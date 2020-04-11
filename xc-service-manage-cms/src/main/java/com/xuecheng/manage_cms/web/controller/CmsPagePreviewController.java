package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.config.cms.CmsPageHtmlControllerApi;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.ICmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;

@Controller
public class CmsPagePreviewController extends BaseController implements CmsPageHtmlControllerApi {

    @Autowired
    private ICmsPageService pageService;


    @Override
    @GetMapping("/cms/preview/{pageId}")
    public void preview(@PathVariable(name="pageId")String pageId) {
        try {
            String pageHtml = pageService.getPageHtml(pageId);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(pageHtml.getBytes("utf-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

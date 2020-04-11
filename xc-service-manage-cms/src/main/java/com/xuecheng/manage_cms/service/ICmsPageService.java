package com.xuecheng.manage_cms.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.exception.CustomException;
import freemarker.template.TemplateException;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ICmsPageService {

    Page<CmsPage> findPageLimit(Integer page, Integer size, CmsPage cmsPage);

    CmsPage addCmsPage(CmsPage cmsPage) throws CustomException;

    CmsPage editCmsPage(CmsPage cmsPage);

    CmsPage findCmsPageById(String id);

    Boolean removeCmsPage(String id);

    CmsConfig queryCmsConfigById(String configId);

    String getPageHtml(String pageId) throws CustomException, IOException, TemplateException;
}

package com.xuecheng.manage_cms.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.exception.ExceptionCatch;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.ICmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.StringUtil;
import net.bytebuddy.asm.Advice;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.beans.Beans;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CmsPageServiceImpl implements ICmsPageService {


    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Override
    public Page<CmsPage> findPageLimit(Integer page, Integer size, CmsPage cmsPage) {
        if(cmsPage!=null){
            cmsPage.setSiteId(cmsPage.getSiteId()==null?null:cmsPage.getSiteId().equals("")?null:cmsPage.getSiteId());
            cmsPage.setPageId(cmsPage.getPageId()==null?null:cmsPage.getPageId().equals("")?null:cmsPage.getPageId());
            cmsPage.setPageName(cmsPage.getPageName()==null?null:cmsPage.getPageName().equals("")?null:cmsPage.getPageName());
            cmsPage.setPageAliase(cmsPage.getPageAliase()==null?null:cmsPage.getPageAliase().equals("")?null:cmsPage.getPageAliase());
            cmsPage.setTemplateId(cmsPage.getTemplateId()==null?null:cmsPage.getTemplateId().equals("")?null:cmsPage.getTemplateId());
        }
        PageRequest  pageable = PageRequest.of(page, size);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        Page<CmsPage> all = cmsPageRepository.findAll(Example.of(cmsPage,matcher),pageable);
        return all;
    }

    @Override
    public CmsPage addCmsPage(CmsPage cmsPage) throws CustomException {
        CmsPage param = new CmsPage();
        param.setPageWebPath(cmsPage.getPageWebPath());
        param.setSiteId(cmsPage.getSiteId());
        param.setPageName(cmsPage.getPageName());
        boolean exists = cmsPageRepository.exists(Example.of(param));
        if(!exists){//校验页面是否存在
            try{
                cmsPage.setPageId(null);
                CmsPage cms = cmsPageRepository.save(cmsPage);
                return cms;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            throw new CustomException(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        return null;
    }

    @Override
    public CmsPage editCmsPage(CmsPage cmsPage) {
        Optional<CmsPage> optional = cmsPageRepository.findById(cmsPage.getPageId());
        if(optional.isPresent()){
            CmsPage target = optional.get();
            BeanUtils.copyProperties(cmsPage,target);
            return cmsPageRepository.save(target);
        }
        return null;
    }

    @Override
    public CmsPage findCmsPageById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        return optional.isPresent()?optional.get():null;
    }

    @Override
    public Boolean removeCmsPage(String id) {
        try {
            cmsPageRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CmsConfig queryCmsConfigById(String configId) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(configId);
        return optional.isPresent()?optional.get():null;
    }

    @Override
    public String getPageHtml(String pageId) throws CustomException, IOException, TemplateException {
        Map data = getDataModel(pageId);
        if(data==null){
            throw  new CustomException(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        String template = getTemplateById(pageId);
        if(StringUtils.isEmpty(template)){
            throw  new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String html = generateHtml(template, data);
        if(StringUtils.isEmpty(html)){
            throw  new CustomException(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        return html;
    }

    /*
        执行静态化
     */
    private String generateHtml(String template,Map model) throws IOException, TemplateException {

        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("template",template);
        configuration.setTemplateLoader(templateLoader);
        Template template1 = configuration.getTemplate("template");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
        return html;
    }


    private String getTemplateById(String pageId) throws CustomException, IOException {
        CmsPage cmsPage = findCmsPageById(pageId);
        if(cmsPage==null||cmsPage.getTemplateId()==null){
            throw  new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String templateId = cmsPage.getTemplateId();
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if(optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            String  templateFileId = cmsTemplate.getTemplateFileId();
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            GridFSDownloadStream fileStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            String content = IOUtils.toString(fileStream,"utf-8");
            return  content;
        }
        return null;
    }

    /*
        获取数据模型
     */
    private Map getDataModel(String pageId) throws CustomException {
        CmsPage cmsPage = findCmsPageById(pageId);
        if(cmsPage==null||cmsPage.getDataUrl()==null){
            throw  new CustomException(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        ResponseEntity<Map> entity = restTemplate.getForEntity(cmsPage.getDataUrl(), Map.class);
        Map body = entity.getBody();
        return body;
    }
}

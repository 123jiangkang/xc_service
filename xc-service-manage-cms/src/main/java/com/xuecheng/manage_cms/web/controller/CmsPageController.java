package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.config.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.service.ICmsPageService;
import io.swagger.annotations.Api;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * @Author kang.jiang
 * @Created 2020年03月20  12:14:31
 * @Description <p>
 * @Modification <p>
 * Date Author Version Description
 * <p>
 * 2020年03月20  kang.jiang 1.0 create file
 */
@RestController
@RequestMapping("cms")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private ICmsPageService cmsPageService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        CmsPage cmsPage = new CmsPage();

        BeanUtils.copyProperties(queryPageRequest,cmsPage);
        Page<CmsPage> pageLimit = cmsPageService.findPageLimit(page, size,cmsPage);
        List<CmsPage> content = pageLimit.getContent();
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(pageLimit.getTotalElements());
        queryResult.setList(content);
        QueryResponseResult queryResponseResult = new
                QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult addCmsPage(@RequestBody CmsPage cmsPage) throws CustomException {
        CmsPage cmsPage1 = cmsPageService.addCmsPage(cmsPage);
        return  new CmsPageResult(cmsPage1==null?CommonCode.FAIL:CommonCode.SUCCESS,cmsPage1);
    }

    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult editCmsPage(@PathVariable(name="id") String id,@RequestBody CmsPage cmsPage) {
        cmsPage.setPageId(id);
        CmsPage cmsPage1 = cmsPageService.editCmsPage(cmsPage);
        return new CmsPageResult(cmsPage1==null?CommonCode.FAIL:CommonCode.SUCCESS,cmsPage1);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public CmsPageResult deleteCmsPage(@PathVariable(name="id") String id) {
        Boolean isRemove = cmsPageService.removeCmsPage(id);
        return new CmsPageResult(isRemove?CommonCode.SUCCESS:CommonCode.FAIL,null);
    }

    @Override
    @GetMapping("/congfig/getconfig/{configId}")
    public CmsConfig getConfig(@PathVariable(name="configId") String configId) {
        return cmsPageService.queryCmsConfigById(configId);
    }

    @GetMapping("/cmsById/{id}")
    public CmsPageResult getCmsById(@PathVariable(name = "id") String id){
        final CmsPage  cmsPage = cmsPageService.findCmsPageById(id);
        return  new CmsPageResult(cmsPage==null?CommonCode.FAIL:CommonCode.SUCCESS,cmsPage);
    }

}

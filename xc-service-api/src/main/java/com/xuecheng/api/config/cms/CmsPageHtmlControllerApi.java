package com.xuecheng.api.config.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Version 1.0
 * @Author kang.jiang
 * @Created 2020年03月20  12:15:46
 * @Description <p>
 * @Modification <p>
 * Date Author Version Description
 * <p>
 * 2020年03月20  kang.jiang 1.0 create file
 */
@Api(value="cms页面预览接口")
public interface CmsPageHtmlControllerApi {


    @ApiOperation("页面预览")
    public void preview(String pageId);

}

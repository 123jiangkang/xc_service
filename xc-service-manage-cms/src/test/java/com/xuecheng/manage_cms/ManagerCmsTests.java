package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import net.bytebuddy.asm.Advice;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Map;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerCmsTests {

      @Test
      public void cur(){

      }
//    @Autowired
//    private RestTemplate template;
//
//    @Test
//    public void test01(){
//        ResponseEntity<Map> entity = template.getForEntity("http://localhost:31001/cms/congfig/getconfig/5a791725dd573c3574ee333f", Map.class);
//        System.out.println(entity);
//    }
//
//    @Autowired
//    private GridFsTemplate gridFsTemplate;
//
//    @Autowired
//    private GridFSBucket gridFSBucket;
////    /*
////        分布式存储文件 gridfs
////     */
////    @Test
////    public void test02() throws FileNotFoundException {
////        FileInputStream input = new FileInputStream(new File("/Users/apple/IdeaProjects/xc_server-master/xc_server/xc-service-manage-cms/src/main/resources/templates/index_banner.ftl"));
////        ObjectId objectId = gridFsTemplate.store(input, "轮播图测试存储", "");
////        String id = objectId.toString();
////        System.out.println(id);
////    }
//
//    @Test
//    public void test03() throws IOException {
//        String id = "5e918d1d4531c605fa65c341";
//        final GridFSFile grid = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
//        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(grid.getObjectId());
//
//        final GridFsResource gridFsResource = new GridFsResource(grid,downloadStream);
//
//        String s = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
//
//        System.out.println(s);
//    }
//    @Test
//    public void test04(){
//        String id = "5e918d1d4531c605fa65c341";
//        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(id)));
//    }
}

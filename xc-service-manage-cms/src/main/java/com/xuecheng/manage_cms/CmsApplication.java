package com.xuecheng.manage_cms;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.xuecheng.api.config.Swagger2Configuration;
import com.xuecheng.framework.CommonApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * @Version 1.0
 * @Author kang.jiang
 * @Created 2020年03月20  11:59:27
 * @Description <p>
 * @Modification <p>
 * Date Author Version Description
 * <p>
 * 2020年03月20  kang.jiang 1.0 create file
 */
@SpringBootApplication
@Import({Swagger2Configuration.class})
@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = "com.xuecheng")
@ComponentScan(basePackages = "com.xuecheng.framework")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }


    @Bean
    public GridFSBucket getGridFSBucket(MongoClient client){
        MongoDatabase database = client.getDatabase("xc_page");
        GridFSBucket bucket = GridFSBuckets.create(database);
        return  bucket;
    }
}

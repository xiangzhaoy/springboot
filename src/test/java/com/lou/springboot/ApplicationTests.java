package com.lou.springboot;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link http://13blog.site
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    // 注入数据源对象
    @Autowired
    private DataSource dataSource;

    @Test
    public void datasourceTest() throws SQLException {
        // 获取数据源类型
        System.out.println("默认数据源为：" + dataSource.getClass());
        // 获取数据库连接对象
        Connection connection = dataSource.getConnection();
        // 判断连接对象是否为空
        System.out.println(connection != null);
        connection.close();
    }
    public void findPage() throws IOException, SolrServerException {
        /**
         * 集群版CloudSolrClient继承自SolrClient 进行强转
         * 单机版不需要直接使用solrClient
         */
        Object solrClient = null;
        CloudSolrClient cloudSolrClient =(CloudSolrClient)solrClient;
        //设置默认的操作实例
        cloudSolrClient.setDefaultCollection("collection1");
        //设置查找的参数
        SolrQuery query = new SolrQuery();

        query.setQuery("item_title:手机");
        query.setStart(1);
        query.setRows(20);
        //执行查找
        QueryResponse response = cloudSolrClient.query(query);
        SolrDocumentList documentList = response.getResults();

        for (SolrDocument entries : documentList) {
            String goodsName = (String) entries.getFieldValue("item_title");
            String id = (String)entries.getFieldValue("id");
            System.out.println(id+"-->"+goodsName);
        }
    }

}

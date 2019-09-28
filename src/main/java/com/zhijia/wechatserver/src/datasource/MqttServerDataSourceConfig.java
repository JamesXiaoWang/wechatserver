package com.zhijia.wechatserver.src.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 
 * @author Administrator
 * @description: MQTT管理服务数据源配置类
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.zhijia.wechatserver.src.mqttserver.mapper", sqlSessionTemplateRef  = "mqttServerSqlSessionTemplate")
public class MqttServerDataSourceConfig {

	private static Logger logger = LoggerFactory.getLogger(MqttServerDataSourceConfig.class); 
	
    @Bean(name = "mqttServerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mqttserver")
    public DataSource mqttServerDataSource() {
    	logger.info("MQTT Server's DataSource is loaded.");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mqttServerSqlSessionFactory")
    public SqlSessionFactory mqttServerSqlSessionFactory(@Qualifier("mqttServerDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/zhijia/wechatserver/src/mqttserver/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "mqttServerTransactionManager")
    public DataSourceTransactionManager mqttServerTransactionManager(@Qualifier("mqttServerDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mqttServerSqlSessionTemplate")
    public SqlSessionTemplate mqttServerSqlSessionTemplate(@Qualifier("mqttServerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

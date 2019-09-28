package com.zhijia.wechatserver.src.datasource;

import com.zhijia.wechatserver.src.deviceserver.controller.DeviceInteractionController;

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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author Administrators
 * @date 2018年10月19日 下午3:57:11
 * @description: 设备管理服务数据源配置类
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.zhijia.wechatserver.src.deviceserver.mapper.*", sqlSessionTemplateRef  = "deviceServerSqlSessionTemplate")
public class DeviceServerDataSourceConfig {
	
	private static Logger logger = LoggerFactory.getLogger(DeviceServerDataSourceConfig.class); 

	@Primary
    @Bean(name = "deviceServerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.deviceserver")
    public DataSource deviceServerDataSource() {
    	logger.info("Device Server's DataSource is loaded.");
        return DataSourceBuilder.create().build();
    }

	@Primary
    @Bean(name = "deviceServerSqlSessionFactory")
    public SqlSessionFactory deviceServerSqlSessionFactory(@Qualifier("deviceServerDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/zhijia/wechatserver/src/deviceserver/mapper/*/*.xml"));
        
        return bean.getObject();
    }

	@Primary
    @Bean(name = "deviceServerTransactionManager")
    public DataSourceTransactionManager deviceServerTransactionManager(@Qualifier("deviceServerDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

	@Primary
    @Bean(name = "deviceServerSqlSessionTemplate")
    public SqlSessionTemplate deviceServerSqlSessionTemplate(@Qualifier("deviceServerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

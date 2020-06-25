package com.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        return druidDataSource;
//    }
//
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource druidDataSource)throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(druidDataSource);
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:/mapper/*.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }

}

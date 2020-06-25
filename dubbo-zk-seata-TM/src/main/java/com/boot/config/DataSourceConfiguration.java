package com.boot.config;



import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        return druidDataSource;
//    }


//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource druidDataSource)throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(druidDataSource);
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:/mapper/*.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }

}

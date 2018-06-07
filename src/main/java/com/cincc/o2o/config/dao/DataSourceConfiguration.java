package com.cincc.o2o.config.dao;

import com.cincc.o2o.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * @author li
 * Date: 2018/06/03
 */

/**
 * 配置datasource到IOC容器里面
 */
@Configuration
@MapperScan("com.cincc.o2o.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    String jdbcDriver;
    @Value("${jdbc.url}")
    String jdbcUrl;
    @Value("${jdbc.password}")
    String jdbcPassword;
    @Value("${jdbc.username}")
    String jdbcUsername;

    /**
     * 生成与spring-dao.xml 对应的bean DataSource
     * @return
     * @throws PropertyVetoException
     */
    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        //生成DataSource实例
        ComboPooledDataSource dataSource=new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
        dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
        //关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
        // 配置c3p0连接池的私有属性
        // 连接池最大线程数
        dataSource.setMaxPoolSize(30);
        // 连接池最小线程数
        dataSource.setMinPoolSize(10);
        // 连接超时时间
        dataSource.setCheckoutTimeout(10000);
        // 连接失败重试次数
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }
}

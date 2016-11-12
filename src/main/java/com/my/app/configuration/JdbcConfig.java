package com.my.app.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

public class JdbcConfig {

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ds.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        ds.setJdbcUrl("jdbc:log4jdbc:mariadb://localhost:3306/test");
        ds.addDataSourceProperty("user", "root");
        ds.addDataSourceProperty("password", "admin123");
        ds.setAutoCommit(false);
        return ds;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath:mybatis/**/mappers/**/*.xml");
		
		Configuration configuration = new Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setMapperLocations(resources);
		sqlSessionFactoryBean.setConfiguration(configuration);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.my.web");
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
		return sqlSessionTemplate;
	}
	
}

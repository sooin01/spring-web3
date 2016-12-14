package com.my.app.configuration;

import java.util.Properties;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;

@EnableTransactionManagement(proxyTargetClass = true)
public class XaJdbcConfig {

	@Bean(initMethod = "init", destroyMethod = "shutdownForce")
	public UserTransactionService userTransactionService() {
		Properties properties = new Properties();
		properties.put("com.atomikos.icatch.service", "com.atomikos.icatch.standalone.UserTransactionServiceFactory");
		UserTransactionServiceImp userTransactionServiceImp = new UserTransactionServiceImp(properties);
		return userTransactionServiceImp;
	}
	
	@Bean(initMethod = "init", destroyMethod = "close")
	@DependsOn(value = "userTransactionService")
	public UserTransactionManager atomikosTransactionManager() {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setStartupTransactionService(false);
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}
	
	@Bean
	@DependsOn(value = "userTransactionService")
	public UserTransaction atomikosUserTransaction() throws Exception {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(300);
		return userTransactionImp;
	}
	
	@Bean(name = "transactionManager")
	@DependsOn(value = "userTransactionService")
	public JtaTransactionManager transactionManager() throws Exception {
		JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
		jtaTransactionManager.setTransactionManager(atomikosTransactionManager());
		jtaTransactionManager.setUserTransaction(atomikosUserTransaction());
		return jtaTransactionManager;
	}
	
	@Bean(initMethod = "init", destroyMethod = "close")
	@Lazy(value = true)
	public DataSource mariadbXaDataSource() throws Exception {
		Properties xaProperties = new Properties();
		xaProperties.put("user", "root");
		xaProperties.put("password", "admin123");
		xaProperties.put("url", "jdbc:mariadb://localhost:3306/test");
		
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setUniqueResourceName("test2");
		atomikosDataSourceBean.setXaDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
		atomikosDataSourceBean.setPoolSize(2);
		atomikosDataSourceBean.setXaProperties(xaProperties);
		return atomikosDataSourceBean;
	}
	
	@Bean(initMethod = "init", destroyMethod = "close")
	@Lazy(value = true)
	public DataSource oracleXaDataSource() throws Exception {
		Properties xaProperties = new Properties();
		xaProperties.put("user", "test");
		xaProperties.put("password", "test");
		xaProperties.put("url", "jdbc:oracle:thin:@192.168.0.10:1521:orcl");
		
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setUniqueResourceName("test1");
		atomikosDataSourceBean.setXaDataSourceClassName("oracle.jdbc.xa.OracleXADataSource");
		atomikosDataSourceBean.setPoolSize(2);
		atomikosDataSourceBean.setXaProperties(xaProperties);
		return atomikosDataSourceBean;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath:mybatis/mariadb/mappers/**/*.xml");
		
		Configuration configuration = new Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mariadbXaDataSource());
		sqlSessionFactoryBean.setMapperLocations(resources);
		sqlSessionFactoryBean.setConfiguration(configuration);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.my.app.web");
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
}

package bootstrap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"dao", "service","security"})
class base {

    // Jndi数据源,使用容器的连接池管理会比较好
    @Bean
    public DataSource dbSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource("jdbc/gmSearch");
    }

    // 实体管理工厂
    @Bean
    public LocalContainerEntityManagerFactoryBean dbFactory() {

        // 设置JPA配置参数
        Map<String, Object> properties = new Hashtable<>();
        properties.put("javax.persistence.schema-generation.database.action", "none");
        properties.put("javax.persistence.schema-generation.scripts.action", "none");
        properties.put("hibernate.show_sql", "true");

        // 创建JPA适配器
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");

        // 创建实例管理器工厂
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(this.dbSource());
        factory.setPackagesToScan("entity");
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        factory.setValidationMode(ValidationMode.NONE);
        factory.setJpaPropertyMap(properties);
        return factory;
    }

    // 事务管理器
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(this.dbFactory().getObject());
    }

}
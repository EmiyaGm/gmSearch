package bootstrap;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

@Order(1)
public final class main implements WebApplicationInitializer {

    private AnnotationConfigWebApplicationContext appContext;
    private AnnotationConfigWebApplicationContext mvcContext;

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        // Spring 全局配置注册
        appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(base.class);

        // Spring MVC 配置注册
        mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(mvc.class);

        // 添加 Spring 全局对象 处理器
        container.addListener(new ContextLoaderListener(appContext));

        // 定义 servlet 对象
        ServletRegistration.Dynamic servlet;

        // 添加 mvc 处理器
        servlet = container.addServlet("SpringFramework", new DispatcherServlet(mvcContext));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("*.do");
    }
}
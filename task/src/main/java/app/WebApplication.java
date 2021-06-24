package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class WebApplication implements WebApplicationInitializer {
    Logger logger = LoggerFactory.getLogger(WebApplication.class);
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.warn("deployed");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(EmailServiceConfiguration.class);
        context.refresh();
        context.setServletContext(servletContext);

        servletContext.addListener(new ContextLoaderListener(context));

        DispatcherServlet dv =
                new DispatcherServlet(new GenericWebApplicationContext());

        ServletRegistration.Dynamic appServlet = servletContext.addServlet("test-mvc", dv);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/test/*");
    }

}

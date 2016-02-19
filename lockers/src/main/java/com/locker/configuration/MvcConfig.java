package com.locker.configuration;

<<<<<<< HEAD
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
=======
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
>>>>>>> 135c7393a4e2da329e4f46c7a66a2a43e2aac6f3

/**
 * Created by randyr on 2/12/16.
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
<<<<<<< HEAD
@Import(ThymeleafConfiguration.class)
@ComponentScan(basePackages = {"com.locker"})
@PropertySource("classpath:application.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {

    /*@Override
=======
@ComponentScan(basePackages = { "com.locker"})
@PropertySource("classpath:application.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
>>>>>>> 135c7393a4e2da329e4f46c7a66a2a43e2aac6f3
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/login").setViewName("login");
    }

<<<<<<< HEAD
    /*@Bean
=======
    @Bean
>>>>>>> 135c7393a4e2da329e4f46c7a66a2a43e2aac6f3
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
<<<<<<< HEAD
    }*/

    // Maps resources path to webapp/WEB-INF/resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
    }

    // Provides internationalization of messages
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        return source;
    }

=======
    }
>>>>>>> 135c7393a4e2da329e4f46c7a66a2a43e2aac6f3
}

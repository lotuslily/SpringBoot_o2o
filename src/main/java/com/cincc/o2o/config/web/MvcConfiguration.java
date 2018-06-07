package com.cincc.o2o.config.web;

/**
 * @author li
 * Date: 2018/06/05
 */

import com.cincc.o2o.interceptor.shopadmin.ShopLoginInterceptor;
import com.cincc.o2o.interceptor.shopadmin.ShopPermissionInterceptor;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 开启MVC自动注入spring容器.  WebMvcConfigurerAdapter:配置视图解析器
 * 当一个类实现了ApplicationContextAware接口之后，这个类就可以方便获得ApplicationContext中所有的bean
 */
@Configuration
//等价于<mvc:annotation-driven />
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    //spring容器
    private ApplicationContext applicationContext;

    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.textproducer.font.color}")
    private String fontcolor;
    @Value("${kaptcha.image.width}")
    private String imagewidth;
    @Value("${kaptcha.textproducer.char.string}")
    private String textproducer;
    @Value("${kaptcha.image.height}")
    private String imageheight;
    @Value("${kaptcha.textproducer.font.size}")
    private String textproducersize;
    @Value("${kaptcha.noise.color}")
    private String noisecolor;
    @Value("${kaptcha.textproducer.char.length}")
    private String textproducerlength;
    @Value("${kaptcha.textproducer.font.names}")
    private String font;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext=applicationContext;
    }

    /**
     * 静态资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:\\projectdev\\image\\upload\\");
    }

    /**
     * 定义默认的请求处理器
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    /**
     * 创建viewResolver
     */
    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver(){
        InternalResourceViewResolver viewResolver =new InternalResourceViewResolver();
        //设置spring容器
        viewResolver.setApplicationContext(this.applicationContext);
        //取消缓存
        viewResolver.setCache(false);
        //设置解析的前缀
        viewResolver.setPrefix("/WEB-INF/html/");
        //设置解析的后缀
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    /**
     * 文件上传解析器
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver(){
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxUploadSize(20971520);
        return multipartResolver;
    }

    /**
     * 由于web.xml不生效了，需要在这里配置Kaptcha验证码Servlet
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(),"/Kaptcha");
        servlet.addInitParameter("kaptcha.border",border);
        servlet.addInitParameter("kaptcha.textproducer.font.color",fontcolor);
        servlet.addInitParameter("kaptcha.image.width",imagewidth);
        servlet.addInitParameter("kaptcha.textproducer.char.string",textproducer);
        servlet.addInitParameter("kaptcha.image.height",imageheight);
        servlet.addInitParameter("kaptcha.textproducer.font.size",textproducersize);
        servlet.addInitParameter("kaptcha.noise.color",noisecolor);
        servlet.addInitParameter("kaptcha.textproducer.char.length",textproducerlength);
        servlet.addInitParameter("kaptcha.textproducer.font.names",font);
        return servlet;
    }

    /**
     *添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        String interceptPath="/shopadmin/**";
        //注册拦截器
        InterceptorRegistration loginIR =registry.addInterceptor(new ShopLoginInterceptor());
        //配置拦截的路径
        loginIR.addPathPatterns(interceptPath);
        //还可以注册其他的拦截器
        InterceptorRegistration permisionIR = registry.addInterceptor(new ShopPermissionInterceptor());
        //配置拦截的路径
        permisionIR.addPathPatterns(interceptPath);
        //配置不拦截的路径
        permisionIR.excludePathPatterns("/shopadmin/shoplist");
        permisionIR.excludePathPatterns("/shopadmin/getshoplist");
        permisionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
        permisionIR.excludePathPatterns("/shopadmin/registershop");
        permisionIR.excludePathPatterns("/shopadmin/shopoperation");
        permisionIR.excludePathPatterns("/shopadmin/shopmanagement");
        permisionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
        
    }

}


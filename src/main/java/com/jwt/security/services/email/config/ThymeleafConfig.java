package com.jwt.security.services.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

//@Configuration
public class ThymeleafConfig {
    
   //@Bean
    //public ClassLoaderTemplateResolver templateResolver() {
     //   ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
      //  templateResolver.setPrefix("/templates/");
       // templateResolver.setSuffix(".html");
       // templateResolver.setTemplateMode(TemplateMode.HTML);
       // templateResolver.setCharacterEncoding("UTF-8");
       // return templateResolver;
    //}

    //@Bean
    //public SpringTemplateEngine templateEngine() {
      //  SpringTemplateEngine templateEngine = new SpringTemplateEngine();
      //  templateEngine.setTemplateResolver(templateResolver());
       // return templateEngine;
   // }

    //@Bean
    //public ViewResolver viewResolver() {
      //  ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
       // viewResolver.setTemplateEngine(templateEngine());
        //viewResolver.setCharacterEncoding("UTF-8");
        //return viewResolver;
    //}
    
}

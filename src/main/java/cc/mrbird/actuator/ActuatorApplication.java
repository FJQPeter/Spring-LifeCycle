package cc.mrbird.actuator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ActuatorApplication implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @GetMapping("del")
    public void remove(){
        removeBean((AnnotationConfigServletWebServerApplicationContext) applicationContext,"mySpringBean");
    }

    @GetMapping("add")
    public void add(){
        addBean((AnnotationConfigServletWebServerApplicationContext) applicationContext,"student",Student.class);
    }

    @GetMapping("query")
    public String query(){
        Student student = applicationContext.getBean("student", Student.class);
        return student.getName();
    }

    @Autowired private Student student;

    @GetMapping("aop")
    public void t1(){
        student.t1();
    }

    void removeBean(AnnotationConfigServletWebServerApplicationContext ctx, String beanName) {
        BeanDefinitionRegistry beanDefReg = (DefaultListableBeanFactory) ctx.getBeanFactory();
        beanDefReg.getBeanDefinition(beanName);
        beanDefReg.removeBeanDefinition(beanName);
    }

    void addBean(AnnotationConfigServletWebServerApplicationContext ctx, String beanName, Class beanClass) {
        BeanDefinitionRegistry beanDefReg = (DefaultListableBeanFactory) ctx.getBeanFactory();
        BeanDefinitionBuilder beanDefBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        beanDefBuilder.addPropertyValue("name","wenwen");
        BeanDefinition beanDef = beanDefBuilder.getBeanDefinition();
        if (!beanDefReg.containsBeanDefinition(beanName)) {
            beanDefReg.registerBeanDefinition(beanName, beanDef);
        }
    }

}

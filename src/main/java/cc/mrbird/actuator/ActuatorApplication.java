package cc.mrbird.actuator;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@SpringBootApplication
@Controller
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ActuatorApplication implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

    //必须指定具体绑定的参数
    @InitBinder("student")
    public void initBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("student.");
        System.out.println("init-binder在controller方法执行前执行");
    }

    @InitBinder("person")
    public void initBinder2(WebDataBinder binder){
        binder.setFieldDefaultPrefix("person.");
    }

    //接收请求参数中的sex
    @ModelAttribute("student")
    public void model(String sex, Model model){
        model.addAttribute("sex",sex);
        System.out.println("ModelAttribute获取参数sex "+sex);
        System.out.println("执行@ModelAttribute比InitBinder先执行");
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
    @ResponseBody
    //有时候我们需要获得一个不带数据绑定的模型属性 @ModelAttribute(binding = false)
    public String queryStudent(@ModelAttribute("student")Student student,Person person){
        return "学生姓名 "+student.getName()+" 学生年龄："+student.getAge()+" 老师姓名："+person.getName();
    }

    @Autowired private Student student;

    @GetMapping("aop")
    public void t1(){
        student.t1();
    }

    @GetMapping("redirect")
    public String re(RedirectAttributes redirectAttributes){
        Student student = new Student();
        student.setName("wenwen");
        student.setAge(25);
        redirectAttributes.addFlashAttribute("sex","女");
        redirectAttributes.addFlashAttribute(student);
        return "redirect:/query";
    }

    @Autowired private ServiceA serviceA;
    @GetMapping("hh")
    @ResponseBody
    public void a(){
        serviceA.t1();
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

    /**
     * 有时候Bean的定义可能使用的是第三方的类，此时可以使用注解＠Bean来配置自定义初始化和销毁方法
     *  @return
     */
    @Bean(initMethod = "getName",destroyMethod = "destroy")
    public Student student(){
        return new Student();
    }

    @Test
    public void t2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Student.class);
        Student student = context.getBean("student", Student.class);
        Student student2 = context.getBean("student", Student.class);
        System.out.println(student==student2);
    }

}

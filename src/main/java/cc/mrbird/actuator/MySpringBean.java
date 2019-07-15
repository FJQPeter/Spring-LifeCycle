package cc.mrbird.actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName: MySpringBean
 * @Description: my spring bean to test
 * @author: daniel.zhao
 * @date: 2018年10月26日 上午10:12:37
 */
@Component
public class MySpringBean implements BeanNameAware, BeanFactoryAware,
        InitializingBean, ApplicationContextAware, DisposableBean{

    private String name;

    public void setName(String name) {
        this.name = name;
        System.out.println("执行set方法");
    }

    private ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(MySpringBean.class);

    public MySpringBean() {
        logger.info("new MySpringBean......");   //1
    }

    @PostConstruct
    public void t1(){
        logger.info("PostConstruct......"); //6
        init();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        logger.info("ApplicationContextAware-setApplicationContext......"); //4
        this.applicationContext = context;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("InitializingBean-afterPropertiesSet......"); //7
    }

    @Override
    public void setBeanFactory(BeanFactory bf) throws BeansException {
        logger.info("BeanFactoryAware-setBeanFactory......"); //3
    }

    @Override
    public void setBeanName(String name) {
        logger.info("BeanNameAware-setBeanName......");  //2
    }

    @Transactional
    public void init() {
        logger.info("init-method......");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("pre-destroy-method........");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("destroy-method......");
    }
}

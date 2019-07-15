package cc.mrbird.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created by FangYan on 2019/7/15.
 */
@Slf4j
public class MySpringBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
            if(configurableListableBeanFactory.getBean(MySpringBean.class) instanceof MySpringBean){
               log.info("BeanFactoryPostProcessor.........");
            }
    }

}

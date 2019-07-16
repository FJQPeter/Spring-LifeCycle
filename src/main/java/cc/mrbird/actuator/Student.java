package cc.mrbird.actuator;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by FangYan on 2019/7/15.
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Student {
    private String name;

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void t1(){
        System.out.println("执行student的t1方法");
    }

    public void init(){
        System.out.println("student 初始化");
    }

    public void destroy(){
        System.out.println("student 销毁");
    }

    public String getName() {
        System.out.println("执行getName方法 "+name);
        return name;
    }

    @Value("${student.name}")
    public void setName(String name) {
        this.name = name;
        System.out.println("执行setName方法 "+name);
    }
}

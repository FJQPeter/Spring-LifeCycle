package cc.mrbird.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * Created by FangYan on 2019/7/16.
 */
@Service
public class ServiceA {
    @Autowired private ServiceB serviceB;

    /**
     * Spring不能处理Bean之间使⽤构造⽅法进⾏注⼊并形成循依赖的情况。
     */
    public ServiceA() {

    }

    public void t1(){

    };
}

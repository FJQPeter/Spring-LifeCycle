package cc.mrbird.actuator;
import	java.lang.Thread.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by FangYan on 2019/7/16.
 */
@Service
public class ServiceB {

    public ServiceB(@Autowired @Qualifier("serviceA") ServiceA serviceA) {

    }

    @Autowired private ServiceA serviceA;
}

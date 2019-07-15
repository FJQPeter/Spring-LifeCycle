package cc.mrbird.actuator;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by FangYan on 2019/7/15.
 */
@Data
@Component
public class Student {
    private String name;

    public void t1(){
        System.out.println("执行student的t1方法");
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("zhcs@123");
        System.out.println(encode);
    }
}

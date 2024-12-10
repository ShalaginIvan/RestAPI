package ru.shalagin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.shalagin.config.MyConfig;
import ru.shalagin.model.User;

import java.util.List;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);

        User user = new User(3L, "James", "Brown", 46 );

        List<User> listUsers = communication.getUsersList();
        System.out.println("All users: \n" + listUsers);

        String code1 = communication.create(user);
        System.out.println("Code 1: " + code1);

        user.setName("Thomas");
        user.setLastName("Shelby");
        String code2 =communication.update(user);
        System.out.println("Code 2: " + code2);

        String code3 =communication.delete(3L);
        System.out.println("Code 3: " + code3);

        System.out.println("Result code : " + code1 + code2 + code3);

    }
}

package hema.web.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {


        Subject proxy = (Subject) java.lang.reflect.Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, new Proxy());

        proxy.sayHello();
    }
}

interface Subject {
    String sayHello();
}

class Proxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        System.out.println("call proxy");

        return "success";
    }
}
package hema.web.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        Subject proxy = (Subject) java.lang.reflect.Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, new Proxy());

        proxy.sayHello();

        proxy.rules();
    }
}

interface Subject {
    Subject sayHello();

    Map<String, String[]> rules();
}

class Proxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

        System.out.println("call proxy");

        if (method.getName().equals("rules")) {
            return new HashMap<>();
        }

        return proxy;
    }
}
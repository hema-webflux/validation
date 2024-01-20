package hema.web.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        Map<String, Set<Integer>> maps = new HashMap<>();

        maps.put("a", new HashSet<>(List.of(1, 2)));
        maps.put("b", new HashSet<>(List.of(3, 4)));
        maps.put("c", new HashSet<>(List.of(5, 6)));

        System.out.println(maps.values().stream().flatMap(Set::stream).collect(Collectors.toSet()));

//        Subject proxy = (Subject) java.lang.reflect.Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, new Proxy());
//
//        proxy.sayHello();
//
//        proxy.rules();
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
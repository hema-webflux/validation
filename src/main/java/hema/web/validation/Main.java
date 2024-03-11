package hema.web.validation;

public class Main {

    public static void main(String[] args) {

        new Parent().test();

    }

    static class Parent {

        public void test(){

            System.out.println();

        }

    }

    static class Sub extends Parent {

    }

}
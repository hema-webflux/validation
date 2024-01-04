package io.github.validation;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

@RestController
public class Controller {

    @GetMapping("/")
    public void index(HttpServletRequest request) throws IOException {
        System.out.println(request.getQueryString());
        System.out.println(request.getRemoteUser());
        System.out.println(request.getRequestURI());
        System.out.println(request.getPathInfo());

        BufferedReader reader = request.getReader();

        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        System.out.println(new JSONObject(builder.toString()).toMap());

        Enumeration<String> headers = request.getHeaderNames();

        while (headers.hasMoreElements()) {
            System.out.print("header头:");
            System.out.print(headers.nextElement());
            System.out.println();
        }

        Enumeration<String> parameters = request.getParameterNames();

        while (parameters.hasMoreElements()) {
            System.out.print("参数:");
            System.out.print(parameters.nextElement());
            System.out.println();
        }

    }
}

package io.github.enums;

import io.github.enums.exception.EnumException;
import io.github.enums.exception.EnumInheritException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public void index() throws EnumException, EnumInheritException {

        for (Color color: Color.values()){
            System.out.println(color.toLocal());
        }
    }
}

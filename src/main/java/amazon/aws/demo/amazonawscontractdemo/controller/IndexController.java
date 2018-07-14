package amazon.aws.demo.amazonawscontractdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @author: jiasfeng
 * @Date: 7/14/2018
 */
@Controller
@Slf4j
public class IndexController {
    @RequestMapping("/")
    public String greeting() {
        return "index";
    }
}

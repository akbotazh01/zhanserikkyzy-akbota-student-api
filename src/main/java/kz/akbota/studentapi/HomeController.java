package kz.akbota.studentapi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("application", "Zhanserikkyzy Akbota Student API");
        info.put("student", "Akbota Zhanserikkyzy");
        info.put("studentId", "37106");
        info.put("group", "IT2-2308");
        return info;
    }
}

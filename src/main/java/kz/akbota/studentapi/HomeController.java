package kz.akbota.studentapi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        Package pkg = HomeController.class.getPackage();
        String version = pkg.getImplementationVersion();

        Map<String, String> info = new LinkedHashMap<>();
        info.put("application", "Zhanserikkyzy Akbota Student API");
        info.put("version", version != null ? version : "dev");
        info.put("student", "Akbota Zhanserikkyzy");
        info.put("studentId", "37106");
        info.put("group", "IT2-2308");
        return info;
    }
}

package za.net.ether.mokse;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointController {
    StatusThread statusThread = MokseApplication.st;

    @GetMapping("/")
    public String index() {
        File file = new File("src/main/java/za/net/ether/mokse/templates/doc.html");
        String doc = "";
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                doc = doc.concat(sc.nextLine());
                System.out.println(doc);
            }
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
        System.out.println(doc);
        return doc;

    }

    @GetMapping("/GetScheduleByCode")
    public Schedule schedule(@RequestParam(value = "code", defaultValue = "1") String code) throws IOException {
        return new Schedule(1, 4, 16, Integer.parseInt(code));
    }

    @GetMapping("/GetStatus")
    public Status status() {
        return StatusThread.status;
    }
}
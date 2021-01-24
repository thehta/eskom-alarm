package za.net.ether.mokse;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.key.MonthDayKeyDeserializer;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {
    StatusThread statusThread = MokseApplication.st;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

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

        // ObjectMapper mapper = new ObjectMapper();
        // SimpleModule module = new SimpleModule("CustomTimeStageSerializer", new
        // Version(1, 0, 0, null, null, null));
        // module.addSerializer(TimeStage.class, new CustomTimeStageSerializer());
        // mapper.registerModule(module);
        // MappingJackson2HttpMessageConverter converter = new
        // MappingJackson2HttpMessageConverter(mapper);

        // mapper.writeValue(out, schedule);

        // return mapper;
        // final byte[] data = out.toByteArray();
        // return new String(data);
    }

    @GetMapping("/GetStatus")
    public Status status() {
        Future<Status> future = executorService.submit(() -> {
            URL url = new URL("https://loadshedding.eskom.co.za/LoadShedding/GetStatus");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer content = new StringBuffer();
            String inputLine = in.readLine();
            in.close();
            connection.disconnect();
            int stage = Integer.parseInt(inputLine);

            StringBuilder fullResponseBuilder = new StringBuilder();
            String date = connection.getHeaderField("Date");
            return new Status(date, stage-1);
        });
        while (!future.isDone()) {

        }
        try {
            return future.get();
        } catch (Exception e) {
            //TODO: handle exception
        }
        return new Status("", 8);
    }
}
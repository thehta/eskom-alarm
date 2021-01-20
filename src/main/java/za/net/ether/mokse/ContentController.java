package za.net.ether.mokse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/content")
    public Content content(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Content(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/code")
    public ObjectMapper schedule(@RequestParam(value = "code", defaultValue = "12") String code) throws IOException {
        Schedule schedule = new Schedule(1, 4, 16, Integer.parseInt(code));

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("CustomTimeStageSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(TimeStage.class, new CustomTimeStageSerializer());
        mapper.registerModule(module);
        // Car car = new Car("yellow", "renault");
        // String carJson = mapper.writeValueAsString(car);

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper2 = new ObjectMapper();

        mapper.writeValue(out, schedule);

        return mapper;
        //final byte[] data = out.toByteArray();
        //return new String(data);
    }
}
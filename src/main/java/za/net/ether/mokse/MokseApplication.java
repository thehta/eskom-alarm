package za.net.ether.mokse;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MokseApplication implements ApplicationRunner {
	public static StatusThread st;

	public static void main(String[] args) {
		SpringApplication.run(MokseApplication.class, args);
		// Dotenv dotenv =
		// Dotenv.configure().directory("src/main/java/za/net/ether/mokse/static/").load();
		// Callable<Integer> callable = new StatusThread(dotenv.get("GETSTATUS_URL"));
		// Integer status = executor.submit(callable);

	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		System.out.println("Hello World from Application Runner");
		st = new StatusThread("https://loadshedding.eskom.co.za/LoadShedding/GetStatus");
		st.start();
	}
	// @Bean
	// public Jackson2ObjectMapperBuilderCustomizer customJackson() {
	// return new Jackson2ObjectMapperBuilderCustomizer() {
	// @Override
	// public void customize(Jackson2ObjectMapperBuilder builder) {
	// ObjectMapper mapper = new ObjectMapper();
	// SimpleModule module = new SimpleModule("CustomTimeStageSerializer",
	// new Version(1, 0, 0, null, null, null));
	// module.addSerializer(TimeStage.class, new CustomTimeStageSerializer());
	// mapper.registerModule(module);
	// builder.serializationInclusion(Include.NON_NULL);
	// builder.failOnUnknownProperties(false);
	// }
	// };
	// }

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("CustomTimeStageSerializer", new Version(1, 0, 0, null, null, null));
		module.addSerializer(TimeStage.class, new CustomTimeStageSerializer());
		// module.addSerializer(Schedule.class, new CustomScheduleSerializer());
		mapper.registerModule(module);
		return new ObjectMapper().registerModule(module);

		// JavaTimeModule module = new JavaTimeModule();
		// module.addSerializer(LOCAL_DATETIME_SERIALIZER);
		// return new
		// ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).registerModule(module);
	}

}

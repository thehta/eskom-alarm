package za.net.ether.mokse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MokseApplication implements ApplicationRunner {
	public static StatusThread st;

	public static void main(String[] args) {
		SpringApplication.run(MokseApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		Dotenv dotenv = Dotenv.configure().directory("src/main/java/za/net/ether/mokse/").load();
		st = new StatusThread(dotenv.get("GETSTATUS_URL"));
		st.start();
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("CustomTimeStageSerializer", new Version(1, 0, 0, null, null, null));
		module.addSerializer(TimeStage.class, new CustomTimeStageSerializer());
		mapper.registerModule(module);
		return new ObjectMapper().registerModule(module);
	}

}

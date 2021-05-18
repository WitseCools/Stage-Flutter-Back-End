package ed.ap.stage.Tijdsregistratie;


import ed.ap.stage.Tijdsregistratie.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
public class TijdsregistratieApplication {

	public static void main(String[] args) {
		SpringApplication.run(TijdsregistratieApplication.class, args);
	}

}

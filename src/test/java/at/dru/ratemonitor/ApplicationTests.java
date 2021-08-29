package at.dru.ratemonitor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

}

package at.dru.ratemonitor.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Disabled // TODO Fix dbUnit dependency
@SpringBootTest
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles({"test"})
public class ConversionRateTest {

    private static final Logger logger = LoggerFactory.getLogger(ConversionRateTest.class);

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Test
    // @DatabaseSetup("test-fourrates.xml")
    public void testFind() {
        Iterable<ConversionRate> rates = conversionRateRepository.findAll();
        int count = 0;
        for (ConversionRate rate : rates) {
            logger.debug("Rate: {}", rate.getId());
            count++;
        }
        assertEquals(4, count);
    }

}

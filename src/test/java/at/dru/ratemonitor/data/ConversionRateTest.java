package at.dru.ratemonitor.data;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration("dbunit-context.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class ConversionRateTest {

    private static final Logger logger = LoggerFactory.getLogger(ConversionRateTest.class);

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Test
    @DatabaseSetup("test-fourrates.xml")
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

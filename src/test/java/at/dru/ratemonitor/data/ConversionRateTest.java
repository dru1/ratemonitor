package at.dru.ratemonitor.data;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dbunit-context.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class ConversionRateTest {

    @Autowired
    private ConversionRateRepository conversionRateRepository;

    @Test
    @DatabaseSetup("test-fourrates.xml")
    public void testFind() throws Exception {
        Iterable<ConversionRate> rates = conversionRateRepository.findAll();
        int count = 0;
        for (ConversionRate rate : rates) {
            System.out.println(rate);
            count++;
        }
        assertEquals(4, count);
    }

}

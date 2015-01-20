package at.dru.ratemonitor;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Date;
import java.util.List;

public interface ConversionRateRepository extends PagingAndSortingRepository<ConversionRate, Long> {

    List<ConversionRate> findByChangedDate(String changedDate);

    List<ConversionRate> findByParsedDate(Date changedDate);

}

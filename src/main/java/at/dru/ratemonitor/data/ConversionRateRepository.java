package at.dru.ratemonitor.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ConversionRateRepository extends PagingAndSortingRepository<ConversionRate, Long> {

    List<ConversionRate> findByChangedDate(String changedDate);

    List<ConversionRate> findByParsedDate(Date changedDate);

}

package at.dru.ratemonitor.data;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ConversionRateRepository extends JpaRepository<ConversionRate, Long>, JpaSpecificationExecutor<ConversionRate> {

    List<ConversionRate> findByChangedDate(String changedDate);

    List<ConversionRate> findByParsedDate(Date changedDate, Pageable pageable);

    List<ConversionRate> findByCountry(String country, Pageable pageable);

    @Nullable
    ConversionRate findTopByOrderByParsedDateDesc();

}

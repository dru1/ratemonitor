package at.dru.ratemonitor.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.util.List;

@Repository
public interface ConversionRateRepository extends PagingAndSortingRepository<ConversionRate, Long>, JpaSpecificationExecutor<ConversionRate> {

    @Nonnull
    List<ConversionRate> findByChangedDate(String changedDate);

    @Nonnull
    List<ConversionRate> findByParsedDate(Date changedDate, Pageable pageable);

    @Nonnull
    List<ConversionRate> findByCountry(String country, Pageable pageable);

    @Nullable
    ConversionRate findTopByOrderByChangedDateDesc();
}

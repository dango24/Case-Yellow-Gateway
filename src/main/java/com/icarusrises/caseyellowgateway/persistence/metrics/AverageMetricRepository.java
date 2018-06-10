package com.icarusrises.caseyellowgateway.persistence.metrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

public interface AverageMetricRepository extends JpaRepository<AverageMetric, Long> {

    String UPDATE_COUNTER_QUERY = "UPDATE AverageMetric a set a.count = a.count+1 where a.id = :id";
    String UPDATE_AVERAGE_QUERY = "UPDATE AverageMetric a set a.avg = :avg where a.id = :id";

    @Modifying
    @Transactional
    @Query(UPDATE_COUNTER_QUERY)
    void updateCounter(@Param("id")long id);

    @Modifying
    @Transactional
    @Query(UPDATE_AVERAGE_QUERY)
    void updateAverage(@Param("id")long id, @Param("avg")double avg);

    AverageMetric findByBucket(String bucketName);

    default AverageMetric updateAverageMetric(String bucketName, double testAvgTime) {
        AverageMetric averageMetric = findByBucket(bucketName);

        if (isNull(averageMetric)) {
            averageMetric = new AverageMetric(bucketName, testAvgTime, 1);
            save(averageMetric);

            return averageMetric;
        }

        double currentAvg = averageMetric.getAvg();
        int currentCount = averageMetric.getCount();
        double newAvg = (currentAvg + testAvgTime) / (currentCount+1);

        updateAverage(averageMetric.getId(), newAvg);
        updateCounter(averageMetric.getId());

        return new AverageMetric(bucketName, newAvg);
    }
}

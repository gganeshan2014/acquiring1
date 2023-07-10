package com.cg.sp.repository;

import com.cg.sp.entity.AcquiringStandardEntity;
import com.cg.sp.model.converter.AcquiringStandardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AcquiringStandardRepository extends JpaRepository<AcquiringStandardEntity, Integer> {
    @Query(value = "SELECT SUM(a.net_volume) AS totalVolume, " +
            "SUM(a.net_value) AS totalTransactionValue " +
            "FROM acquiring_standard AS a " +
            "WHERE a.terminal_id = :terminalId " +
            "AND " +
            "a.trans_date BETWEEN :startDate AND :endDate AND a.channel = 'PoS' ;", nativeQuery = true)
    AcquiringStandardDTO fetchStandardDetails(@Param("terminalId") String terminalId,
                                              @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate);


}

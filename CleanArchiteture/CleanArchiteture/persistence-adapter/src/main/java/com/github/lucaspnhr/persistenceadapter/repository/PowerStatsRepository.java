package com.github.lucaspnhr.persistenceadapter.repository;

import com.github.lucaspnhr.persistenceadapter.entity.PowerStatsJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PowerStatsRepository extends JpaRepository<PowerStatsJPAEntity, UUID> {
}

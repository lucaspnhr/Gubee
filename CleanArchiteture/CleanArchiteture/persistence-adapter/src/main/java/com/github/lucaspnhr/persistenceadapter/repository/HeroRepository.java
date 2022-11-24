package com.github.lucaspnhr.persistenceadapter.repository;

import com.github.lucaspnhr.persistenceadapter.entity.HeroJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface HeroRepository extends JpaRepository<HeroJPAEntity, UUID> {
}

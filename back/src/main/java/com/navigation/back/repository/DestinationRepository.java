package com.navigation.back.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.navigation.back.entity.Destination;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("destinationRepository")
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<Destination> findByName(String name); // 이름으로 조회하는 메서드
}
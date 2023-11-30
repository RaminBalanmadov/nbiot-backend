package net.sinam.nbiot.repository;

import net.sinam.nbiot.entity.CounterConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<CounterConfigEntity, Integer> {

    CounterConfigEntity findCounterConfigEntityBySmartReaderId(String smartReaderId);

}

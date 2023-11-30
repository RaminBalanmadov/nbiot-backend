package net.sinam.nbiot.service;

import lombok.RequiredArgsConstructor;
import net.sinam.nbiot.entity.CounterConfigEntity;
import net.sinam.nbiot.repository.CounterRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CounterService {

    private final CounterRepository counterRepository;

    public void create(CounterConfigEntity counterEntity) {
        counterRepository.save(counterEntity);
    }

}

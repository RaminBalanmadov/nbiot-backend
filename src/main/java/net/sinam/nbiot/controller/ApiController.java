package net.sinam.nbiot.controller;

import lombok.RequiredArgsConstructor;
import net.sinam.nbiot.entity.CounterConfigEntity;
import net.sinam.nbiot.entity.NbiotUnitEntity;
import net.sinam.nbiot.model.ConfigResponseModel;
import net.sinam.nbiot.model.NbiotGoupedResponseModel;
import net.sinam.nbiot.model.NbiotResponseModel;
import net.sinam.nbiot.service.CounterService;
import net.sinam.nbiot.service.NbiotUnitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiController {

    private final CounterService counterService;
    private final NbiotUnitService unitService;

    @PostMapping("create/config")
    public ResponseEntity create(@RequestBody CounterConfigEntity counterEntity) {
        counterService.create(counterEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("add/unit")
    public ResponseEntity addUnit(@RequestBody NbiotUnitEntity unitEntity) {
        unitService.create(unitEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("update/unit")
    public ResponseEntity updateUnit(@RequestBody NbiotUnitEntity unitEntity) {
        unitService.update(unitEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("update/config")
    public ResponseEntity updateConfig(@RequestBody CounterConfigEntity configEntity) throws IOException {
        unitService.updateConfig(configEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("units")
    public List<NbiotUnitEntity> getUnits() {
        return unitService.getUnits();
    }

    @GetMapping("config")
    public List<ConfigResponseModel> getConfig() {
        return unitService.getConfig();
    }

    @GetMapping("grouped-units")
    public List<NbiotGoupedResponseModel> getUnitBySmartReader(@RequestParam String smartReaderId) {
        return unitService.getUnitBySmartReader(smartReaderId);
    }

    @GetMapping("filtered-unit")
    public List<NbiotResponseModel> getFilteredUnit(@RequestParam String smartReaderId,
                                                   @RequestParam(required = false) String startDate,
                                                   @RequestParam(required = false) String endDate) {
        LocalDateTime startD = startDate != null && !startDate.isEmpty() ? LocalDate.parse(startDate).atStartOfDay() : null;
        LocalDateTime endD = endDate != null  && !endDate.isEmpty() ? LocalDate.parse(endDate).atStartOfDay() : null;

        return unitService.getUnitsByDate(smartReaderId, startD, endD);
    }
}

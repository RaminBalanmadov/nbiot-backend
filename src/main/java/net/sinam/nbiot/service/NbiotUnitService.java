package net.sinam.nbiot.service;

import lombok.RequiredArgsConstructor;
import net.sinam.nbiot.entity.CounterConfigEntity;
import net.sinam.nbiot.entity.NbiotUnitEntity;
import net.sinam.nbiot.model.ConfigResponseModel;
import net.sinam.nbiot.model.NbiotGoupedResponseModel;
import net.sinam.nbiot.model.NbiotResponseModel;
import net.sinam.nbiot.repository.CounterRepository;
import net.sinam.nbiot.repository.NbiotUnitConfigRepository;
import net.sinam.nbiot.repository.NbiotUnitRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NbiotUnitService {

    private final CounterRepository counterRepository;
    private final NbiotUnitRepository unitRepository;
    private final NbiotUnitConfigRepository configRepository;

    @Transactional
    public void create(NbiotUnitEntity entity) {
        entity.setAccount(1000);
        entity.setAlarm(0);
        entity.setDate(LocalDateTime.now());
        unitRepository.save(entity);
    }

    @Transactional
    public void update(NbiotUnitEntity entity) {
        unitRepository.updateAllBySmartReaderId(entity.getSmartReaderId(), entity.getCounterId(), entity.getBuildingDeviceId(), entity.getType());
    }

    @Transactional
    public void updateConfig(CounterConfigEntity configEntity) throws IOException {

        String payload = "Event = ConfigUpdater, DevID = c8c8c8c9, UniqueID = 12345618, DataInt = 300000, InfoInt = 3600000";

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("xxx.xx.xxx.xx");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        sendData = payload.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 15012);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + modifiedSentence);
        clientSocket.close();
    }

    public List<NbiotUnitEntity> getUnits() {
        List<String> smartReaders = unitRepository.findDistinctBySmartReaderIdOrderByType();
        List<NbiotUnitEntity> unitEntities = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 1);
        smartReaders.forEach(smartReaderID -> {
            unitEntities.add(unitRepository.findNbiotUnitEntityBySmartReaderIdOrderByDateDesc(smartReaderID, pageable).stream().collect(Collectors.toList()).get(0));
        });
        return unitEntities;
    }

    public List<ConfigResponseModel> getConfig() {
        List<String> smartReaders = configRepository.findDistinctBySmartReaderIdOrderByDate();
        List<ConfigResponseModel> unitEntities = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 1);
        smartReaders.forEach(smartReaderID -> {
            unitEntities.add(configRepository.findNbiotUnitEntityBySmartReaderIdAndDataNameOrderByDateDescJoinConfig(smartReaderID,"DATA interval", pageable).stream().collect(Collectors.toList()).get(0));
        });
        return unitEntities;
    }

    public List<NbiotGoupedResponseModel> getUnitBySmartReader(String smartReaderId) {
        return unitRepository.findAllBySmartReaderIdGroupBy(smartReaderId).stream().peek(model -> model.setUnit(unitRepository.findNbiotUnitEntityBySmartReaderIdOrderByDateDesc(model.getSmartReaderId(), PageRequest.of(0, 1))
                .stream().collect(Collectors.toList()).get(0).getUnit())).collect(Collectors.toList());
    }

    public List<NbiotResponseModel> getUnitsByDate(String smartReaderId, LocalDateTime startDate, LocalDateTime endDate) {
        List<NbiotUnitEntity> unitEntities;
        if (startDate != null && endDate != null) {
            unitEntities = unitRepository.findAllBySmartReaderIdAndDateIsGreaterThanEqualAndDateIsLessThanEqualOrderByDate(smartReaderId, startDate, endDate);
        } else if (startDate != null) {
            unitEntities = unitRepository.findAllBySmartReaderIdAndDateIsGreaterThanEqualOrderByDate(smartReaderId, startDate);
        } else {
            unitEntities = unitRepository.findAllBySmartReaderIdAndDateIsLessThanEqualOrderByDate(smartReaderId, endDate);
        }
        Set<LocalDate> dates = new HashSet<>();
        return unitEntities.stream().map(unitEntity -> {
                    if (dates.stream().anyMatch(s -> s.equals(unitEntity.getDate().toLocalDate()))) {
                        return null;
                    }
                    dates.add(unitEntity.getDate().toLocalDate());
                    NbiotResponseModel responseModel = new NbiotResponseModel();
                    responseModel.setSmartReaderId(unitEntity.getSmartReaderId());
                    responseModel.setDate(unitEntity.getDate());
                    responseModel.setUnitAndTimes(unitEntities.stream().filter(unitEntity1 -> unitEntity1.getDate().toLocalDate().equals(unitEntity.getDate().toLocalDate()))
                            .map(unitEntity1 -> {
                                Map<String, String> unitAndDate = new HashMap<>();
                                unitAndDate.put("time", unitEntity1.getDate().toString());
                                unitAndDate.put("unit", unitEntity1.getUnit().toString());
                                return unitAndDate;
                            }).collect(Collectors.toList()));
                    responseModel.setUnit(unitEntities.stream().filter(unitEntity1 -> unitEntity1.getDate().toLocalDate().equals(unitEntity.getDate().toLocalDate()))
                            .sorted(Comparator.comparing(NbiotUnitEntity::getDate, Comparator.reverseOrder()))
                            .collect(Collectors.toList()).get(0).getUnit());

                    return responseModel;
                }).filter(Objects::nonNull).collect(Collectors.toSet()).stream().filter(nbiotResponseModel -> nbiotResponseModel.getDate() != null)
                .sorted(Comparator.comparing(o -> o.getDate().toLocalDate())).collect(Collectors.toList());
    }

}

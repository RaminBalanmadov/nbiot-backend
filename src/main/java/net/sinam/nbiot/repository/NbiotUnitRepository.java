package net.sinam.nbiot.repository;

import net.sinam.nbiot.entity.NbiotUnitEntity;
import net.sinam.nbiot.model.ConfigResponseModel;
import net.sinam.nbiot.model.NbiotGoupedResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NbiotUnitRepository extends JpaRepository<NbiotUnitEntity, Integer> {

    @Query("select distinct n.smartReaderId from NbiotUnitEntity n order by n.type")
    List<String> findDistinctBySmartReaderIdOrderByType();


    //    @Query("select n from NbiotUnitEntity n where n.smartReaderId = :smartReaderId order by n.date desc limit 1")
    Page<NbiotUnitEntity> findNbiotUnitEntityBySmartReaderIdOrderByDateDesc(String smartReaderId, Pageable pageable);

    @Query("select new net.sinam.nbiot.model.NbiotGoupedResponseModel(n.smartReaderId, n.counterId) from NbiotUnitEntity n where n.buildingDeviceId = :smartReaderId group by n.counterId,n.smartReaderId order by n.date desc ")
    List<NbiotGoupedResponseModel> findAllBySmartReaderIdGroupBy(String smartReaderId);

    List<NbiotUnitEntity> findAllBySmartReaderIdAndDateIsGreaterThanEqualAndDateIsLessThanEqualOrderByDate(String smartReaderId, LocalDateTime startDate, LocalDateTime endDate);
    List<NbiotUnitEntity> findAllBySmartReaderIdAndDateIsGreaterThanEqualOrderByDate(String smartReaderId, LocalDateTime startDate);
    List<NbiotUnitEntity> findAllBySmartReaderIdAndDateIsLessThanEqualOrderByDate(String smartReaderId, LocalDateTime endDate);

    @Modifying
    @Query("update NbiotUnitEntity n set n.counterId= :counterId, n.type = :type, n.buildingDeviceId = :buildingDeviceId where n.smartReaderId = :smartReaderId")
    void updateAllBySmartReaderId(String smartReaderId, String counterId, String buildingDeviceId, Integer type);
}

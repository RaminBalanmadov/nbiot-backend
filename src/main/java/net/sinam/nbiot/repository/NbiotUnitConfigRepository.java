package net.sinam.nbiot.repository;

import net.sinam.nbiot.entity.NbiotConfigEntity;
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
public interface NbiotUnitConfigRepository extends JpaRepository<NbiotConfigEntity, Integer> {

    @Query("select distinct n.smartReaderId from NbiotConfigEntity n order by n.date")
    List<String> findDistinctBySmartReaderIdOrderByDate();

    @Query("select new net.sinam.nbiot.model.ConfigResponseModel(n.smartReaderId,n.dataValue,n.date) from NbiotConfigEntity n where n.smartReaderId = :smartReaderId and n.dataName = :dataName order by n.date desc ")
    Page<ConfigResponseModel> findNbiotUnitEntityBySmartReaderIdAndDataNameOrderByDateDescJoinConfig(String smartReaderId,String dataName, Pageable pageable);

}

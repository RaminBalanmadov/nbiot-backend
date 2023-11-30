package net.sinam.nbiot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class NbiotGoupedResponseModel {

    public NbiotGoupedResponseModel(String smartReaderId, String counterId, Integer unit) {
        this.smartReaderId = smartReaderId;
        this.counterId = counterId;
        this.unit = unit;
    }

    public NbiotGoupedResponseModel(String smartReaderId, String counterId) {
        this.smartReaderId = smartReaderId;
        this.counterId = counterId;
    }

    private String smartReaderId;
    private String counterId;
    private Integer unit;

}

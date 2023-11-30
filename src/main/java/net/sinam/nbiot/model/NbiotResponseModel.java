package net.sinam.nbiot.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class NbiotResponseModel {

    private String smartReaderId;
    private LocalDateTime date;
    private Integer unit;
    private List<Map<String, String>> unitAndTimes;

}

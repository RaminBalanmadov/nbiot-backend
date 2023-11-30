package net.sinam.nbiot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigResponseModel {

    private String smartReaderId;
    private String dataValue;
    private LocalDateTime date;

}

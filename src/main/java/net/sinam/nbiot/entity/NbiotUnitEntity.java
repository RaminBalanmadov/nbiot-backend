
package net.sinam.nbiot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DevicesData_0012")
public class NbiotUnitEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Date")
    private LocalDateTime date;
    @Column(name = "DeviceID")
    private String smartReaderId;
    @Column(name = "BuildingDeviceID")
    private String buildingDeviceId;
    @Column(name = "Account")
    private Integer account;
    @Column(name = "Units")
    private Integer unit;
    @Column(name = "Alarm")
    private Integer alarm;
    @Column(name = "CounterID")
    private String counterId;
    @Column(name = "Type")
    private Integer type;

}

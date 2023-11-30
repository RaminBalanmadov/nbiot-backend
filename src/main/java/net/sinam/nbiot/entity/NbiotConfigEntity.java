
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
@Table(name = "DevicesInfo_0012")
public class NbiotConfigEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Date")
    private LocalDateTime date;
    @Column(name = "DeviceID")
    private String smartReaderId;
    @Column(name = "Account")
    private Integer account;
    @Column(name = "DataName")
    private String dataName;
    @Column(name = "DataValue")
    private String dataValue;

}


package net.sinam.nbiot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "CounterConfig")
public class CounterConfigEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "Time")
    private Integer interval;
    @Column(name = "SmartReaderID")
    private String smartReaderId;
    @Column(name = "Unit")
    private Integer unit;
    @Column(name = "StartDate")
    private LocalDate startDate;

}

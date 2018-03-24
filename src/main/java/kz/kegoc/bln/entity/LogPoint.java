package kz.kegoc.bln.entity;

import kz.kegoc.bln.converter.jpa.BooleanToIntConverter;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of= {"id"})
@NoArgsConstructor
@Entity
@Table(name = "log_points")
public class LogPoint {
    public LogPoint(Long id) { this.id = id; }

    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "last_load_time")
    private LocalDateTime lastLoadTime;

    @Column(name = "is_active")
    @Convert(converter = BooleanToIntConverter.class)
    private Boolean isActive;
}

package in.ashokit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cities_master")
@Setter
@Getter
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;

    private String cityName;

    @ManyToOne
    @JoinColumn(name ="stateId")
    private StateEntity state;
}

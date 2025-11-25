package in.ashokit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name= "user_master")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    private String name;

    private String email;

    private String pwd;

    private String pwdUpdated;

    private Long phno;

    @ManyToOne
    @JoinColumn(name ="countryId")
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name ="stateId")
    private StateEntity state;

    @ManyToOne
    @JoinColumn(name ="cityId")
    private CityEntity city;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;
}

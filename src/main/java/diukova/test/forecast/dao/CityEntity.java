package diukova.test.forecast.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "cities")
@Getter
@Setter
public class CityEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String city;

    @Column
    private String latitude;

    @Column
    private String longitude;
}

package diukova.test.forecast.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name="chat_to_city")
@Setter
@Getter
public class ChatToCityEntity {

    @Id
    @SequenceGenerator(name="chat_to_city_id_seq",
            sequenceName="chat_to_city_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="chat_to_city_id_seq")
    private Long id;

    @Column
    private Long chatId;

    @Column
    private Long cityId;
}

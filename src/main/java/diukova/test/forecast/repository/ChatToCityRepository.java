package diukova.test.forecast.repository;

import diukova.test.forecast.dao.ChatToCityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatToCityRepository extends JpaRepository<ChatToCityEntity, Long> {

    @Query("SELECT ctc FROM chat_to_city ctc WHERE ctc.chatId = :chatId")
    Optional<ChatToCityEntity> findChatToCityByChatId(
            @Param("chatId") Long chatId);
}

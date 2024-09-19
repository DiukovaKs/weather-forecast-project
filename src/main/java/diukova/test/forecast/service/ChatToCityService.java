package diukova.test.forecast.service;

import diukova.test.forecast.dao.ChatToCityEntity;
import diukova.test.forecast.repository.ChatToCityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatToCityService {
    @Autowired
    ChatToCityRepository chatToCityRepository;

    public Long getCityId(Long chatId) {
        Optional<ChatToCityEntity> entity = chatToCityRepository.findChatToCityByChatId(chatId);

        return entity.map(ChatToCityEntity::getCityId).orElse(null);
    }

    public void setCityToChat(Long chatId, Long cityId) {
        Optional<ChatToCityEntity> entity = chatToCityRepository.findChatToCityByChatId(chatId);

        if (entity.isEmpty()) {
            ChatToCityEntity newEntity = new ChatToCityEntity();
            newEntity.setChatId(chatId);
            newEntity.setCityId(cityId);

            chatToCityRepository.save(newEntity);
        } else if (!entity.get().getCityId().equals(cityId)) {
            ChatToCityEntity entityUpdated = entity.get();

            entityUpdated.setCityId(cityId);
            chatToCityRepository.saveAndFlush(entityUpdated);
        }
    }
}

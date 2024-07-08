package diukova.test.forecast.service;

import diukova.test.forecast.repository.ChatToCityRepository;
import diukova.test.forecast.repository.CityRepository;
import diukova.test.forecast.dao.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public List<CityEntity> getCities() {
        return cityRepository.findAll();
    }

    public CityEntity getCityById(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }
}

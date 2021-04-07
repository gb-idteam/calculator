package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.Image;

public interface ImageService {
    void save(Image image);
    Image findById(Long id);
}

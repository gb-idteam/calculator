package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.repository.ImageRepository;
import ru.systemairac.calculator.service.allinterface.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow();
    }
}

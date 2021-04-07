package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Image;
import ru.systemairac.calculator.repository.ImageRepository;
import ru.systemairac.calculator.service.allinterface.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    void init(){
        Image image1 = Image.builder().link("statico/img/humidifiers/ehu-1.png").build();
        Image image2 = Image.builder().link("statico/img/humidifiers/ers.png").build();
        imageRepository.save(image1);
        imageRepository.save(image2);
    }

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        init();
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

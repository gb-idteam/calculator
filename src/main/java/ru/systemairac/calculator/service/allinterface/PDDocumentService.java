package ru.systemairac.calculator.service.allinterface;

import org.apache.pdfbox.pdmodel.PDDocument;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.*;

import java.io.IOException;
import java.util.List;

public interface PDDocumentService {

    PDDocument toPDDocument(UserDto userDto,
                            ProjectDto projectDto,
                            TechDataDto techDataDto,
                            HumidifierDto humidifierDto,
                            List<HumidifierComponentDto> humidifierComponentDtoList,
                            VaporDistributorDto distributor
    ) throws IOException;
}

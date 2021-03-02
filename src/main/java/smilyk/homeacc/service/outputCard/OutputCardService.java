package smilyk.homeacc.service.outputCard;

import smilyk.homeacc.dto.OutputCardDto;

import java.util.List;

public interface OutputCardService {

    OutputCardDto createInputCard(OutputCardDto outputCardDto);

    List<OutputCardDto> getAllOutputCardsByUserUuid(String userUuid);
}

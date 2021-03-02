package smilyk.homeacc.service.inputCard;

import smilyk.homeacc.dto.InputCardDto;

import java.util.List;

public interface InputCardService {
    InputCardDto createInputCard(InputCardDto inputCardDto);

    List<InputCardDto> getAllInputCardsByUserUuid(String userUuid);

    InputCardDto deleteInputCard(String inputCardUuid);
}

package smilyk.homeacc.service.inputCard;

import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.model.InputCard;

import java.util.Date;
import java.util.List;

public interface InputCardService {
    InputCardDto createInputCard(InputCardDto inputCardDto);

    List<InputCardDto> getAllInputCardsByUserUuid(String userUuid);

    InputCardDto deleteInputCard(String inputCardUuid);

    InputCardDto getInputCardByUuid(String userUuid, String inputCardUuid);

    List<InputCard> getAllInputCardsByUserUuidAndDate(String userUuid, Date chosenDate);
}

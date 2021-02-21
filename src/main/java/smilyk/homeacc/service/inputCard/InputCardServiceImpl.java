package smilyk.homeacc.service.inputCard;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.InputCardConstant;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.model.InputCard;
import smilyk.homeacc.repo.InputCardRepository;
import smilyk.homeacc.service.user.UserServiceImpl;
import smilyk.homeacc.utils.Utils;

@Service
public class InputCardServiceImpl implements InputCardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    InputCardRepository inputCardRepository;
    @Autowired
    Utils utils;

    @Override
    public InputCardDto createInputCard(InputCardDto inputCardDto) {
        InputCard inputCard = InputCard.builder()
            .deleted(false)
            .subcategoryUuid(inputCardDto.getSubCategoryUuid())
            .subcategoryName(inputCardDto.getSubCategoryName())
            .categoryUuid(inputCardDto.getCategoryUuid())
            .categoryName(inputCardDto.getCategoryName())
            .billName(inputCardDto.getBillName())
            .billUuid(inputCardDto.getBillUuid())
            .inputCardUuid(utils.generateUserUuid().toString())
            .count(inputCardDto.getCount())
            .currency(inputCardDto.getCurrency())
            .userUuid(inputCardDto.getUserUuid())
            .discount(inputCardDto.getDiscount())
            .note(inputCardDto.getNote())
            .sum(inputCardDto.getSum())
            .unit(inputCardDto.getUnit())
            .build();
        inputCard.setInputCardUuid(utils.generateUserUuid().toString());
        inputCard.setDeleted(false);
        inputCardRepository.save(inputCard);
        LOGGER.info(InputCardConstant.INPUT_CARD + inputCardDto.getUserUuid() +
            InputCardConstant.CREATED);
        return modelMapper.map(inputCard, InputCardDto.class);
    }
}

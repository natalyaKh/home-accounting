package smilyk.homeacc.service.outputCard;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.constants.OutputCardConstant;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.dto.OutputCardDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.InputCard;
import smilyk.homeacc.model.OutputCard;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.repo.OutputCardRepository;
import smilyk.homeacc.service.user.UserServiceImpl;
import smilyk.homeacc.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OutputCardServiceImpl implements OutputCardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    OutputCardRepository outputCardRepository;
    @Autowired
    Utils utils;
    @Autowired
    BillRepository billRepository;

    @Override
    public OutputCardDto createInputCard(OutputCardDto outputCardDto) {
        Bill bill = billRepository.findByBillNameAndUserUuidAndDeleted(outputCardDto.getBillName(),
            outputCardDto.getUserUuid(), false).get();
        billRepository.delete(bill);
        bill = changeSum(outputCardDto, bill);
        billRepository.save(bill);
        LOGGER.info(BillConstants.BILL_SUM + BillConstants.FOR + BillConstants.BILL_WITH_NAME
        +bill.getBillName() + BillConstants.CHANGED);
        OutputCard outputCard = getOutputCard(outputCardDto);
        outputCardRepository.save(outputCard);
        LOGGER.info(OutputCardConstant.OUTPUT_CARD + outputCardDto.getUserUuid() +
            OutputCardConstant.CREATED);
        return modelMapper.map(outputCard, OutputCardDto.class);
    }




    @Override
    public List<OutputCardDto> getAllOutputCardsByUserUuid(String userUuid) {
        Optional<List<OutputCard>> optionalInputCards = outputCardRepository.findAllByUserUuid(userUuid);
        return optionalInputCards.map(categories -> categories.stream().map(this::outputCardToOutputCardDto)
            .collect(Collectors.toList())).orElseGet(ArrayList::new);

    }

    @Override
    public OutputCardDto deleteOutputCard(String outputCardUuid) {
        Optional<OutputCard> optionalOutputCard = outputCardRepository.findByOutputCardUuid(outputCardUuid);
        outputCardRepository.delete(optionalOutputCard.get());
        LOGGER.info(OutputCardConstant.OUTPUT_CARD_WITH_UUID + outputCardUuid + OutputCardConstant.DELETED);
        return modelMapper.map(optionalOutputCard.get(), OutputCardDto.class);
    }

    private OutputCardDto outputCardToOutputCardDto(OutputCard outputCard) {
        OutputCardDto outputCardDto = modelMapper.map(outputCard, OutputCardDto.class);
        outputCardDto.setOutputcardUuid(outputCard.getOutputCardUuid());
        return outputCardDto;
// TODO test
    }

    private Bill changeSum(OutputCardDto outputCardDto, Bill bill) {
        if (outputCardDto.getCurrency().equals(Currency.USA)) {
            bill.setSumUsa(bill.getSumUsa() - outputCardDto.getSum());
        }
        else if (outputCardDto.getCurrency().equals(Currency.ISR)) {
            bill.setSumIsr(bill.getSumIsr() - outputCardDto.getSum());
        }
        else if (outputCardDto.getCurrency().equals(Currency.UKR)) {
            bill.setSumUkr(bill.getSumUkr() - outputCardDto.getSum());
        }
        return bill;
    }

    private OutputCard getOutputCard(OutputCardDto outputCardDto) {
        OutputCard outputCard = OutputCard.builder()
            .deleted(false)
            .subcategoryUuid(outputCardDto.getSubcategoryUuid())
            .subcategoryName(outputCardDto.getSubcategoryName())
            .categoryUuid(outputCardDto.getCategoryUuid())
            .categoryName(outputCardDto.getCategoryName())
            .billName(outputCardDto.getBillName())
            .billUuid(outputCardDto.getBillUuid())
            .outputCardUuid(utils.generateUserUuid().toString())
            .count(outputCardDto.getCount())
            .currency(outputCardDto.getCurrency())
            .userUuid(outputCardDto.getUserUuid())
            .discount(outputCardDto.getDiscount())
            .note(outputCardDto.getNote())
            .sum(outputCardDto.getSum())
            .unit(outputCardDto.getUnit())
            .createCardDate(outputCardDto.getCreateCardDate())
            .build();
        outputCard.setOutputCardUuid(utils.generateUserUuid().toString());
        outputCard.setDeleted(false);
        return outputCard;
    }
}

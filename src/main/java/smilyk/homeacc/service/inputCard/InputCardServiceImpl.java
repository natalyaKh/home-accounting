package smilyk.homeacc.service.inputCard;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.constants.InputCardConstant;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.InputCard;
import smilyk.homeacc.repo.BillRepository;
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
    @Autowired
    BillRepository billRepository;

    @Override
    public InputCardDto createInputCard(InputCardDto inputCardDto) {
        Bill bill = billRepository.findByBillNameAndUserUuidAndDeleted(inputCardDto.getBillName(),
            inputCardDto.getUserUuid(), false).get();
        billRepository.delete(bill);
        bill = changeSum(inputCardDto, bill);
        billRepository.save(bill);
        LOGGER.info(BillConstants.BILL_SUM + BillConstants.FOR + BillConstants.BILL_WITH_NAME
        +bill.getBillName() + BillConstants.CHANGED);
        InputCard inputCard = getInputCard(inputCardDto);
        inputCardRepository.save(inputCard);
        LOGGER.info(InputCardConstant.INPUT_CARD + inputCardDto.getUserUuid() +
            InputCardConstant.CREATED);
        return modelMapper.map(inputCard, InputCardDto.class);
    }

    private Bill changeSum(InputCardDto inputCardDto, Bill bill) {
        if (inputCardDto.getCurrency().equals(Currency.USA)) {
            bill.setSumUsa(bill.getSumUsa() - inputCardDto.getSum());
        }
        else if (inputCardDto.getCurrency().equals(Currency.ISR)) {
            bill.setSumIsr(bill.getSumIsr() - inputCardDto.getSum());
        }
        else if (inputCardDto.getCurrency().equals(Currency.UKR)) {
            bill.setSumUkr(bill.getSumUkr() - inputCardDto.getSum());
        }
        return bill;
    }

    private InputCard getInputCard(InputCardDto inputCardDto) {
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
        return inputCard;
    }
}

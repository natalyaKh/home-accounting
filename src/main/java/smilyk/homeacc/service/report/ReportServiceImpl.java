package smilyk.homeacc.service.report;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.ReportConstants;
import smilyk.homeacc.dto.LastOperationsDto;
import smilyk.homeacc.dto.LastOperationsRequestDto;
import smilyk.homeacc.enums.CategoryType;
import smilyk.homeacc.enums.Period;
import smilyk.homeacc.model.InputCard;
import smilyk.homeacc.model.OutputCard;
import smilyk.homeacc.service.inputCard.InputCardServiceImpl;
import smilyk.homeacc.service.outputCard.OutputCardServiceImpl;
import smilyk.homeacc.service.user.UserServiceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    LocalDate today = LocalDate.now();
    Date chosenDate;
    @Autowired
    InputCardServiceImpl inputCardService;
    @Autowired
    OutputCardServiceImpl outputCardService;


    @Override
//    TODO test
    public List<LastOperationsDto> getAllLastOperations(LastOperationsRequestDto dto, String userUuid) {
        getDate(dto, userUuid);
        List<OutputCard> outputCards = outputCardService.getAllOutputCardsByUserAndDate(
            userUuid, chosenDate
        );
        List<InputCard> inputCards = inputCardService.getAllInputCardsByUserUuidAndDate(
            userUuid, chosenDate
        );

        List<LastOperationsDto> rez = Stream.concat(
            inputCards.stream().map(this::inputCardToLastOperation),
            outputCards.stream().map(this::outputCardToLastOperations))
            .sorted(Comparator.comparing(LastOperationsDto::getDate)
                .thenComparing(LastOperationsDto::getBillName))
            .collect(Collectors.toList());
        LOGGER.info(ReportConstants.LIST_LAST_OPERATION + rez + ReportConstants.PERIOD +
            dto.getPeriod().name() + ReportConstants.GETTING);
        return rez;
    }

    private LastOperationsDto inputCardToLastOperation(InputCard inputCard) {
        return LastOperationsDto.builder()
            .date(inputCard.getCreateCardDate())
            .billName(inputCard.getBillName())
            .category(inputCard.getCategoryName())
            .subcategory(inputCard.getSubcategoryName())
            .description(inputCard.getNote())
            .sum(inputCard.getSum())
            .operationUuid(inputCard.getInputCardUuid())
            .type(CategoryType.INPUT)
            .build();
    }

    private LastOperationsDto outputCardToLastOperations(OutputCard outputCard) {
        return LastOperationsDto.builder()
            .date(outputCard.getCreateCardDate())
            .billName(outputCard.getBillName())
            .category(outputCard.getCategoryName())
            .subcategory(outputCard.getSubcategoryName())
            .description(outputCard.getNote())
            .sum(outputCard.getSum())
            .operationUuid(outputCard.getOutputCardUuid())
            .type(CategoryType.OUTPUT)
            .build();
    }

    private void getDate(LastOperationsRequestDto dto, String userUuid) {
        if (dto.getPeriod().equals(Period.DAY)) {
            chosenDate = localDateToDate(today);
        }
        if (dto.getPeriod().equals(Period.WEEK)) {
            chosenDate = localDateToDate(today.minusWeeks(1));
        }
        if (dto.getPeriod().equals(Period.MONTH)) {
            chosenDate = localDateToDate(today.minusMonths(1));
        }
        if (dto.getPeriod().equals(Period.YEAR)) {
            chosenDate = localDateToDate(today.minusYears(1));
        }
        LOGGER.info(ReportConstants.CHOSEN_DATE + chosenDate);
    }

    private Date localDateToDate(LocalDate today) {
        return Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }
}

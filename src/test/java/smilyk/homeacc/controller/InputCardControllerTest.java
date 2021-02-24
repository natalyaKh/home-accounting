package smilyk.homeacc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.InputCard;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.repo.InputCardRepository;
import smilyk.homeacc.service.inputCard.InputCardServiceImpl;
import smilyk.homeacc.utils.Utils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class InputCardControllerTest {

}

package smilyk.homeacc.service.report;

import smilyk.homeacc.dto.LastOperationsDto;
import smilyk.homeacc.dto.LastOperationsRequestDto;

import java.util.List;

public interface ReportService {
    List<LastOperationsDto> getAllLastOperations(LastOperationsRequestDto dto, String userUuid);
}

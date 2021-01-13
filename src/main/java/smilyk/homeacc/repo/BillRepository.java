package smilyk.homeacc.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import smilyk.homeacc.model.Bill;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {


    Optional<Bill> findByBillNameAndDeleted(String billName, boolean b);

    Optional<Bill> findByMainBill(boolean b);

    Optional<Bill> findByBillNameAndUserUuidAndDeleted(String billName,String userUuid, boolean b);

    List<Bill> findAllByUserUuidAndDeleted(String userUuid, boolean b);

    Optional<Bill> findByBillNameAndUserUuidAndDeletedAndCurrencyName(String billName, String userUuid, boolean b, String name);
}

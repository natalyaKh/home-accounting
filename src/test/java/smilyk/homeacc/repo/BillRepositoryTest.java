//package smilyk.homeacc.repo;
//
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import smilyk.homeacc.enums.Currency;
//import smilyk.homeacc.model.Bill;
//
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//
//
//public class BillRepositoryTest {
//
//    @Autowired
//    BillRepository billRepository;
//
//    @Test
//    public void saveBill() {
//        Bill bill = Bill.builder()
//                .billName("bill")
//                .billUuid("1111")
//                .currencyName(Currency.ALL)
//                .deleted(false)
//                .mainBill(true)
//                .description("bill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
//        Bill gettingBill = billRepository.save(bill);
//        Assert.assertEquals(bill, gettingBill);
//        Assert.assertNotNull(bill.getId());
//    }
//
//    @Test
//    public void findByBillNameAndDeleted() {
//        //not deleted bill
//        Bill bill = Bill.builder()
//                .billName("bill")
//                .billUuid("1111")
//                .currencyName(Currency.ALL)
//                .deleted(false)
//                .mainBill(true)
//                .description("bill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
////        deleted bill
//        Bill bill2 = Bill.builder()
//                .billName("bill2")
//                .billUuid("1112")
//                .currencyName(Currency.ALL)
//                .deleted(true)
//                .mainBill(true)
//                .description("deletedBill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
//        billRepository.save(bill);
//        billRepository.save(bill2);
//        Bill notDeletedBill = billRepository.findByBillNameAndDeleted("bill", false).get();
//        Bill deletedBill = billRepository.findByBillNameAndDeleted("bill2", true).get();
//        Optional<Bill> wrongBill = billRepository.findByBillNameAndDeleted("bill2", false);
//
//        Assert.assertEquals(bill, notDeletedBill);
//        Assert.assertEquals(bill2, deletedBill);
//        Assert.assertFalse(wrongBill.isPresent());
//    }
//
//    //
//    @Test
//    public void findByMainBill() {
////        main
//        Bill bill = Bill.builder()
//                .billName("bill")
//                .billUuid("1111")
//                .currencyName(Currency.ALL)
//                .deleted(false)
//                .mainBill(true)
//                .description("bill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
////        notMani
//        Bill bill2 = Bill.builder()
//                .billName("bill2")
//                .billUuid("1112")
//                .currencyName(Currency.ALL)
//                .deleted(false)
//                .mainBill(false)
//                .description("deletedBill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
//        billRepository.save(bill);
//        billRepository.save(bill2);
//        Bill notMainBill = billRepository.findByMainBill(false).get();
//        Bill mainBill = billRepository.findByMainBill(true).get();
//
//
//        Assert.assertEquals(bill, mainBill);
//        Assert.assertEquals(bill2, notMainBill);
//
//    }
//
//    @Test
//    public void findByBillNameAndUserUuidAndDeleted() {
//        //not deleted bill
//        Bill bill = Bill.builder()
//                .billName("bill")
//                .billUuid("1111")
//                .currencyName(Currency.ALL)
//                .deleted(false)
//                .mainBill(true)
//                .description("bill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
////        deleted bill
//        Bill bill2 = Bill.builder()
//                .billName("bill2")
//                .billUuid("1112")
//                .currencyName(Currency.ALL)
//                .deleted(true)
//                .mainBill(true)
//                .description("deletedBill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("2222")
//                .build();
//        billRepository.save(bill);
//        billRepository.save(bill2);
//        Bill notDeletedBill = billRepository.findByBillNameAndUserUuidAndDeleted("bill", "1111", false).get();
//        Bill deletedBill = billRepository.findByBillNameAndUserUuidAndDeleted("bill2", "2222", true).get();
//        Optional<Bill> wrongBill = billRepository.findByBillNameAndUserUuidAndDeleted("bill2", "2222", false);
//
//        Assert.assertEquals(bill, notDeletedBill);
//        Assert.assertEquals(bill2, deletedBill);
//        Assert.assertFalse(wrongBill.isPresent());
//    }
//
//    @Test
//    public void findAllByUserUuidAndDeleted() {
//        //not deleted bill
//        Bill bill = Bill.builder()
//                .billName("bill")
//                .billUuid("1111")
//                .currencyName(Currency.ALL)
//                .deleted(false)
//                .mainBill(true)
//                .description("bill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
//        //not deleted bill
//        Bill bill3 = Bill.builder()
//                .billName("bill3")
//                .billUuid("3333")
//                .currencyName(Currency.ALL)
//                .deleted(false)
//                .mainBill(true)
//                .description("bill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("1111")
//                .build();
////        deleted bill
//        Bill bill2 = Bill.builder()
//                .billName("bill2")
//                .billUuid("1112")
//                .currencyName(Currency.ALL)
//                .deleted(true)
//                .mainBill(true)
//                .description("deletedBill")
//                .sumIsr(0.0)
//                .sumUkr(0.0)
//                .sumUsa(0.0)
//                .userUuid("2222")
//                .build();
//        billRepository.save(bill);
//        billRepository.save(bill3);
//        billRepository.save(bill2);
//        List<Bill> notDeletedBill = billRepository.findAllByUserUuidAndDeleted("1111", false);
//        List<Bill> deletedBill = billRepository.findAllByUserUuidAndDeleted("2222", true);
//        List<Bill> wrongBill = billRepository.findAllByUserUuidAndDeleted("3333", true);
//
//        Assert.assertEquals(2, notDeletedBill.size());
//        Assert.assertEquals(1, deletedBill.size());
//        Assert.assertEquals(0, wrongBill.size());
//    }
//
//    @Test
//    public void findByBillNameAndUserUuidAndDeletedAndCurrencyName() {
////       TODO create test
//    }
//
//}
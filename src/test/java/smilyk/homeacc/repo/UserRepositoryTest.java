package smilyk.homeacc.repo;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;
import smilyk.homeacc.model.User;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Test
    public void saveUser() {
        User user = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1111")
                .build();
        userRepository.save(user);
        Assert.assertEquals("UserFirstName", user.getFirstName());
        Assert.assertEquals("UserLastName", user.getLastName());
        Assert.assertEquals("1234", user.getEncryptedPassword());
        Assert.assertEquals(false, user.isDeleted());
        Assert.assertEquals("mail@mail.com", user.getEmail());
        Assert.assertEquals("1111", user.getUserUuid());
        Assert.assertNotNull(user.getId());
    }


    @Test
    public void findAllUsers() {
        User user1 = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .build();
        User user2 = User.builder()
                .firstName("UserFirstName_2")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .build();
        User user3 = User.builder()
                .firstName("UserFirstName_3")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        userRepository.saveAll(users);

        int page = 0;
        int limit = 10;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<User> usersPage = userRepository.findAll(pageableRequest);
        List<User> gettingUsers = userRepository.findAll(pageableRequest).getContent();
        Assert.assertEquals(3, gettingUsers.size());
        Assert.assertEquals("UserFirstName", gettingUsers.get(0).getFirstName());
        Assert.assertEquals("UserFirstName_2", gettingUsers.get(1).getFirstName());
        Assert.assertEquals("UserFirstName_3", gettingUsers.get(2).getFirstName());


    }

    @Test
    public void findUserByEmailVerificationToken() {
        User user = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .emailVerificationToken("12345")
                .build();
        User user2 = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .emailVerificationToken("123456")
                .build();
        userRepository.save(user);
        userRepository.save(user2);
        User gettingUser = userRepository.findUserByEmailVerificationToken("12345").get();
        Assert.assertEquals(user, gettingUser);
    }


    @Test
    public void findByEmailAndDeleted() {
//        deleted user
        User user = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(true)
                .email("user@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .emailVerificationToken("12345")
                .build();
//        not deleted user
        User user2 = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1112")
                .emailVerificationStatus(false)
                .emailVerificationToken("123456")
                .build();
        userRepository.save(user);
        userRepository.save(user2);
        User gettingNotDeletedUser = userRepository.findByEmailAndDeleted("mail@mail.com", false).get();
        User gettingDeletedUser = userRepository.findByEmailAndDeleted("user@mail.com", true).get();
        Assert.assertEquals(user2, gettingNotDeletedUser);
        Assert.assertEquals(user, gettingDeletedUser);
    }

    @Test
    public void findByUserUuidAndDeleted() {
//        deleted user
        User user = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(true)
                .email("user@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .emailVerificationToken("12345")
                .build();
//        not deleted user
        User user2 = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(false)
                .email("mail@mail.com")
                .userUuid("1112")
                .emailVerificationStatus(false)
                .emailVerificationToken("123456")
                .build();
        userRepository.save(user);
        userRepository.save(user2);
        User gettingNotDeletedUser = userRepository.findUserByUserUuidAndDeleted("1112", false).get();
        User gettingDeletedUser = userRepository.findUserByUserUuidAndDeleted("1111", true).get();
        Assert.assertEquals(user2, gettingNotDeletedUser);
        Assert.assertEquals(user, gettingDeletedUser);
    }
}
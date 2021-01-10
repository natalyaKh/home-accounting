package smilyk.homeacc.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import smilyk.homeacc.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}

package am.itspace.storebook.repository;

import am.itspace.storebook.entity.Order;
import am.itspace.storebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUser(User user);

}

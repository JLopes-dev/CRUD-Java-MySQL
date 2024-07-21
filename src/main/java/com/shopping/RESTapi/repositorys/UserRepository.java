package com.shopping.RESTapi.repositorys;

import com.shopping.RESTapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}

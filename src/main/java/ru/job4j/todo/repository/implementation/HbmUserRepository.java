package ru.job4j.todo.repository.implementation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(HbmUserRepository.class);

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        var map = new HashMap<String, Object>();
        map.put("flogin", login);
        map.put("fpassword", password);
        return crudRepository.optional("FROM User u WHERE u.login = :flogin AND u.password = :fpassword",
                User.class,
                map);
    }

    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.save(user));
            return Optional.of(user);
        } catch (Exception e) {
            var cause = e.getCause();
            if (cause instanceof SQLException sqlEx) {
                if ("23505".equals(sqlEx.getSQLState())) {
                    LOGGER.error("Такой email уже существует");
                } else {
                    LOGGER.error("SQLState: {}", sqlEx.getSQLState());
                }
            }
        }
        return Optional.empty();
    }
}

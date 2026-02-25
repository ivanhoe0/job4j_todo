package ru.job4j.todo.repository.implementation;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private final SessionFactory sf;

    private static final Logger LOGGER = LoggerFactory.getLogger(HbmUserRepository.class);

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery("FROM User u WHERE u.login = :flogin AND u.password = :fpassword", User.class)
                .setParameter("flogin", login)
                .setParameter("fpassword", password);
        var result = query.uniqueResultOptional();
        session.close();
        return result;
    }

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
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

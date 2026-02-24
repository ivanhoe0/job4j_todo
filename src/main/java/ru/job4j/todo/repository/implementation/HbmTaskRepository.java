package ru.job4j.todo.repository.implementation;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {

    private final SessionFactory sf;

    @Override
    public List<Task> findUnFinishedTasks() {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery("FROM Task t WHERE t.done = FALSE", Task.class);
        var result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public List<Task> findFinishedTasks() {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery("FROM Task t WHERE t.done = TRUE", Task.class);
        var result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery("FROM Task t WHERE t.id = :fid", Task.class)
                .setParameter("fid", id);
        var result = query.uniqueResultOptional();
        session.close();
        return result;
    }

    @Override
    public Task addTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            task.setCreated(LocalDateTime.now());
            session.save(task);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        }
        return task;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Task task = new Task();
            task.setId(id);
            session.delete(task);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery("FROM Task", Task.class);
        var result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public boolean makeDone(Integer id) {
        var optionalTask = findById(id);
        if (optionalTask.isPresent()) {
            Session session = sf.openSession();
            try {
                session.beginTransaction();
                Task task = optionalTask.get();
                task.setDone(true);
                session.update(task);
                session.getTransaction().commit();
                session.close();
            } catch (Exception e) {
                session.getTransaction().rollback();
                return false;
            }
        }
        return optionalTask.isPresent();
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.merge(task);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }
}

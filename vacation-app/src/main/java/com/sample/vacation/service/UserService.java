package com.sample.vacation.service;

import com.sample.vacation.dao.UserDao;
import com.sample.vacation.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Iryna Subota on 21.10.2016.
 */

@Service("user-service")
@Transactional
public class UserService {

    @Autowired
    private UserDao dao;

    public User getById(long id) {
        return dao.getById(id);
    }

    public List<User> getAll() {
        return dao.getAll();
    }

    public long save(User user) {
        if (user.getId() > 0) {
            long savedId = dao.insert(user);
            user.setId(savedId);
            return savedId;
        } else {
            return dao.update(user);
        }
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public UserDao getDao() {
        return dao;
    }

    public void setDao(UserDao dao) {
        this.dao = dao;
    }
}

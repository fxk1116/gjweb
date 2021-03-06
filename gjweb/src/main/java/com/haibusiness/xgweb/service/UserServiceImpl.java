package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.dao.UserDao;
import com.haibusiness.xgweb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService,UserDetailsService {
    @Autowired
    private UserDao userDao;



    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }


    @Override
    public void removeUser(Long id) {
        userDao.delete(id);
    }


    @Override
    public void removeUsersInBatch(List<User> users) {
        userDao.deleteInBatch(users);
    }


    @Override
    public User updateUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public List<User> listUsers() {
        return userDao.findAll();
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        // 模糊查询
        name = "%" + name + "%";
        Page<User> users = userDao.findByNameLike(name, pageable);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }
}

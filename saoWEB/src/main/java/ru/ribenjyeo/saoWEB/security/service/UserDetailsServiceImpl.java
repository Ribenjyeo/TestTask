package ru.ribenjyeo.saoWEB.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.ribenjyeo.saoWEB.execption.ApiException;
import ru.ribenjyeo.saoWEB.repo.UserRepo;
import ru.ribenjyeo.saoWEB.model.domain.User;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo
                .findByUsername (username)
                .orElseThrow (() -> new UsernameNotFoundException ("User not found with username: " + username));
        return UserDetailsImpl.build (user);
    }

    @Transactional
    public UserDetailsImpl loadUserById(Long id) {
        User user = userRepo.findById (id).orElseThrow (
                () -> new ApiException ("User not found ", HttpStatus.NOT_FOUND)
        );

        return UserDetailsImpl.build (user);
    }


}

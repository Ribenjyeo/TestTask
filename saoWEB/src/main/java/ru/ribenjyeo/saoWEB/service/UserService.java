package ru.ribenjyeo.saoWEB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ribenjyeo.saoWEB.model.domain.User;
import ru.ribenjyeo.saoWEB.model.dto.UserDto;
import ru.ribenjyeo.saoWEB.execption.ApiException;
import ru.ribenjyeo.saoWEB.repo.UserRepo;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerAccount(UserDto.Create userDto) {
        userRepo.findByUsername (userDto.getUsername ())
                .ifPresent (user -> {
                    throw new ApiException ("Login Already exists.", HttpStatus.BAD_REQUEST);
                });
        User user = this.createUser (userDto.getUsername (), userDto.getPassword ());
        return user;
    }

    public User createUser(String username, String password) {
        User newUser = new User ();
        newUser.setPassword (passwordEncoder.encode (password));
        newUser.setUsername (username);
        userRepo.save (newUser);
        return newUser;
    }

    public void deleteUser(Long userId) {
        userRepo.findOneById (userId).ifPresent (user -> {
            userRepo.deleteById (userId);
        });
    }

}

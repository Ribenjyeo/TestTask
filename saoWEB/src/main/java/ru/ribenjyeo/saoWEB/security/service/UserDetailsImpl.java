package ru.ribenjyeo.saoWEB.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.ribenjyeo.saoWEB.model.domain.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private List<String> animals;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;

    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList (new SimpleGrantedAuthority ("USER"));

        return new UserDetailsImpl (
                user.getId (),
                user.getUsername (),
                user.getPassword (),
                authorities
        );
    }

    public static UserDetailsImpl build(User user, List<String> animals) {
        UserDetailsImpl userDetails = UserDetailsImpl.build (user);
        userDetails.setAnimals (animals);
        return userDetails;
    }

    public Long getId() {
        return id;
    }

    public List<String> getAnimals() {
        return animals;
    }

    public void setAnimals(List<String> animals) {
        this.animals = animals;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package ru.ribenjyeo.saoWEB.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
@Getter
@Setter
public class User {

    // ============================================= //
    //                     FIELDS                    //
    // ============================================= //

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 30)
    private String username;

    @JsonIgnore
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = true)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Animal> animals = new ArrayList<> ();

    // ============================================= //
    //                  CONSTRUCTOR                  //
    // ============================================= //

    public User() {
    }

    private int attempts;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass () != o.getClass ()) {
            return false;
        }

        User user = (User) o;
        return !(user.getId () == null || getId () == null) && Objects.equals (getId (), user.getId ());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode (getId ());
    }
}

package ru.ribenjyeo.saoWEB.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "animals",
        uniqueConstraints = {
            @UniqueConstraint (columnNames = "nickname")
        })
@Getter
@Setter
public class Animal {

    // ============================================= //
    //                     FIELDS                    //
    // ============================================= //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EAnimal type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String gender;

    @Column(name = "nickname", nullable = false)
    @Length(min = 1)
    private String nickname;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @NotNull
    private User user;

    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    private String createdBy;

    // ============================================= //
    //                  CONSTRUCTOR                  //
    // ============================================= //

    public Animal() {
    }

    public Animal(Long id) {
        this.id = id;
    }

    public Animal(EAnimal type, LocalDate birthday, String gender, String nickname) {
        this.type = type;
        this.birthday = birthday;
        this.gender = gender;
        this.nickname = nickname;
    }
}


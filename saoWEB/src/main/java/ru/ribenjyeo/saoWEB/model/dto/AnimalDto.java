package ru.ribenjyeo.saoWEB.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.ribenjyeo.saoWEB.model.domain.Animal;
import ru.ribenjyeo.saoWEB.model.domain.EAnimal;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class AnimalDto {

    // ============================================= //
    //                     FIELDS                    //
    // ============================================= //

    private Long id;
    private String nickname;
    private String gender;
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private EAnimal type;
    private String createdBy;

    // ============================================= //
    //                  CONSTRUCTOR                  //
    // ============================================= //

    public AnimalDto() {
    }

    public AnimalDto(Animal animal) {
        this.id = animal.getId ();
        this.nickname = animal.getNickname ();
        this.gender = animal.getGender ();
        this.createdBy = animal.getCreatedBy ();
        this.birthday = animal.getBirthday ();
        this.type = animal.getType ();
        this.userId = animal.getUser ().getId ();

    }

}

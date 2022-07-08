package ru.ribenjyeo.saoWEB.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ribenjyeo.saoWEB.execption.BadRequestException;
import ru.ribenjyeo.saoWEB.model.domain.Animal;
import ru.ribenjyeo.saoWEB.model.domain.User;
import ru.ribenjyeo.saoWEB.model.dto.AnimalDto;
import ru.ribenjyeo.saoWEB.repo.AnimalRepo;
import ru.ribenjyeo.saoWEB.security.SecurityUtil;
import ru.ribenjyeo.saoWEB.security.service.UserDetailsImpl;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AnimalService {

    @Autowired
    private AnimalRepo animalRepo;

    public Optional<Animal> findForId(Long id) {
        return animalRepo.findById (id);
    }

    public AnimalDto addAnimal(AnimalDto animalDto, UserDetailsImpl userDetails) {
        Animal newAnimal = new Animal ();
        newAnimal.setNickname (animalDto.getNickname ());
        newAnimal.setBirthday (animalDto.getBirthday ());
        newAnimal.setGender (animalDto.getGender ());
        newAnimal.setType (animalDto.getType ());
        newAnimal.setCreatedBy (userDetails.getUsername ());
        newAnimal.setUser (new User (userDetails.getId ())); // temporary code
        return new AnimalDto (animalRepo.saveAndFlush (newAnimal));
    }

    public Optional<AnimalDto> editAnimal(AnimalDto animalDto, Long id) {
        return this.findForId (id)
                .map (p -> {
                    if (!p.getUser ().getId ().equals (SecurityUtil.getCurrentUserLogin ().get ().getId ())) {
                        throw new BadRequestException ("It's not a writer.");
                    }
                    p.setNickname (animalDto.getNickname ());
                    p.setBirthday (animalDto.getBirthday ());
                    p.setGender (animalDto.getGender ());
                    return p;
                })
                .map (AnimalDto::new);
    }

    public void deleteAnimal(Long id) {
        animalRepo.findById (id).ifPresent (p -> {
            if (p.getUser ().getId () != SecurityUtil.getCurrentUserLogin ().get ().getId ()) {
                throw new BadRequestException ("It's not a writer.");
            }
            animalRepo.delete (p);
        });
    }

}

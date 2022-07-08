package ru.ribenjyeo.saoWEB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ribenjyeo.saoWEB.execption.ApiException;
import ru.ribenjyeo.saoWEB.model.domain.Animal;
import ru.ribenjyeo.saoWEB.model.dto.AnimalDto;
import ru.ribenjyeo.saoWEB.repo.AnimalRepo;
import ru.ribenjyeo.saoWEB.security.CurrentUser;
import ru.ribenjyeo.saoWEB.security.service.UserDetailsImpl;
import ru.ribenjyeo.saoWEB.service.AnimalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animal")
public class AnimalControllers {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalRepo animalRepo;

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> getAnimal(@PathVariable Long id) {
        Animal animal = animalService.findForId (id).orElseThrow (() ->
                new ApiException ("Animal does not exist", HttpStatus.NOT_FOUND));
        return new ResponseEntity<> (new AnimalDto (animal), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<?> getAnimals(@CurrentUser UserDetailsImpl userDetails) {
        return animalRepo.findAnimalByCreatedBy (userDetails.getUsername ());
    }

    @PostMapping("/add")
    public ResponseEntity<AnimalDto> addAnimal(@RequestBody AnimalDto animalDto,
                                               @CurrentUser UserDetailsImpl userDetails) {
        if (animalRepo.existsByNickname (animalDto.getNickname ())) {
            throw new ApiException ("Nickname already in use", HttpStatus.CONFLICT);
        } else {
            AnimalDto returnAnimal = animalService.addAnimal (animalDto, userDetails);
            return new ResponseEntity<AnimalDto> (returnAnimal, HttpStatus.CREATED);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AnimalDto> editAnimal(@PathVariable Long id, @RequestBody AnimalDto animalDto) {
        Optional<Animal> animal = animalService.findForId (id);
        if (!animal.isPresent ()) {
            throw new ApiException ("Animal cloud not be found", HttpStatus.NOT_FOUND);
        }
        Optional<AnimalDto> returnAnimal = animalService.editAnimal (animalDto, id);
        return returnAnimal.map (response -> new ResponseEntity<AnimalDto> (response, HttpStatus.OK))
                .orElse (new ResponseEntity<AnimalDto> (HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        if (id == null) {
            throw new ApiException ("Animal id cannot null", HttpStatus.NOT_FOUND);
        } else {
            animalService.deleteAnimal (id);
            return new ResponseEntity<> (HttpStatus.OK);
        }
    }


}

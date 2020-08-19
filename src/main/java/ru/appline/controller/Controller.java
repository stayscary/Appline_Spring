package ru.appline.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    public String createPet(@RequestBody Pet pet) {
        int id = Integer.parseInt(newId.toString());
        petModel.add(pet, newId.getAndIncrement());
        return gson.toJson("Поздравляем! Вы создали нового питомца! Его id = " + id);
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }

    @PutMapping(value = "/editPet", consumes = "application/json", produces = "application/json")
    public String editPet(@RequestBody Map<String,String> pet) {
        int id = Integer.parseInt(pet.get("id"));
        String name = pet.get("name");
        String type = pet.get("type");
        int age = Integer.parseInt(pet.get("age"));
        petModel.editPet(id, name, type, age);
        return gson.toJson("Питомец с id = " + id + " изменён!");
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public String deletePet(@RequestBody Map<String, Integer> pet){
        petModel.deletePet(pet.get("id"));
        return gson.toJson("Питомец с id = " + pet.get("id") + " удалён :(");
    }
}

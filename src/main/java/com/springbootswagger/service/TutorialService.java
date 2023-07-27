package com.springbootswagger.service;

import com.springbootswagger.model.Tutorial;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TutorialService is a service component that implement CRUD methods and custom finder methods
 * Main logic to operate with Data List: create list, add/remove/get elements of list
 * Data structure:
 *  List of data type is Tutorial
 */

@Schema(description = "Tutorial Service are actions on the Tutorial data class")
@Service
public class TutorialService {

    static List<Tutorial> tutorials = new ArrayList<>();

    @Schema(description = "Unique value of tutorial", example = "1000")
    static long id = 0;

    public List<Tutorial> findAll(){
        return tutorials;
    }

    public List<Tutorial> getTutorialByTitle(String title){
        return tutorials.stream().filter(elem -> elem.getTitle().contains(title)).collect(Collectors.toList());
    }

    public Tutorial getTutorialById(long id){
        return tutorials.stream().filter(elem -> elem.getId() == id).findAny().orElse(null);
    }

    public List<Tutorial> findByPublished(boolean isPublished){
        return tutorials.stream().filter(elem -> elem.isPublished() == isPublished).collect(Collectors.toList());
    }

    public Tutorial save(Tutorial tutorial){
        //update Tutorial by id
        if(tutorial.getId() != 0){
            for(int idx=0; idx<tutorials.size(); idx++){
                if(tutorial.getId() == tutorials.get(idx).getId()){
                    tutorials.set(idx, tutorial);
                    break;
                }
            }
            return tutorial;
        }
        //create Tutorial
        System.out.println(tutorial);
        long _id = ++id;
        System.out.println(_id);
        tutorial.setId(_id);
        tutorials.add(tutorial);
        return tutorial;
    }

    public void deleteById(long id){
        tutorials.removeIf(elem -> elem.getId() == id);
    }

    public void deleteAll(){
        tutorials.removeAll(tutorials);
    }
}

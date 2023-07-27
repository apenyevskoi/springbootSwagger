package com.springbootswagger.controller;

import com.springbootswagger.model.Tutorial;
import com.springbootswagger.service.TutorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring Controller Class
 * Describes interaction with client through URI and http methods
 * URL http://localhost:8080
 * URI /api
 * GET POST /tutorials
 * GET /tutorials/{title} (part of title contains in the title)
 * GET PUT DELETE /tutorials/{id}
 * GET /tutorials/published
 */

/**
 * SPRING DESCRIPTION
 *
 * TutorialController is a RestController which has request mapping methods for RESTful requests such as:
 * getAllTutorials, createTutorial, updateTutorial, deleteTutorial, findByPublished…
 */

/**
 * CORS (Cross-Origin Resource Sharing) allows a webpage to request additional resources into the browser from other
 * domains such as API data
 */
// RestController creates Restful controllers.
// It helps to convert the response to JSON or XML.
//    src
//            ├── main
//            │   ├── java
//            │   │   └── com
//            │   │       └── zetcode
//            │   │           ├── Application.java
//            │   │           ├── controller
//            │   │           │   └── MyController.java
//            │   │           ├── model
//            │   │           │   └── City.java
//            │   │           └── service
//            │   │               ├── CityService.java
//            │   │               └── ICityService.java
//            │   └── resources
//            │       └── application.properties
//            └── test
@RestController
// CrossOrigin allows controlling the CORS configuration at the "method level". @RestController or @Controller
// points out that this class uses data, not models.
@CrossOrigin(origins = "http://localhost:8080")
// RequestMapping sets what method to use with request
// It is used to map web requests onto specific handler classes or methods.
// Options: name, value, method, headers
// So, the URL will be http://localhost:8081/api
@RequestMapping("/api")
@Tag(name = "API Tutorial", description = "Description of API tutorial")
public class TutorialController {

    // Autowired asks Spring to inject value to this variable/field
    @Autowired
    TutorialService tutorialService;

    @Operation(
            summary = "Get all tutorials or by containing request parameter in the title",
            description = "Get tutorials in JSON format",
            tags = {"Get all tutorials", "Get by title"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    // GetMapping is to map HTTP GET request to method.
    // GetMapping processes GET method using URI. Similar RequestMapping
    // RequestParam is used to allow the method to get parameters of http-request
    // http://localhost:8080/getByName/name=Ivan request -> methodName(@RequestParam("name") String name)
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(
            @RequestParam(required = false)@Parameter(description = "Title part to search in titles") String title2
    ){
        try{
            List<Tutorial> tutorials = new ArrayList<>();

            if(title2 == null)
                tutorialService.findAll().forEach(tutorials::add);
            else {
                tutorialService.getTutorialByTitle(title2).forEach(tutorials::add);
            }

            if(tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get tutorial by ID",
            description = "Get tutorial using ID as part of URI. Responce in JSON format",
            tags = {"Get By ID"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    // @PathVariable is to get specific part of URI.
    // URI: http://localhost:8080/tutorial/23, so get id=23
    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(
            @PathVariable("id")@Parameter(description = "Tutorial ID") long id
    ){
        Tutorial tutorial = tutorialService.getTutorialById(id);
        if (tutorial != null)
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Add tutorial data",
            description = "Add tutorial. Responce in JSON format",
            tags = {"Add tutorial"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    //@RequestBody helps to convert request body to data model, ex. Tutorial model
    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(
            @RequestBody@Parameter(description = "Tutorial data adding") Tutorial tutorial
    ){
        Optional<Tutorial> test = Optional.ofNullable(tutorial);
        if(test.isPresent()){
            Tutorial _tutorial = tutorialService.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(_tutorial, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Change tutorial data using ID",
            description = "Change tutorial using ID. Responce in JSON format",
            tags = {"Change tutorial"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(
            @PathVariable("id")@Parameter(description = "Tutorial ID to insert changes") long id,
            @RequestBody@Parameter(description = "Data to insert into tutorial") Tutorial tutorial
    ){
        Tutorial _tutorial = tutorialService.getTutorialById(id);

        if(_tutorial != null) {
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialService.save(_tutorial), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Delete tutorial using ID",
            description = "Delete tutorial using ID. Responce in JSON format",
            tags = {"Delete tutorial"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(
            @PathVariable("id")@Parameter(description = "Tutorial ID to delete") long id
    ){
        try{
            tutorialService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Filter tutorials by published field",
            description = "Filter tutorial. Responce is the list of tutorials in JSON format",
            tags = {"Filter tutorials by published"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Tutorial.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished(){
        try{
            List<Tutorial> tutorialList = tutorialService.findByPublished(true);
            if(tutorialList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutorialList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}

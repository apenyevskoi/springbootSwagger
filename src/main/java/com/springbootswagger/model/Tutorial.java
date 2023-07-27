package com.springbootswagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Tutorial is data model class
 * Tutorial: id, title, description, published
 * Lombok is used
 */
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Data model of Tutorial")
public class Tutorial {
    @Schema(description = "Unique number", example = "20")
    private long id = 0;

    @Schema(description = "Title of tutorial", example = "Example of title")
    private String title;

    @Schema(description = "Description of tutorial", example = "Example of description")
    private String description;

    @Schema(description = "Boolean value of publishing", example = "false")
    private boolean published;

    public Tutorial(){}

    public Tutorial(String title, String description, boolean published){
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public boolean isPublished(){
        return published;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }
}
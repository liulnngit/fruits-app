package com.example.fruits.dto;

import com.example.fruits.model.emoji.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "fruits", itemRelation = "fruit")
public class FruitsRepresentationModel extends RepresentationModel<FruitsRepresentationModel> {
    private Integer id;
    private String name;
    private String emoji;
    private Nutrition nutritions;
}

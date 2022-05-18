package com.example.fruits.controller.assembler;

import com.example.fruits.controller.FruitsController;
import com.example.fruits.model.emoji.FruitWithEmoji;
import com.example.fruits.dto.FruitsRepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FruitsModelAssembler
    extends RepresentationModelAssemblerSupport<FruitWithEmoji, FruitsRepresentationModel> {

    public FruitsModelAssembler() {
        super(FruitsController.class, FruitsRepresentationModel.class);
    }

    @Override
    public FruitsRepresentationModel toModel(FruitWithEmoji entity) {
        FruitsRepresentationModel model = instantiateModel(entity);
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setNutritions(entity.getNutritions());
        model.setEmoji(entity.getEmoji());

        return model;
    }

}

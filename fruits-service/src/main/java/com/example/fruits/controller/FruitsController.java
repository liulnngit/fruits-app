package com.example.fruits.controller;

import com.example.fruits.controller.assembler.FruitsModelAssembler;
import com.example.fruits.dto.FruitsRepresentationModel;
import com.example.fruits.model.NutritionType;
import com.example.fruits.model.emoji.FruitWithEmoji;
import com.example.fruits.service.FruitsService;
import com.example.fruits.utils.HttpUtils;
import com.example.fruits.utils.PageUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fruits")
public class FruitsController {
    private static final String TOP_COUNT = "3";
    private final FruitsService fruitsService;
    private final PagedResourcesAssembler<FruitWithEmoji> pagedResourcesAssembler;
    private final FruitsModelAssembler fruitsModelAssembler;

    @Autowired
    public FruitsController(FruitsService fruitsService,
                            PagedResourcesAssembler<FruitWithEmoji> pagedResourcesAssembler,
                            FruitsModelAssembler fruitsModelAssembler) {
        this.fruitsService = fruitsService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.fruitsModelAssembler = fruitsModelAssembler;
    }

    @ApiOperation("Fruit")
    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FruitWithEmoji> getFruitWithEmoji(@PathVariable String name) {
        var fruitWithEmoji = fruitsService.getFruitWithEmoji(name);

        return HttpUtils.ok(fruitWithEmoji);
    }

    @ApiOperation("Fruits list")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<FruitsRepresentationModel>> getAll(
        Pageable pageable
    ) {
        var page = fruitsService.getAllFruitsWithEmojis(pageable);
        var pagedModel = PageUtils.toPagedModel(
            page,
            FruitsRepresentationModel.class,
            pagedResourcesAssembler,
            fruitsModelAssembler
        );

        return HttpUtils.ok(pagedModel);
    }

    @ApiOperation("Fruits Top list")
    @GetMapping(value = "/topBy/{nutritionType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<FruitsRepresentationModel>> getTop(
        @PathVariable("nutritionType") NutritionType nutritionType,
        @RequestParam(defaultValue = TOP_COUNT) int count,
        Pageable pageable
    ) {
        var page = fruitsService.getTopFruitsWithEmojis(pageable, nutritionType, count);
        var pagedModel = PageUtils.toPagedModel(
            page,
            FruitsRepresentationModel.class,
            pagedResourcesAssembler,
            fruitsModelAssembler
        );

        return HttpUtils.ok(pagedModel);
    }

}

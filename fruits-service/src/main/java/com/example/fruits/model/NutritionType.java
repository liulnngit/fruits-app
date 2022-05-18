package com.example.fruits.model;

import com.example.fruits.model.emoji.FruitWithEmoji;

public enum NutritionType {
    CARBOHYDRATES {
        @Override
        public Double getNutrition(FruitWithEmoji fruit) {
            return fruit.getNutritions().getCarbohydrates();
        }
    },
    PROTEIN {
        @Override
        public Double getNutrition(FruitWithEmoji fruit) {
            return fruit.getNutritions().getProtein();
        }
    },
    FAT {
        @Override
        public Double getNutrition(FruitWithEmoji fruit) {
            return fruit.getNutritions().getFat();
        }
    },
    CALORIES {
        @Override
        public Double getNutrition(FruitWithEmoji fruit) {
            return fruit.getNutritions().getCalories().doubleValue();
        }
    },
    SUGAR {
        @Override
        public Double getNutrition(FruitWithEmoji fruit) {
            return fruit.getNutritions().getSugar();
        }
    };

    public abstract Double getNutrition(FruitWithEmoji fruit);
}

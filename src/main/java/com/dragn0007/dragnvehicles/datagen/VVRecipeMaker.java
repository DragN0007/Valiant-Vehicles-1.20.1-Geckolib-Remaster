package com.dragn0007.dragnvehicles.datagen;

import com.dragn0007.dragnvehicles.item.VVItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class VVRecipeMaker extends RecipeProvider implements IConditionBuilder {
    public VVRecipeMaker(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.CAR_SPAWN_EGG.get())
                .define('A', VVItems.CAR_BODY.get())
                .define('B', VVItems.WHEEL.get())
                .pattern(" A ")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_body", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(VVItems.CAR_BODY.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.CLASSIC_SPAWN_EGG.get())
                .define('A', VVItems.CLASSIC_BODY.get())
                .define('B', VVItems.WHEEL.get())
                .pattern(" A ")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_body", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(VVItems.CLASSIC_BODY.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.TRUCK_SPAWN_EGG.get())
                .define('A', VVItems.TRUCK_BODY.get())
                .define('B', VVItems.WHEEL.get())
                .pattern(" A ")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_body", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(VVItems.TRUCK_BODY.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.SUV_SPAWN_EGG.get())
                .define('A', VVItems.SUV_BODY.get())
                .define('B', VVItems.WHEEL.get())
                .pattern(" A ")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_body", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(VVItems.SUV_BODY.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.SPORTCAR_SPAWN_EGG.get())
                .define('A', VVItems.SPORT_CAR_BODY.get())
                .define('B', VVItems.WHEEL.get())
                .pattern(" A ")
                .pattern("B B")
                .pattern("B B")
                .unlockedBy("has_body", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(VVItems.SPORT_CAR_BODY.get()).build()))
                .save(pFinishedRecipeConsumer);

//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.MOTORCYCLE_SPAWN_EGG.get())
//                .define('A', VVItems.MOTORCYCLE_BODY.get())
//                .define('B', VVItems.SPORTS_WHEEL.get())
//                .pattern(" A ")
//                .pattern("B B")
//                .unlockedBy("has_body", inventoryTrigger(ItemPredicate.Builder.item()
//                        .of(VVItems.MOTORCYCLE_BODY.get()).build()))
//                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.WHEEL.get())
                .define('A', Items.DRIED_KELP_BLOCK)
                .define('B', Items.IRON_INGOT)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("has_dried_kelp_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.DRIED_KELP_BLOCK).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.SPORTS_WHEEL.get())
                .define('A', Items.DRIED_KELP_BLOCK)
                .define('B', Items.IRON_INGOT)
                .define('C', Items.GOLD_INGOT)
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .unlockedBy("has_dried_kelp_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.DRIED_KELP_BLOCK).build()))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.CAR_BODY.get())
                .define('A', Items.IRON_BLOCK)
                .define('B', Items.CHEST)
                .define('C', Items.GLASS_PANE)
                .define('D', VVItems.ENGINE.get())
                .pattern("ACA")
                .pattern("DAB")
                .pattern("A A")
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.CLASSIC_BODY.get())
                .define('A', Items.IRON_BLOCK)
                .define('B', Items.CHEST)
                .define('C', Items.GLASS_PANE)
                .define('D', VVItems.ENGINE.get())
                .pattern("DCB")
                .pattern("A A")
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.TRUCK_BODY.get())
                .define('A', Items.IRON_BLOCK)
                .define('B', Items.CHEST)
                .define('C', Items.GLASS_PANE)
                .define('D', VVItems.ENGINE.get())
                .pattern("ACA")
                .pattern("DAB")
                .pattern("AAA")
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.SUV_BODY.get())
                .define('A', Items.IRON_BLOCK)
                .define('B', Items.CHEST)
                .define('C', Items.GLASS_PANE)
                .define('D', VVItems.ENGINE.get())
                .pattern("ACA")
                .pattern("DBB")
                .pattern("AAA")
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.SPORT_CAR_BODY.get())
                .define('A', Items.IRON_BLOCK)
                .define('B', Items.CHEST)
                .define('C', Items.GLASS_PANE)
                .define('D', VVItems.ENGINE.get())
                .pattern("DCB")
                .pattern("A A")
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.MOTORCYCLE_BODY.get())
                .define('A', Items.IRON_BLOCK)
                .define('B', Items.CHEST)
                .define('C', Items.GLASS_PANE)
                .define('D', VVItems.SPORTS_ENGINE.get())
                .pattern("B C")
                .pattern("ADA")
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.ENGINE.get())
                .define('A', Items.IRON_INGOT)
                .define('B', Items.REDSTONE)
                .define('C', Items.QUARTZ)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("has_iron_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VVItems.SPORTS_ENGINE.get())
                .define('A', Items.GOLD_INGOT)
                .define('B', Items.REDSTONE)
                .define('C', Items.QUARTZ)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("ABA")
                .unlockedBy("has_gold_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.GOLD_INGOT).build()))
                .save(pFinishedRecipeConsumer);
    }
}
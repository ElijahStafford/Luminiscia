package net.elijahandpaige.luminiscia;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoOpFeature;

public class Luminiscia implements ModInitializer {
    public static final String MOD_ID = "luminiscia";

    @Override
    public void onInitialize() {
        LuminisciaBlocks.registerAll();
        LuminisciaItems.registerAll();
        registerFuels();
        registerFlammableBlock();
        Registry.register(Registries.FEATURE, new Identifier(MOD_ID, "shimmerwood_tree"), new ShimmerwoodTree(DefaultFeatureConfig.CODEC));
    }


    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(LuminisciaItems.SHIMMERWOOD_LOG, 500);

    }

    private static void registerFlammableBlock() {
        FlammableBlockRegistry flamInstance = FlammableBlockRegistry.getDefaultInstance();

        flamInstance.add(LuminisciaBlocks.SHIMMERWOOD_LOG,5,5);

    }
}

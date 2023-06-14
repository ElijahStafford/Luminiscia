package net.elijahandpaige.luminiscia;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;

import static net.minecraft.server.command.CommandManager.literal;

public class Luminiscia implements ModInitializer {
    public static final String MOD_ID = "luminiscia";

    @Override
    public void onInitialize() {
        // Register blocks and items
        LuminisciaBlocks.registerAll();
        LuminisciaItems.registerAll();
        registerFuels();
        registerFlammableBlock();

        // Register terrain generation objects
        Registry.register(Registries.FEATURE, ShimmerwoodTree.ID, ShimmerwoodTree.FEATURE);
    }


    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(LuminisciaItems.SHIMMERWOOD_LOG, 300);
        registry.add(LuminisciaItems.SHIMMERWOOD_PLANKS, 300);
        registry.add(LuminisciaItems.SHIMMERWOOD_SLAB, 150);
        registry.add(LuminisciaItems.SHIMMERWOOD_STAIRS , 300);
    }

    private static void registerFlammableBlock() {
        FlammableBlockRegistry flamInstance = FlammableBlockRegistry.getDefaultInstance();

        flamInstance.add(LuminisciaBlocks.SHIMMERWOOD_LOG, 5, 5);
        flamInstance.add(LuminisciaBlocks.SHIMMERWOOD_PLANKS, 20, 5);
        flamInstance.add(LuminisciaBlocks.SHIMMERWOOD_SLAB, 20, 5);
        flamInstance.add(LuminisciaBlocks.SHIMMERWOOD_STAIRS, 20, 5);
    }
}

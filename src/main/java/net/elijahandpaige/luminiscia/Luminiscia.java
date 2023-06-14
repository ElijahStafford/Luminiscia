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

    public static final Identifier SHIMMERWOOD_TREE_ID = new Identifier(MOD_ID, "shimmerwood_tree");
    public static Feature<DefaultFeatureConfig> SHIMMERWOOD_TREE = new ShimmerwoodTree(DefaultFeatureConfig.CODEC);
    public static ConfiguredFeature<DefaultFeatureConfig, ShimmerwoodTree> SHIMMERWOOD_TREE_CONFIGURED = new ConfiguredFeature<>(
            (ShimmerwoodTree) SHIMMERWOOD_TREE,
            new DefaultFeatureConfig()
    );

    @Override
    public void onInitialize() {
        LuminisciaBlocks.registerAll();
        LuminisciaItems.registerAll();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("test")
                    .executes(context -> {
                        var source = context.getSource();
                        ServerWorld serverWorld = source.getWorld();
                        var pos = BlockPos.ofFloored(source.getPosition());
                        SHIMMERWOOD_TREE_CONFIGURED.generate(serverWorld, serverWorld.getChunkManager().getChunkGenerator(), serverWorld.getRandom(), pos);
                        return 1;
                    }));
        });

        Registry.register(Registries.FEATURE, SHIMMERWOOD_TREE_ID, SHIMMERWOOD_TREE);
        // Registry.register(RegistryKeys.CONFIGURED_FEATURE, SHIMMERWOOD_TREE_ID, SHIMMERWOOD_TREE_CONFIGURED);

        registerFuels();
        registerFlammableBlock();
    }


    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(LuminisciaItems.SHIMMERWOOD_LOG, 500);

    }

    private static void registerFlammableBlock() {
        FlammableBlockRegistry flamInstance = FlammableBlockRegistry.getDefaultInstance();

        flamInstance.add(LuminisciaBlocks.SHIMMERWOOD_LOG, 5, 5);

    }
}

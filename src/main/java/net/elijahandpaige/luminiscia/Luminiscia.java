package net.elijahandpaige.luminiscia;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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
//        ShimmerwoodTree.register();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("test")
                    .executes(context -> {
                        var source = context.getSource();
                        ServerWorld serverWorld = source.getWorld();
                        var pos = BlockPos.ofFloored(source.getPosition());
                        var block = BlockStateArgumentType.getBlockState(context, "pos");
                        block.setBlockState(serverWorld, pos, 2);
                        serverWorld.updateNeighbors(pos, Blocks.CHORUS_PLANT);
                        SHIMMERWOOD_TREE_CONFIGURED.generate(serverWorld, serverWorld.getChunkManager().getChunkGenerator(), serverWorld.getRandom(), pos);
                        source.sendFeedback(Text.literal("hi loser"), false);
                        return 1;
                    }));
        });

        Registry.register(Registries.FEATURE, SHIMMERWOOD_TREE_ID, SHIMMERWOOD_TREE);
        // Registry.register(RegistryKeys.CONFIGURED_FEATURE, SHIMMERWOOD_TREE_ID, SHIMMERWOOD_TREE_CONFIGURED);
    }

//    public static Vec3i splinePositions(ArrayList<Vec3i> positions, double position) {
//        if (positions.size() <= 1)
//            return positions.get(0);
//
//
//    }
}

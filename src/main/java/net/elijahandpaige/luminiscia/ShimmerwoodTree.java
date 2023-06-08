package net.elijahandpaige.luminiscia;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import static net.elijahandpaige.luminiscia.Luminiscia.MOD_ID;

public class ShimmerwoodTree extends Feature<DefaultFeatureConfig> {

    public ShimmerwoodTree(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        BlockPos testPos = origin;

        for (int i = 0; i < 6; i++) {
            // create a simple pillar of blocks
            world.setBlockState(testPos, Blocks.CHORUS_PLANT.getDefaultState(), 0x10);
            testPos = testPos.up();

            // ensure we don't try to place blocks outside the world
            if (testPos.getY() >= world.getTopY()) break;
        }
        return true;
    }

    public static void register() {
    }
}

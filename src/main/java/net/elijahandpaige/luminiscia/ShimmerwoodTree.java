package net.elijahandpaige.luminiscia;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;

import static net.elijahandpaige.luminiscia.Luminiscia.MOD_ID;

public class ShimmerwoodTree extends Feature<DefaultFeatureConfig> {

    public ShimmerwoodTree(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        var pos = new ArrayList<Vec3i>();
        pos.add(new Vec3i(0,6,-4));
        pos.add(new Vec3i(2,6,-2));
        pos.add(new Vec3i(0,6,2));
        pos.add(new Vec3i(1,8,7));

        for (float i = 0; i <= 1; i += 1f / 100) {
            BlockPos position = new BlockPos(CatmullRomSpline.getPoint(pos, i));
            position = position.add(origin);
            setBlockState(world, position, Blocks.REDSTONE_BLOCK.getDefaultState());
        }

        for (var position : pos) {
            setBlockState(world, new BlockPos(position.add(origin)), Blocks.DIAMOND_BLOCK.getDefaultState());
        }

        return true;
    }
}

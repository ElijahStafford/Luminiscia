package net.elijahandpaige.luminiscia;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Random;

import static net.elijahandpaige.luminiscia.Luminiscia.MOD_ID;

public class ShimmerwoodTree extends Feature<DefaultFeatureConfig> {

    public ShimmerwoodTree(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        var currPos = new Vec3d(origin.getX(), origin.getY(), origin.getZ());

        var pos = new ArrayList<Vec3i>();
        var rand = new Random();
        var direction = new Vec3d(0, 0, 10);

        for (int i = 0; i <= 5; i++) {
            var rotation = new Vec2f(-rand.nextInt(40) - 40, rand.nextInt(360));

            pos.add(new Vec3i(
                    Math.round((float)currPos.x),
                    Math.round((float)currPos.y),
                    Math.round((float)currPos.z)
            ));

            currPos = rotateVector(direction, rotation, currPos);
        }

        for (float i = 0; i <= 1; i += 1f / 100) {
            BlockPos position = new BlockPos(CatmullRomSpline.getPoint(pos, i));
            setBlockState(world, position, Blocks.REDSTONE_BLOCK.getDefaultState());
        }

        for (var position : pos) {
            setBlockState(world, new BlockPos(position), Blocks.DIAMOND_BLOCK.getDefaultState());
        }

        return true;
    }

    public Vec3d rotateVector(Vec3d vector, Vec2f rotation) {
        return rotateVector(vector, rotation, new Vec3d(0,0,0));
    }

    public Vec3d rotateVector(Vec3d vector, Vec2f rotation, Vec3d anchor) {
        float f = MathHelper.cos((rotation.y + 90.0F) * 0.017453292F);
        float g = MathHelper.sin((rotation.y + 90.0F) * 0.017453292F);
        float h = MathHelper.cos(-rotation.x * 0.017453292F);
        float i = MathHelper.sin(-rotation.x * 0.017453292F);
        float j = MathHelper.cos((-rotation.x + 90.0F) * 0.017453292F);
        float k = MathHelper.sin((-rotation.x + 90.0F) * 0.017453292F);
        Vec3d vec3d2 = new Vec3d((double)(f * h), (double)i, (double)(g * h));
        Vec3d vec3d3 = new Vec3d((double)(f * j), (double)k, (double)(g * j));
        Vec3d vec3d4 = vec3d2.crossProduct(vec3d3).multiply(-1.0);
        double d = vec3d2.x * vector.z + vec3d3.x * vector.y + vec3d4.x * vector.x;
        double e = vec3d2.y * vector.z + vec3d3.y * vector.y + vec3d4.y * vector.x;
        double l = vec3d2.z * vector.z + vec3d3.z * vector.y + vec3d4.z * vector.x;
        return new Vec3d(anchor.x + d, anchor.y + e, anchor.z + l);
    }
}

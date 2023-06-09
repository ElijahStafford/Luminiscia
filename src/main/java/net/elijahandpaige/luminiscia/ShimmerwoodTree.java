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
        var helix = new ArrayList<Vec3i>();
        var rand = new Random();
        var direction = new Vec3d(0, 0, 10);

        var clockwise = rand.nextBoolean() ? 1 : -1;
        var totalRot = rand.nextInt(360);
        var steps = 5;

        for (int i = 0; i <= steps; i++) {
            var fraction = i / (float)steps;
            var centerFract = Math.max(1 - Math.abs(0.5 - (0.2 + fraction * 0.8)) * 2, 0);

            totalRot += clockwise * (rand.nextInt(40) + 80);
            totalRot %= 360;
            var rotation = new Vec2f(-rand.nextInt(40) - 30, totalRot);

            pos.add(new Vec3i(
                    Math.round((float) currPos.x),
                    Math.round((float) currPos.y),
                    Math.round((float) currPos.z)
            ));

            var helixPos = rotateVector(new Vec3d(8 * centerFract * clockwise,0,0), rotation, currPos);

            helix.add(new Vec3i(
                    Math.round((float) helixPos.x),
                    Math.round((float) helixPos.y),
                    Math.round((float) helixPos.z)
            ));

            currPos = rotateVector(direction, rotation, currPos);
        }

        drawCurve(world, pos, steps * 5, 6, 2);
        drawCurve(world, helix, steps * 10, 3, 2);

        for (var position : pos) {
            setBlockState(world, new BlockPos(position), Blocks.DIAMOND_BLOCK.getDefaultState());
        }

        return true;
    }

    public void drawCurve(
            StructureWorldAccess world,
            ArrayList<Vec3i> positions,
            int steps,
            int startRadius, // TODO: Map radius to a callback function?
            int endRadius
    ) {

        for (float i = 0; i <= 1; i += 1f / steps) {
            BlockPos position = new BlockPos(CatmullRomSpline.getPoint(positions, i));
            int radius = MathHelper.lerp(i, startRadius, endRadius);

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        var magnitude = Math.sqrt(x * x + y * y + z * z);

                        if (magnitude > radius || magnitude < radius - 1) {
                            continue;
                        }

                        setBlockState(world, position.add(x, y, z), LuminisciaBlocks.SHIMMERWOOD_LOG.getDefaultState());
                    }
                }
            }
        }
    }

    public Vec3d rotateVector(Vec3d vector, Vec2f rotation) {
        return rotateVector(vector, rotation, new Vec3d(0, 0, 0));
    }

    public Vec3d rotateVector(Vec3d vector, Vec2f rotation, Vec3d anchor) {
        float f = MathHelper.cos((rotation.y + 90.0F) * 0.017453292F);
        float g = MathHelper.sin((rotation.y + 90.0F) * 0.017453292F);
        float h = MathHelper.cos(-rotation.x * 0.017453292F);
        float i = MathHelper.sin(-rotation.x * 0.017453292F);
        float j = MathHelper.cos((-rotation.x + 90.0F) * 0.017453292F);
        float k = MathHelper.sin((-rotation.x + 90.0F) * 0.017453292F);
        Vec3d vec3d2 = new Vec3d((double) (f * h), (double) i, (double) (g * h));
        Vec3d vec3d3 = new Vec3d((double) (f * j), (double) k, (double) (g * j));
        Vec3d vec3d4 = vec3d2.crossProduct(vec3d3).multiply(-1.0);
        double d = vec3d2.x * vector.z + vec3d3.x * vector.y + vec3d4.x * vector.x;
        double e = vec3d2.y * vector.z + vec3d3.y * vector.y + vec3d4.y * vector.x;
        double l = vec3d2.z * vector.z + vec3d3.z * vector.y + vec3d4.z * vector.x;
        return new Vec3d(anchor.x + d, anchor.y + e, anchor.z + l);
    }
}

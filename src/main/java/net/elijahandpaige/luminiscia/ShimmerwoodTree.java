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
    public Random rand = new Random();
    public static final Identifier ID = new Identifier(MOD_ID, "shimmerwood_tree");
    public static Feature<DefaultFeatureConfig> FEATURE = new ShimmerwoodTree(DefaultFeatureConfig.CODEC);
    public static ConfiguredFeature<DefaultFeatureConfig, ShimmerwoodTree> CONFIGURED = new ConfiguredFeature<>(
            (ShimmerwoodTree) FEATURE,
            new DefaultFeatureConfig()
    );

    public ShimmerwoodTree(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        final int STEM_PITCH_MIN = -10;
        final int STEM_PITCH_MAX = -70;
        final int STEM_ROTATE_MIN = 40;
        final int STEM_ROTATE_MAX = 120;
        final int STEM_STEPS_MIN = 4;
        final int STEM_STEPS_MAX = 14;

        var steps = rand.nextInt(STEM_STEPS_MIN, STEM_STEPS_MAX);

        StructureWorldAccess world = context.getWorld();
        Vec3i currPos = context.getOrigin();

        var stemPositions = new ArrayList<Vec3i>();
        var helixPositions = new ArrayList<Vec3i>();
        var stemDirectionVector = new Vec3i(0, 0, 10);

        var clockwise = rand.nextBoolean() ? 1 : -1;
        var totalRot = rand.nextInt(360);

        for (int i = 0; i <= steps; i++) {
            float fraction = i / (float)steps;
            float centerFract = Math.max(1 - Math.abs(0.5f - (0.2f + fraction * 0.8f)) * 2, 0);

            totalRot += clockwise * rand.nextInt(STEM_ROTATE_MIN, STEM_ROTATE_MAX);
            totalRot %= 360;

            var rotation = new Vec2f(rand.nextInt(STEM_PITCH_MAX, STEM_PITCH_MIN), totalRot);

            stemPositions.add(currPos);

            int helixOutwardLength = Math.round(8 * centerFract * clockwise);
            var helixPos = rotateVector(new Vec3i(helixOutwardLength,0,0), rotation, currPos);

            helixPositions.add(helixPos);

            if (i == steps) {
                for (int x = 0; x >= -60; x -= 20) {
                    for (int y = 0; y < 360; y += 60 - x) {
                        branch(world, currPos, x, y, 0);
                    }
                }
            }

            currPos = rotateVector(stemDirectionVector, rotation, currPos);
        }

        drawCurve(world, stemPositions, steps * 5, 6, 2);
        drawCurve(world, helixPositions, steps * 10, 3, 2);

        return true;
    }


    public Vec3d vec3ito3d(Vec3i vec) {
        return new Vec3d(vec.getX(), vec.getY(), vec.getZ());
    }

    public Vec3i vec3dTo3i(Vec3d vec) {
        return new Vec3i(
                Math.round((float) vec.x),
                Math.round((float) vec.y),
                Math.round((float) vec.z)
        );
    }

    public Vec3i vec3dTo3i(float x, float y, float z) {
        return new Vec3i(
                Math.round(x),
                Math.round(y),
                Math.round(z)
        );
    }

    public void branch(
            StructureWorldAccess world,
            Vec3i position,
            int rotationX,
            int rotationY,
            int depth
    ) {
        final int MAX_BRANCHES = 3;
        final int ROTATION_NOISE = 60;
        final int MIN_DIST = 6;
        final int MAX_DIST = 15;
        final int MAX_DEPTH = 3;
        final int LEAVES_RADIUS = 3;

        if (depth > MAX_DEPTH) {
            return;
        }

        var branches = rand.nextInt(0, MAX_BRANCHES + 1);

        for (int b = 0; b < branches; b++) {
            var newRotationX = MathHelper.clamp(rotationX + rand.nextInt(-ROTATION_NOISE, ROTATION_NOISE / 2), -90, 90);
            var newRotationY = (rotationY + rand.nextInt(-ROTATION_NOISE, ROTATION_NOISE)) % 360;
            var dist = rand.nextInt(MIN_DIST, MAX_DIST);
            var rotatedVector = rotateVector(new Vec3i(0,0,dist), new Vec2f(newRotationX, newRotationY));

            Vec3i newPos = null;

            int sphereProgress = LEAVES_RADIUS + 2;

            for (float i = 0; i <= dist; i += 0.8) {
                float scalar = i / dist;
                scalar *= (depth + 1) / (float)MAX_DEPTH;

                newPos = vec3dTo3i(
                        rotatedVector.getX() * scalar + position.getX(),
                        rotatedVector.getY() * scalar + position.getY(),
                        rotatedVector.getZ() * scalar + position.getZ()
                );

                sphereProgress++;

                if (sphereProgress > LEAVES_RADIUS + 2 || i > dist - 0.8) {
                    sphereProgress = 0;
                    for (int x = -LEAVES_RADIUS; x <= LEAVES_RADIUS; x++) {
                        for (int y = -LEAVES_RADIUS; y <= LEAVES_RADIUS; y++) {
                            for (int z = -LEAVES_RADIUS; z <= LEAVES_RADIUS; z++) {
                                var magnitude = Math.sqrt(x * x + y * y + z * z);

                                if (magnitude > LEAVES_RADIUS || magnitude < LEAVES_RADIUS - 1) {
                                    continue;
                                }

                                var pos = new BlockPos(newPos).add(x, y, z);
                                if (world.isAir(pos))
                                    setBlockState(world, pos, Blocks.BLUE_STAINED_GLASS.getDefaultState());
                            }
                        }
                    }
                }

                setBlockState(world, new BlockPos(newPos), LuminisciaBlocks.SHIMMERWOOD_LOG.getDefaultState());
            }

            branch(world, newPos, rotationX, rotationY, depth + 1);
        }
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

    public Vec3i rotateVector(Vec3i vector, Vec2f rotation) {
        return rotateVector(vector, rotation, new Vec3i(0, 0, 0));
    }

    public Vec3i rotateVector(Vec3i vector, Vec2f rotation, Vec3i anchor) {
        float f = MathHelper.cos((rotation.y + 90.0F) * 0.017453292F);
        float g = MathHelper.sin((rotation.y + 90.0F) * 0.017453292F);
        float h = MathHelper.cos(-rotation.x * 0.017453292F);
        float i = MathHelper.sin(-rotation.x * 0.017453292F);
        float j = MathHelper.cos((-rotation.x + 90.0F) * 0.017453292F);
        float k = MathHelper.sin((-rotation.x + 90.0F) * 0.017453292F);
        Vec3d vec3d2 = new Vec3d((double) (f * h), (double) i, (double) (g * h));
        Vec3d vec3d3 = new Vec3d((double) (f * j), (double) k, (double) (g * j));
        Vec3d vec3d4 = vec3d2.crossProduct(vec3d3).multiply(-1.0);
        double d = vec3d2.x * vector.getZ() + vec3d3.x * vector.getY() + vec3d4.x * vector.getX();
        double e = vec3d2.y * vector.getZ() + vec3d3.y * vector.getY() + vec3d4.y * vector.getX();
        double l = vec3d2.z * vector.getZ() + vec3d3.z * vector.getY() + vec3d4.z * vector.getX();
        return vec3dTo3i((float)(anchor.getX() + d), (float)(anchor.getY() + e), (float)(anchor.getZ() + l));
    }
}

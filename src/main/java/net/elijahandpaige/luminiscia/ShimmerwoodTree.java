import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ShimmerwoodTree extends Feature<DefaultFeatureConfig> {
    public ShimmerwoodTree(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        BlockPos testPos = origin;

        if (world.getBlockState(testPos).isIn(BlockTags.DIRT)) {
            if (world.getBlockState(testPos.up()).isOf(Blocks.AIR)) {
                for (int i = 0; i < 6; i++) {
                    // create a simple pillar of blocks
                    world.setBlockState(testPos, Blocks.CHORUS_PLANT.getDefaultState(), 0x10);
                    testPos = testPos.up();

                    // ensure we don't try to place blocks outside the world
                    if (testPos.getY() >= world.getTopY()) break;
                }
                return true;
            }
        }

        return false;
    }
}

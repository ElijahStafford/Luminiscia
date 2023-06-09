package net.elijahandpaige.luminiscia;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Set;

public class LuminisciaBlocks {
    public static final Block PYROFLUXITE_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f));
    public static final Block SHIMMERWOOD_LOG = new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(4.0f).requiresTool());
    public static final Block SHIMMERWOOD_STRIPPED_LOG = new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(4.0f).requiresTool());
    public static final Block SHIMMERWOOD_PLANKS = new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4.0f).requiresTool());
    public static final Block SHIMMERWOOD_STAIRS = new StairsBlock(SHIMMERWOOD_PLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4.0f).requiresTool());
    public static final Block SHIMMERWOOD_SLAB = new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4.0f).requiresTool());
    //public static final Block SHIMMERWOOD_SIGN = new SignBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(1.0f).requiresTool(), WoodType.OAK);

    public static void registerAll() {
        registerBlock("pyrofluxite_ore", PYROFLUXITE_ORE);
        registerBlock("shimmerwood_log", SHIMMERWOOD_LOG);
        registerBlock("shimmerwood_planks", SHIMMERWOOD_PLANKS);
        registerBlock("shimmerwood_stairs", SHIMMERWOOD_STAIRS);
        registerBlock("shimmerwood_slab", SHIMMERWOOD_SLAB);
        registerBlock("shimmerwood_stripped_log", SHIMMERWOOD_STRIPPED_LOG);
        //registerBlock("shimmerwood_sign", SHIMMERWOOD_SIGN);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(
                Registries.BLOCK,
                new Identifier(Luminiscia.MOD_ID, name),
                block
        );
    }
}


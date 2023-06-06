package net.elijahandpaige.luminiscia;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class LuminisciaDimension {
    public static final RegistryKey<World> KEY = RegistryKey.of(
            RegistryKeys.WORLD,
            new Identifier(Luminiscia.MOD_ID, "luminiscia")
    );

    public static final RegistryKey<DimensionType> TYPE = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE,
            KEY.getValue()
    );

    public static void register() {

    }

    public static void spline() {
        // https://github.com/Swifter1243/ReMapper/blob/master/src/animation.ts#L551-L572
    }
}

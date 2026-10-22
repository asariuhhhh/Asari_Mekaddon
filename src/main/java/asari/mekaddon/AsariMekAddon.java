package asari.mekaddon;
//.\gradlew.bat runClient

import asari.mekaddon.block.AAblocks;
import asari.mekaddon.config.AsariMekAddonConfig;
import asari.mekaddon.recipe.AsariMekAddonRecipeType;
import asari.mekaddon.reg.AsariMekAddonSeri;
import asari.mekaddon.reg.BlockContainer;
import asari.mekaddon.reg.BlockType_entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(AsariMekAddon.MOD_ID)
public class AsariMekAddon {

    public static final String MOD_ID = "asari_mekaddon";

    public AsariMekAddon() {

        IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();
        AsariMekAddonConfig.registerConfigs(ModLoadingContext.get());

        ModGases.GASES.register(modEventBus);
        AAblocks.BLOCKS.register(modEventBus);
        BlockType_entity.TILE_ENTITY_TYPES.register(modEventBus);
        BlockContainer.CONTAINER_TYPE.register(modEventBus);
        AsariMekAddonRecipeType.RECIPE_TYPES.register(modEventBus);
        AsariMekAddonSeri.RECIPE_SERIALIZERS.register(modEventBus);

    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(AsariMekAddon.MOD_ID,path);
    }
}
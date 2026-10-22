package asari.mekaddon.config;

import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class AsariMekAddonConfig {

    public static final AsariMekAddonUsageConfig USAGE = new AsariMekAddonUsageConfig();
    public static final AsariMekAddonStorageConfig STORAGE = new AsariMekAddonStorageConfig();


    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        MekanismConfigHelper.registerConfig(modContainer,USAGE);
        MekanismConfigHelper.registerConfig(modContainer,STORAGE);
    }
}

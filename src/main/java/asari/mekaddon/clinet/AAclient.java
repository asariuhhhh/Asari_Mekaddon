package asari.mekaddon.clinet;

import asari.mekaddon.AsariMekAddon;
import asari.mekaddon.clinet.menu.AAtest_g_menu;
import asari.mekaddon.clinet.menu.AntiprotonicNucleosynthesizeGui;
import asari.mekaddon.reg.BlockContainer;
import mekanism.client.ClientRegistrationUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AsariMekAddon.MOD_ID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
public class AAclient {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(AAclient::registercc);
    }
    private static void registercc() {
        ClientRegistrationUtil.registerScreen(BlockContainer.TEST_G, AAtest_g_menu::new);
        ClientRegistrationUtil.registerScreen(BlockContainer.ANTIPROTONIC_NUCLEOSYNTHESIZE, AntiprotonicNucleosynthesizeGui::new);
    }

}

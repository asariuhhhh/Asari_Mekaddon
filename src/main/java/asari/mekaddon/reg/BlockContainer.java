package asari.mekaddon.reg;

import asari.mekaddon.mchines.AntiprotonicNucleosynthesize;
import asari.mekaddon.AsariMekAddon;
import asari.mekaddon.block.AAblocks;
import asari.mekaddon.mchines.test_g;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;


public class BlockContainer {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPE = new ContainerTypeDeferredRegister(AsariMekAddon.MOD_ID);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<test_g>> TEST_G;
    public static final ContainerTypeRegistryObject<MekanismTileContainer<AntiprotonicNucleosynthesize>> ANTIPROTONIC_NUCLEOSYNTHESIZE;


    static {
        TEST_G = CONTAINER_TYPE.register(AAblocks.TEST_G,test_g.class);
        ANTIPROTONIC_NUCLEOSYNTHESIZE = CONTAINER_TYPE.register(AAblocks.ANTIPROTONIC_NUCLEOSYNTHESIZE,AntiprotonicNucleosynthesize.class);
    }

    }

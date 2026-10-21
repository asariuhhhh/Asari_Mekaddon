package asari.mekaddon.reg;

import asari.mekaddon.mchines.AntiprotonicNucleosynthesize;
import asari.mekaddon.AsariMekAddon;
import asari.mekaddon.block.AAblocks;
import asari.mekaddon.mchines.test_g;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class BlockType_entity {
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(AsariMekAddon.MOD_ID);
    public static final TileEntityTypeRegistryObject<test_g> TEST_G;
    public static final TileEntityTypeRegistryObject<AntiprotonicNucleosynthesize> ANTIPROTONIC_NUCLEOSYNTHESIZE;

    static {
        TEST_G = TILE_ENTITY_TYPES.builder(AAblocks.TEST_G,test_g::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .build();
        ANTIPROTONIC_NUCLEOSYNTHESIZE = TILE_ENTITY_TYPES.builder(AAblocks.ANTIPROTONIC_NUCLEOSYNTHESIZE,AntiprotonicNucleosynthesize::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .build();
    }
}

    package asari.mekaddon.block;

    import asari.mekaddon.mchines.AntiprotonicNucleosynthesize;
    import asari.mekaddon.AsariMekAddon;
    import asari.mekaddon.mchines.test_g;
    import asari.mekaddon.reg.AAmannger_machine;
    import mekanism.common.block.interfaces.IHasDescription;
    import mekanism.common.block.prefab.BlockTile;
    import mekanism.common.item.block.ItemBlockTooltip;
    import mekanism.common.item.block.machine.ItemBlockMachine;
    import mekanism.common.registration.impl.BlockDeferredRegister;
    import mekanism.common.registration.impl.BlockRegistryObject;
    import mekanism.common.resource.BlockResourceInfo;
    import net.minecraft.world.item.Rarity;
    import net.minecraft.world.level.block.Block;

    import java.util.function.Supplier;

    public class AAblocks {
        public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(AsariMekAddon.MOD_ID);

        public static final BlockRegistryObject<BlockTile.BlockTileModel<test_g, AAmannger_machine<test_g>>, ItemBlockTooltip> TEST_G;
        public static final BlockRegistryObject<BlockTile.BlockTileModel<AntiprotonicNucleosynthesize,AAmannger_machine<AntiprotonicNucleosynthesize>>, ItemBlockTooltip> ANTIPROTONIC_NUCLEOSYNTHESIZE;

        static {
            TEST_G = BLOCKS.register("test_g",() -> new BlockTile.BlockTileModel<>(AAblocktypes.TEST_G, properties -> properties.strength(3.5F,16F).requiresCorrectToolForDrops().mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
            ANTIPROTONIC_NUCLEOSYNTHESIZE = BLOCKS.register("antiprotonicnucleosynthesize",() -> new BlockTile.BlockTileModel<>(AAblocktypes.ANTIPROTONIC_NUCLEOSYNTHESIZE,properties -> properties.strength(3.5F,16F).requiresCorrectToolForDrops().mapColor(BlockResourceInfo.STEEL.getMapColor())),ItemBlockMachine::new);
        }


        private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerBlock(String name,
                                                                                                                                 Supplier<? extends BLOCK> blockSupplier) {
            return BLOCKS.registerDefaultProperties(name, blockSupplier, ItemBlockTooltip::new);
        }

        private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerBlock(String name,
                                                                                                                                 Supplier<? extends BLOCK> blockSupplier, Rarity rarity) {
            return BLOCKS.registerDefaultProperties(name, blockSupplier, (block, props) -> new ItemBlockTooltip<>(block, props.rarity(rarity)));
        }

    }

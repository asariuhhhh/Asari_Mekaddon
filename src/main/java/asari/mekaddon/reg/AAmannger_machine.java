package asari.mekaddon.reg;

import mekanism.api.Upgrade;
import mekanism.api.text.ILangEntry;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.AttributeParticleFX;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.lib.math.Pos3D;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

import java.util.EnumSet;
import java.util.function.Supplier;

public class AAmannger_machine<TILE extends TileEntityMekanism> extends BlockTypeTile<TILE> {
    public AAmannger_machine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar, ILangEntry description) {
        super(tileEntityRegistrar, description);
        add(new AttributeParticleFX()
                .add(ParticleTypes.SMOKE, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, 0.52))
                .add(DustParticleOptions.REDSTONE, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, 0.52)));
        add(Attributes.ACTIVE_LIGHT, new AttributeStateFacing(), Attributes.SECURITY, Attributes.INVENTORY, Attributes.REDSTONE, Attributes.COMPARATOR);
        add(new AttributeUpgradeSupport(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)));
    }

    public static class AAMachineBuilder<MACHINE extends AAmannger_machine<TILE>, TILE extends TileEntityMekanism, T extends AAMachineBuilder<MACHINE, TILE, T>> extends BlockTileBuilder<MACHINE, TILE, T> {
        protected AAMachineBuilder(MACHINE holder) {
            super(holder);
        }

        public static <TILE extends TileEntityMekanism> AAMachineBuilder<AAmannger_machine<TILE>, TILE, ?> createAAMachine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar,
                                                                                                                           ILangEntry description) {
            return new AAMachineBuilder<>(new AAmannger_machine<>(tileEntityRegistrar, description));
        }
    }
}

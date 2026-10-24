package asari.mekaddon.block;

import asari.AAlang;
import asari.mekaddon.config.AsariMekAddonConfig;
import asari.mekaddon.mchines.AntiprotonicNucleosynthesize;
import asari.mekaddon.mchines.test_g;
import asari.mekaddon.reg.BlockContainer;
import asari.mekaddon.reg.BlockType_entity;
import asari.mekaddon.reg.AAmannger_machine;

import mekanism.api.Upgrade;

import mekanism.common.block.attribute.AttributeCustomSelectionBox;
import mekanism.common.block.attribute.AttributeParticleFX;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.blocktype.BlockShapes;
import mekanism.common.registries.MekanismSounds;


public class AAblocktypes {
        public static final AAmannger_machine<test_g> TEST_G = AAmannger_machine.AAMachineBuilder
                .createAAMachine(() -> BlockType_entity.TEST_G, AAlang.TEST_G)
                .withGui(() -> BlockContainer.TEST_G)
                .withEnergyConfig(AsariMekAddonConfig.USAGE.test_g,AsariMekAddonConfig.STORAGE.test_g)
                .withSupportedUpgrades(java.util.EnumSet.of(Upgrade.ENERGY,Upgrade.MUFFLING))
                .withComputerSupport("test_g")
                .withSound(MekanismSounds.CHEMICAL_OXIDIZER)
                .replace(Attributes.ACTIVE_LIGHT)
                .build();

        public static final AAmannger_machine<AntiprotonicNucleosynthesize> ANTIPROTONIC_NUCLEOSYNTHESIZE = AAmannger_machine.AAMachineBuilder
                .createAAMachine(() -> BlockType_entity.ANTIPROTONIC_NUCLEOSYNTHESIZE,AAlang.ANTIPROTONIC_NUCLEOSYNTHESIZE)
                .withGui(() -> BlockContainer.ANTIPROTONIC_NUCLEOSYNTHESIZE)
                //.withEnergyConfig(MekanismConfig.usage.antiprotonicNucleosynthesizer,MekanismConfig.storage.antiprotonicNucleosynthesizer)
                .with(AttributeCustomSelectionBox.JSON)
                .withCustomShape(BlockShapes.SOLAR_NEUTRON_ACTIVATOR)
                //.with(AttributeCustomSelectionBox.JSON)
                .without(AttributeParticleFX.class, AttributeUpgradeSupport.class)

               // .withBounding(((blockPos, state, Builder) -> Builder.add(blockPos.above())))
                .withComputerSupport("antiprotonicnucleosynthesize")
                .replace(Attributes.ACTIVE)
                .build();
}

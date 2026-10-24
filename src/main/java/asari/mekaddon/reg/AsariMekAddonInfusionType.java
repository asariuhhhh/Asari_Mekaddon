package asari.mekaddon.reg;

import asari.mekaddon.AsariMekAddon;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.common.registration.impl.InfuseTypeDeferredRegister;
import mekanism.common.registration.impl.InfuseTypeRegistryObject;

public class AsariMekAddonInfusionType {

    public static final InfuseTypeDeferredRegister INFUSE_TYPES = new InfuseTypeDeferredRegister(AsariMekAddon.MOD_ID);

    public static final InfuseTypeRegistryObject<InfuseType> ANTIMATTER_G = INFUSE_TYPES.register("antimatter_infuse_gas",0x844ECC);

}

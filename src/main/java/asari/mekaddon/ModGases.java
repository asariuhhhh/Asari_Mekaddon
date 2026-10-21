package asari.mekaddon;
import mekanism.api.math.FloatingLong;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasBuilder;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.DeferredRegister;
import mekanism.api.MekanismAPI;
import net.minecraftforge.registries.RegistryObject;

public class ModGases {
    public static final DeferredRegister<Gas> GASES =
            DeferredRegister.create(
                    MekanismAPI.GAS_REGISTRY_NAME,
                    AsariMekAddon.MOD_ID
            );
    public static final RegistryObject<Gas> ASARI_GAS =
            GASES.register("asari_gas", () ->
                    new Gas(GasBuilder.builder()
                            .tint(0x663399)
                            .with(new GasAttributes.Fuel(
                                    44,
                                    FloatingLong.createConst(6200000))
                            ))
            );
    public static final RegistryObject<Gas> STEP0_ASARI_GAS =
            GASES.register("step0_asari_gas", () ->
                    new Gas(GasBuilder.builder().tint(0x8080FF))
            );
    public static final RegistryObject<Gas> STEP1_ASARI_GAS =
            GASES.register("step1_asari_gas", () ->
                    new Gas(GasBuilder.builder()
                            .tint(0x008080)
                            .with(new GasAttributes.Radiation(66.7))
                            )
            );
    public static final RegistryObject<Gas> ANTI_RADIATION_GAS =
            GASES.register("anti_radiation_gas", () ->
                    new Gas(GasBuilder.builder().tint(0xFF9999))
            );
    public static final RegistryObject<Gas> PALLADIUM =
            GASES.register("palladium_gas",() ->
                    new Gas(GasBuilder.builder().tint(0xC0C0C0))
            );
}
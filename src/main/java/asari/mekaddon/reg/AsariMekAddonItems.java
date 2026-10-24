package asari.mekaddon.reg;

import asari.mekaddon.AsariMekAddon;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AsariMekAddonItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AsariMekAddon.MOD_ID);

    public static final RegistryObject<Item> ANTIMATTER_CRYSTAL = ITEMS.register("antimatter_crystal",() -> new Item(new Item.Properties()));
}

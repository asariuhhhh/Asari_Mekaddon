package asari;


import asari.mekaddon.AsariMekAddon;
import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum AAlang implements ILangEntry {
    TEST_G("description","test_g"),
    ANTIPROTONIC_NUCLEOSYNTHESIZE("description","antiprotonicnucleosynthesize");



    private final String key;

    AAlang(String type,String path) {
        this(Util.makeDescriptionId(type,AsariMekAddon.rl(path)));
    }
    AAlang(String key) {
        this.key = key;
    }


    @Override
    public String getTranslationKey() {
        return key;
    }

}

package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.modules.AbstractModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleBotania extends AbstractModule {

    public static ManaInfusionMethod MANA_INFUSION;
    public static ManaAlchemyMethod MANA_ALCHEMY;
    public static ElvenTradeMethod ELVEN_TRADE;
    public static RunicAltarMethod RUNIC_ALTAR;
    public static PetalApothecaryMethod PETAL_APOTHECARY;

    public static final BiMap<String, AbstractHeckMethod> methods = HashBiMap.create();

    public static final List<String> methodIds = new ArrayList<>();

    public ModuleBotania() {}

    public void init(BiMap<String, AbstractHeckMethod> heckMethods) {
        MANA_INFUSION = registerMethod("mana_infusion", new ManaInfusionMethod(), heckMethods);
        MANA_ALCHEMY = registerMethod("mana_alchemy", new ManaAlchemyMethod(), heckMethods);
        ELVEN_TRADE = registerMethod("elven_trade", new ElvenTradeMethod(), heckMethods);
        RUNIC_ALTAR = registerMethod("runic_altar", new RunicAltarMethod(), heckMethods);
        PETAL_APOTHECARY = registerMethod("petal_apothecary", new PetalApothecaryMethod(), heckMethods);
    }

    public static <T extends AbstractHeckMethod> T registerMethod(String id, T method, BiMap<String, AbstractHeckMethod> heckMethods) {
        methods.put(id, method);
        methodIds.add(id);
        heckMethods.put(id, method);
        return method;
    }

    public BiMap<String, AbstractHeckMethod> getMethods() {
        return methods;
    }

    public List<String> getMethodIds() {
        return methodIds;
    }
}

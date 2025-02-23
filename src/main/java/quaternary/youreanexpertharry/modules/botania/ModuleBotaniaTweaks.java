package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.modules.AbstractModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleBotaniaTweaks extends AbstractModule {

    public static AgglomerationBasicMethod BASIC_AGGLOMERATION;
    public static AgglomerationAdvancedMethod ADVANCED_AGGLOMERATION;

    public static final BiMap<String, AbstractHeckMethod> methods = HashBiMap.create();

    public static final List<String> methodIds = new ArrayList<>();

    public void init(BiMap<String, AbstractHeckMethod> heckMethods) {
        BASIC_AGGLOMERATION = registerMethod("youre_an_expert_gary_basic_agglomeration", new AgglomerationBasicMethod(), heckMethods);
        ADVANCED_AGGLOMERATION = registerMethod("youre_an_expert_gary_advanced_agglomeration", new AgglomerationAdvancedMethod(), heckMethods);
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

package quaternary.youreanexpertharry.modules.actuallyAdditions;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.modules.AbstractModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleActuallyAddiditons extends AbstractModule {

    public static EmpowererMethod EMPOWERER;

    public static final BiMap<String, AbstractHeckMethod> methods = HashBiMap.create();

    public static final List<String> methodIds = new ArrayList<>();

    public ModuleActuallyAddiditons() {}

    @Override
    public void init(BiMap<String, AbstractHeckMethod> heckMethods) {
        EMPOWERER = registerMethod("empowererer", new EmpowererMethod(), heckMethods);
    }

    @Override
    public BiMap<String, AbstractHeckMethod> getMethods() {
        return methods;
    }

    public static <T extends AbstractHeckMethod> T registerMethod(String id, T method, BiMap<String, AbstractHeckMethod> heckMethods) {
        methods.put(id, method);
        methodIds.add(id);
        heckMethods.put(id, method);
        return method;
    }

    @Override
    public List<String> getMethodIds() {
        return methodIds;
    }
}

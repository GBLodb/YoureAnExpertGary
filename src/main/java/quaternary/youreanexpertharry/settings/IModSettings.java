package quaternary.youreanexpertharry.settings;

import quaternary.youreanexpertharry.etc.HeckMethodProps;
import quaternary.youreanexpertharry.heck.HeckTier;

import java.util.List;

public interface IModSettings {
    static void init(List<HeckTier.TierItemStack> goalItems, List<HeckTier.TierItemStack> bannedItems, List<HeckTier.TierItemStack> baseItems, List<HeckMethodProps> heckMethods){};
}

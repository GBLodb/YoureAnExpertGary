package quaternary.youreanexpertharry.settings;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import quaternary.youreanexpertharry.etc.HeckMethodProps;
import quaternary.youreanexpertharry.heck.HeckMethods;
import quaternary.youreanexpertharry.heck.HeckTier;
import de.ellpeck.actuallyadditions.mod.blocks.*;
import de.ellpeck.actuallyadditions.mod.items.*;
import java.util.List;

public class ActuallyAdditionsSettings implements IModSettings {

    public static void init(List<HeckTier.TierItemStack> goalItems, List<HeckTier.TierItemStack> bannedItems, List<HeckTier.TierItemStack> baseItems, List<HeckMethodProps> heckMethods) {

        // Black Quartz, Wood Casing.
        baseItems.add(new HeckTier.TierItemStack(new ItemStack(ItemMisc.getItemById(5)), 0));
        baseItems.add(new HeckTier.TierItemStack(new ItemStack(BlockMisc.getBlockById(4)), 0));
        for (int i = 0; i < 6; i++) {
            baseItems.add(new HeckTier.TierItemStack(new ItemStack(ItemCrystal.getItemById(i), 1, i), 0));
            goalItems.add(new HeckTier.TierItemStack(new ItemStack(BlockEmpowerer.getBlockById(i), 1, i), 0));
        }

        heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("empowerer"), 1, 6));

        goalItems.add(new HeckTier.TierItemStack(new ItemStack(ItemMisc.getItemById(4), 1, 0), 3));

    }
}

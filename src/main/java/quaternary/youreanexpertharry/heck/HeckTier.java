package quaternary.youreanexpertharry.heck;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class HeckTier {
    public final int id;
    public final Set<Heck.GoodItemStack> bannedItems = new HashSet<>();
    public final Set<Heck.GoodItemStack> goalItems = new HashSet<>();
    public final Set<Heck.GoodItemStack> baseItems = new HashSet<>();

    public HeckTier(int id) {
        this.id = id;
    }

    public static class TierItemStack {
        public final ItemStack stack;
        public final int tier;
        public TierItemStack(ItemStack stack, int tier) {
            this.stack = stack;
            this.tier = tier;
        }

        public TierItemStack(Block block) {
            this.stack = new ItemStack(block);
            this.tier = 0;
        }

        public TierItemStack(Item item) {
            this.stack = new ItemStack(item);
            this.tier = 0;
        }

    }
}

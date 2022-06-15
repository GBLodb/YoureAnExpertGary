package quaternary.youreanexpertharry.settings;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import quaternary.youreanexpertharry.etc.HeckMethodProps;
import quaternary.youreanexpertharry.heck.HeckMethods;
import quaternary.youreanexpertharry.heck.HeckTier;

import java.util.List;

public class BotaniaSettings implements IModSettings {

    public static void init(List<HeckTier.TierItemStack> goalItems, List<HeckTier.TierItemStack> bannedItems, List<HeckTier.TierItemStack> baseItems, List<HeckMethodProps> heckMethods) {
        NonNullList<ItemStack> flowers = NonNullList.create();
        vazkii.botania.common.block.ModBlocks.specialFlower.getSubBlocks(vazkii.botania.common.core.BotaniaCreativeTab.INSTANCE, flowers);
        //Get ready for mana pool. A mana pool requires a mana spreader, a wand of the forest, and some sort of generating flower.
        //I realise that all of those can be made without the petal apothecary. But, I'll use it, so that I can have petal apothecary recipes in tier 1.
        baseItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.STONE_SLAB, 1, 3), 0));
        baseItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.altar, 1, 0), 0));
        YAEHSettings.addAllSubtypesTo(baseItems, vazkii.botania.common.item.ModItems.petal, 0);

        //Tier 1
        heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("petal_apothecary"), 1, 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.item.ModItems.lexicon), 1));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.item.ModItems.twigWand), 1));
        //Can't add flowers. Add goalItems for the flower ingredients?
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.spreader, 1, 0), 1));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.pool), 1));

        heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("mana_infusion"), 2, 4));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.alchemyCatalyst), 2));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.runeAltar), 2));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.item.ModItems.fertilizer), 2));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.livingrock), 2));

        heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("mana_alchemy"), 3,4));
        heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("runic_altar"), 3, 6));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.livingwood, 1, 0), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.livingwood, 1, 5), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.alfPortal), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.pylon, 1, 1), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.pylon, 1, 2), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.terraPlate), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.item.ModItems.spark), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.LAPIS_BLOCK), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.BEACON), 3));
        goalItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.IRON_BLOCK), 3));

        heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("elven_trade"), 4, 6));
        baseItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 4), 4));

        baseItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 5), 5));
        baseItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 14), 5));


        if (Loader.isModLoaded("botania_tweaks")) {
            heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("basic_agglomeration"), 4, 5));
            heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("advanced_agglomeration"), 5, 6));
        }
    }

}

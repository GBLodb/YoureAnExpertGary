package quaternary.youreanexpertharry.settings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.youreanexpertharry.heck.HeckMethods;
import quaternary.youreanexpertharry.etc.HeckMethodProps;
import quaternary.youreanexpertharry.heck.HeckTier;

import java.util.ArrayList;
import java.util.List;

public class YAEHSettings {
	public List<HeckTier.TierItemStack> goalItems = new ArrayList<>();
	public int topDifficulty = 6;
	
	public List<HeckTier.TierItemStack> bannedItems = new ArrayList<>();
	public List<HeckTier.TierItemStack> baseItems = new ArrayList<>();

	public List<HeckMethodProps> heckMethods = new ArrayList<>();
	
	{
		goalItems.add(new HeckTier.TierItemStack(Items.CLAY_BALL));
		goalItems.add(new HeckTier.TierItemStack(Blocks.CLAY));
		if (!(Loader.isModLoaded("botania"))) {
			goalItems.add(new HeckTier.TierItemStack(Blocks.BEACON));
		}
		//You can't even use these in gamemode 0 but that won't stop me
		goalItems.add(new HeckTier.TierItemStack(Blocks.COMMAND_BLOCK));
		goalItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.OBSIDIAN), 2));
		
		bannedItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.BEDROCK), 3));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.COMMAND_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.CHAIN_COMMAND_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.REPEATING_COMMAND_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.BARRIER));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.STRUCTURE_BLOCK));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.STRUCTURE_VOID));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.END_PORTAL_FRAME));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.MOB_SPAWNER));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.FARMLAND));
		bannedItems.add(new HeckTier.TierItemStack(Blocks.GRASS_PATH));
		addAllSubtypesTo(bannedItems, Blocks.MONSTER_EGG, 0);
		bannedItems.add(new HeckTier.TierItemStack(Items.COMMAND_BLOCK_MINECART));
		bannedItems.add(new HeckTier.TierItemStack(Items.SPAWN_EGG));
		bannedItems.add(new HeckTier.TierItemStack(Items.KNOWLEDGE_BOOK));

		//Gatherable
		baseItems.add(new HeckTier.TierItemStack(Items.COAL));
		baseItems.add(new HeckTier.TierItemStack(new ItemStack(Items.DYE, 1, 4), 0));
		baseItems.add(new HeckTier.TierItemStack(Items.REDSTONE));
		baseItems.add(new HeckTier.TierItemStack(Items.IRON_INGOT));
		baseItems.add(new HeckTier.TierItemStack(Items.GOLD_INGOT));
		baseItems.add(new HeckTier.TierItemStack(Items.DIAMOND));
		addAllSubtypesTo(baseItems, Blocks.LOG, 0);
		//for exploration!
		//addAllSubtypesTo(baseItems, Blocks.LOG2, 0);
		baseItems.add(new HeckTier.TierItemStack(Blocks.COBBLESTONE));
		baseItems.add(new HeckTier.TierItemStack(Blocks.DIRT));
		addAllSubtypesTo(baseItems, Blocks.SAPLING, 0);
		baseItems.add(new HeckTier.TierItemStack(Blocks.SAND));
		baseItems.add(new HeckTier.TierItemStack(Blocks.GRAVEL));

		baseItems.add(new HeckTier.TierItemStack(Items.BEETROOT_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.MELON_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.PUMPKIN_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.WHEAT_SEEDS));
		baseItems.add(new HeckTier.TierItemStack(Items.WHEAT));
		baseItems.add(new HeckTier.TierItemStack(Items.MELON));
		baseItems.add(new HeckTier.TierItemStack(Items.BEETROOT));
		baseItems.add(new HeckTier.TierItemStack(Blocks.PUMPKIN));

		//Simple crafting
		baseItems.add(new HeckTier.TierItemStack(Items.BUCKET));
		addAllSubtypesTo(baseItems, Blocks.PLANKS, 0);
		baseItems.add(new HeckTier.TierItemStack(Items.STICK));
		baseItems.add(new HeckTier.TierItemStack(Blocks.TORCH));
		baseItems.add(new HeckTier.TierItemStack(Blocks.LAPIS_BLOCK));
		baseItems.add(new HeckTier.TierItemStack(Blocks.REDSTONE_BLOCK));
		baseItems.add(new HeckTier.TierItemStack(Items.WOODEN_PICKAXE));
		baseItems.add(new HeckTier.TierItemStack(Items.STONE_PICKAXE));
		baseItems.add(new HeckTier.TierItemStack(Items.IRON_PICKAXE));
		baseItems.add(new HeckTier.TierItemStack(Items.GOLD_NUGGET));
		baseItems.add(new HeckTier.TierItemStack(Blocks.FURNACE));
		baseItems.add(new HeckTier.TierItemStack(Blocks.CRAFTING_TABLE));
		
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPELESS_TWO_BY_TWO, 1, 3));
		heckMethods.add(new HeckMethodProps(HeckMethods.FOUR_WAY_SYMMETRICAL_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SYMMETRICAL_SHAPED_THREE_BY_THREE, 2, 4));
		heckMethods.add(new HeckMethodProps(HeckMethods.SHAPED_THREE_BY_THREE, 5, 6));
		//Sanity check--one furnace recipe per tier!
		heckMethods.add(new HeckMethodProps(HeckMethods.SMELTING, 1, 4));

		//Botania stuff
		if (Loader.isModLoaded("botania") && Loader.isModLoaded("modtweaker")) {
			NonNullList<ItemStack> flowers = NonNullList.create();
			vazkii.botania.common.block.ModBlocks.specialFlower.getSubBlocks(vazkii.botania.common.core.BotaniaCreativeTab.INSTANCE, flowers);
			//Get ready for mana pool. A mana pool requires a mana spreader, a wand of the forest, and some sort of generating flower.
			//I realise that all of those can be made without the petal apothecary. But, I'll use it, so that I can have petal apothecary recipes in tier 1.
			baseItems.add(new HeckTier.TierItemStack(new ItemStack(Blocks.STONE_SLAB, 1, 3), 0));
			baseItems.add(new HeckTier.TierItemStack(new ItemStack(vazkii.botania.common.block.ModBlocks.altar, 1, 0), 0));
			addAllSubtypesTo(baseItems, vazkii.botania.common.item.ModItems.petal, 0);

			//Tier 1
			if (Loader.isModLoaded("botania_tweaks")) {
				heckMethods.add(new HeckMethodProps(HeckMethods.methods.get("petal_apothecary"), 1, 3));
			}
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
	
	private static void addAllSubtypesTo(List<HeckTier.TierItemStack> list, Block b, int tier) {
		NonNullList<ItemStack> bepis = NonNullList.create();
		b.getSubBlocks(b.getCreativeTab(), bepis);
		bepis.forEach(is -> list.add(new HeckTier.TierItemStack(is, tier)));
	}

	private static void addAllSubtypesTo(List<HeckTier.TierItemStack> list, Item i, int tier) {
		NonNullList<ItemStack> bepis = NonNullList.create();
		i.getSubItems(i.getCreativeTab(), bepis);
		bepis.forEach(is -> list.add(new HeckTier.TierItemStack(is, tier)));
	}
}

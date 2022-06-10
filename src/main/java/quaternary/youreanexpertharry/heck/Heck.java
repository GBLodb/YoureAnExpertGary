package quaternary.youreanexpertharry.heck;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.heck.tasks.IHeckTask;
import quaternary.youreanexpertharry.heck.tasks.RecipeTask;
import quaternary.youreanexpertharry.settings.YAEHSettings;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Heck {
	public static final Random random = new Random();
	static List<Item> allItems;
	public static YAEHSettings settings;
	
	public static void doHeck() throws Heckception {
		settings = YoureAnExpertHarry.settings;
		HeckData allHeck = new HeckData(settings);
		
		Collection<Item> allItemsCollection = ForgeRegistries.ITEMS.getValuesCollection();
		allItems = new ArrayList<>(allItemsCollection);
		
		List<String> zenBody = new ArrayList<>();
		allHeck.recipeCount = 0;

		while(allHeck.currentLevel >= 0) {
			zenBody.add("// RECIPE LEVEL: " + allHeck.currentLevel + "\n\n");
			allHeck.tasks.stream().filter(p -> p instanceof RecipeTask).forEach(p -> allHeck.bannedItems.add(((RecipeTask) p).outputGood));

			for (IHeckTask task : allHeck.tasks) {
				zenBody.add(task.execute());
			}

			allHeck.currentLevel--;
			if (allHeck.currentLevel == -1) break;
			allHeck.tasks.clear();
			allHeck.tasks.addAll(allHeck.nextTasks);
			allHeck.tiers.get(allHeck.currentLevel).goalItems
					.forEach(p -> allHeck.tasks.add(new RecipeTask(allHeck, settings, allHeck.currentLevel, p)));
			allHeck.nextTasks.clear();
		}
		
		StringBuilder header = new StringBuilder();
		allHeck.usedMethods.forEach(a -> a.getRequiredImports().ifPresent(i -> {
			header.append(i);
			header.append('\n');
		}));

		File mainFolder = YoureAnExpertHarry.settingsFile.getParentFile().getParentFile();
		File scriptsFolder = new File(mainFolder.getAbsolutePath() + File.separator + "scripts");
		if (scriptsFolder.mkdirs()) {
			splitAndWriteZenScript(header.toString(), zenBody, scriptsFolder);
			YoureAnExpertHarry.LOGGER.info("Done");
		}
		YoureAnExpertHarry.LOGGER.warn("Something went wrong while creating the scripts folder.");
	}

	//Add disallowed recipes
	public static AbstractHeckMethod chooseMethod(YAEHSettings settings, int currentLevel, AbstractHeckMethod disallowedMethod) throws Heckception {
		List<AbstractHeckMethod> methods;

		if (currentLevel > 0) {
			methods = settings.heckMethods.stream()
					.filter(p -> currentLevel <= p.maxLevel && currentLevel >= p.minLevel)
					.map(p -> p.method)
					.collect(Collectors.toList());
		} else {
			methods = new ArrayList<>();
			methods.add(HeckMethods.FOUR_WAY_SYMMETRICAL_THREE_BY_THREE);
			methods.add(HeckMethods.SHAPED_THREE_BY_THREE);
			methods.add(HeckMethods.SHAPELESS_TWO_BY_TWO);
		}

		methods.remove(disallowedMethod);
		
		if(methods.size() == 0) throw new Heckception("No heck methods available for level " + currentLevel);
		else return methods.get(random.nextInt(methods.size()));
	}


	public static ItemStack chooseItem(HeckData allHeck, GoodItemStack alsoBannedItem, boolean base) throws Heckception {
		if (base) return chooseBaseItem(allHeck.allBaseItems);
		Set<GoodItemStack> tierBaseItems = allHeck.tiers.get(allHeck.currentLevel).baseItems;
		for(int tries = 0; tries < 1000; tries++) {
			GoodItemStack bep;
			ItemStack hahayes;

			//Tries to add a baseItem from the tier with a chance that scales with number of items in tier.
			//Next up, change baseList, so it actually has all the base items from every tier lower than the current one.
			if (random.nextInt((YoureAnExpertHarry.settings.topDifficulty - allHeck.currentLevel + 1) * 10) == 1
					&& tierBaseItems.size() > 0) {
				ArrayList<GoodItemStack> baseList = new ArrayList<>(tierBaseItems);
				bep = baseList.get(random.nextInt(baseList.size()));
				hahayes = bep.actualStack;
			} else {
				Item i = allItems.get(random.nextInt(allItems.size()));
				int data;
				if(i.getHasSubtypes()) {
					NonNullList<ItemStack> choices = NonNullList.create();
					i.getSubItems(Objects.requireNonNull(i.getCreativeTab()), choices);
					if(choices.isEmpty()) data = 0;
					else data = choices.get(random.nextInt(choices.size())).getMetadata();
				} else {
					data = 0;
				}

				hahayes = new ItemStack(i, 1, data);
				bep = new GoodItemStack(hahayes);
			}

			if(!hahayes.isEmpty() && !allHeck.bannedItems.contains(bep) && !allHeck.tiers.get(allHeck.currentLevel).bannedItems.contains(bep)
					&& !alsoBannedItem.equals(bep) && itemSanity(hahayes)) return hahayes;
		}
		
		throw new Heckception("Ran out of input items for recipes (couldn't find a fresh item to add to a recipe after 1000 tries). Either your difficulty is set too high, or you are just unlucky");
	}

	public static boolean itemSanity(ItemStack is) {
		Item it = is.getItem();
		if (it instanceof ItemBlock) return !(((ItemBlock) it).getBlock() instanceof BlockFluidBase);
		return true;
	}


	private static ItemStack chooseBaseItem(Set<GoodItemStack> baseItems) {
		ArrayList<GoodItemStack> base = new ArrayList<>(baseItems);
		Item i = base.get(random.nextInt(base.size())).actualStack.getItem();
		int data;
		if (i.getHasSubtypes()) {
			NonNullList<ItemStack> choices = NonNullList.create();
			i.getSubItems(Objects.requireNonNull(i.getCreativeTab()), choices);
			if(choices.isEmpty()) data = 0;
			else data = choices.get(random.nextInt(choices.size())).getMetadata();
		} else {
			data = 0;
		}

		return new ItemStack(i, 1, data);
	}
	
	public static class GoodItemStack {
		public GoodItemStack(ItemStack actualStack) {
			this.actualStack = actualStack;
		}
		public GoodItemStack(HeckTier.TierItemStack actualStack) {this.actualStack = actualStack.stack;}
		
		public final ItemStack actualStack;
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof GoodItemStack) {
				ItemStack other = ((GoodItemStack)obj).actualStack;
				return other.getItem() == actualStack.getItem() && other.getMetadata() == actualStack.getMetadata();
			} else return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.requireNonNull(actualStack.getItem().getRegistryName()).hashCode() + actualStack.getMetadata() * 1232323;
		}
	}
	
	private static final int LINES_PER_FILE = 150;
	
	public static void splitAndWriteZenScript(String header, List<String> lines, File scriptsFolder) throws Heckception {
		int fileCount = MathHelper.ceil(lines.size() / (float) LINES_PER_FILE);
		
		for(int i = 0; i < fileCount; i++) {
			StringBuffer b = new StringBuffer();
			b.append("#priority ");
			b.append(fileCount + 5 - i);
			b.append('\n');
			b.append(header);
			
			int from = Math.min(i * LINES_PER_FILE, lines.size());
			int to = Math.min((i + 1) * LINES_PER_FILE, lines.size());
			lines.subList(from, to).forEach(s -> {
				b.append(s);
				b.append('\n');
			});
			
			try {
				File outputFile = new File(scriptsFolder.getAbsolutePath() + File.separator + "youre_an_expert_harry_" + i + ".zs");
				if(outputFile.exists()) {
					try {
						YoureAnExpertHarry.LOGGER.info("Deleting " + outputFile.getAbsolutePath());
						outputFile.delete();
					} catch (Exception e) {
						YoureAnExpertHarry.LOGGER.error("Couldn't delete " + outputFile.getAbsolutePath());
						e.printStackTrace();
					}
				}
				try {
					YoureAnExpertHarry.LOGGER.info("Creating " + outputFile.getAbsolutePath());
					outputFile.createNewFile();
					YoureAnExpertHarry.LOGGER.info("Writing");
				} catch (Exception e) {
					YoureAnExpertHarry.LOGGER.error("Couldn't create " + outputFile.getAbsolutePath());
					e.printStackTrace();
				}
				
				try(FileWriter writer = new FileWriter(outputFile)) {
					writer.write(b.toString());
				}
			} catch(Exception eee) {
				YoureAnExpertHarry.LOGGER.error(eee);
				throw new Heckception("Couldn't write output file number " + i + " hmm (check log)");
			}
		}
	}
}

package quaternary.youreanexpertharry.heck;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.heck.tasks.RecipeTask;
import quaternary.youreanexpertharry.modules.AbstractModule;
import quaternary.youreanexpertharry.settings.YAEHSettings;

import java.util.List;
import java.util.Optional;

//Here should go a method that gets "required type of item" like if a machine block is required. Or something.
public abstract class AbstractHeckMethod {
	public AbstractHeckMethod(int inputCount) {
		this.inputCount = inputCount;
	}
	
	public final int inputCount;
	
	public abstract Optional<String> getRequiredImports();
	public abstract String removeExistingRecipe(ItemStack output);
	public abstract String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs);
	public abstract List<ItemStack> getRequiredItems();
	//Make a new data class for this check
	public abstract Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception;
	
	public String removeRecipe(ItemStack output) {
		StringBuilder b = new StringBuilder();
		b.append(HeckMethods.SHAPED_THREE_BY_THREE.removeExistingRecipe(output));
		b.append("\n");
		b.append(HeckMethods.SMELTING.removeExistingRecipe(output));
		b.append("\n");
		for (AbstractModule mod : YoureAnExpertHarry.modules) {
			for (String id : mod.getMethodIds()) {
				b.append(mod.getMethods().get(id).removeExistingRecipe(output));
			}
		}
		return b.toString();
	}

	public void addItemsToTask(List<ItemStack> recipeStacks, HeckData allHeck, YAEHSettings settings) {
		for (ItemStack is : recipeStacks) {
			Heck.GoodItemStack gis = new Heck.GoodItemStack(is);
			if (!(allHeck.allGoalItems.contains(gis)) && !(allHeck.allBaseItems.contains(gis))) {
				allHeck.nextTasks.add(new RecipeTask(allHeck, settings, allHeck.currentLevel - 1, gis));
			}
		}
	}

	public static String stackToBracket(ItemStack stack) {
		if(stack.getMetadata() != 0) {
			return String.format("<item:%s:%s>", stack.getItem().getRegistryName(), stack.getMetadata());
		} else {
			return String.format("<%s>", stack.getItem().getRegistryName());
		}
	}
	
	public static String stacksToBracketedList(List<ItemStack> stacks) {
		StringBuilder beb = new StringBuilder();
		beb.append('[');
		for(int i = 0; i < stacks.size(); i++) {
			beb.append(stackToBracket(stacks.get(i)));
			if(i != stacks.size() - 1) beb.append(", ");
		}
		beb.append(']');
		return beb.toString();
	}
	
	public static String quote(String s) {
		return '"' + s + '"';
	}
}

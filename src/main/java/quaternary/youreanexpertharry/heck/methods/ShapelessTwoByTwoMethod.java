package quaternary.youreanexpertharry.heck.methods;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.etc.ShapelessStack;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.*;

public class ShapelessTwoByTwoMethod extends AbstractCraftingMethod {

	public static final Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

	public ShapelessTwoByTwoMethod() {
		super(4);
	}

	public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
		List<ItemStack> recipeStacks = new ArrayList<>(this.inputCount);
		HashSet<ShapelessStack> shapelessSet = new HashSet<>();

		boolean sanity = false;

		while (!(sanity)) {
			recipeStacks.clear();
			shapelessSet.clear();
			for(int a = 0; a < this.inputCount; a++) {
				recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
			}
			recipeStacks.forEach(is -> ShapelessStack.shapelessAdd(shapelessSet, is));

			//YoureAnExpertHarry.LOGGER.info("Sanity-checking s2b2");
			//YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
			sanity = this.sanityCheck(shapelessSet);
		}
		//YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
		sanitySet.add(shapelessSet);
		if (allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
		String b = writeZenscript("youre_an_expert_gary_" + allHeck.recipeCount, outputGood.actualStack, recipeStacks);

		return new MutablePair<>(new MutablePair<>(recipeStacks, b), Boolean.TRUE);

	}

	private boolean sanityCheck(HashSet<ShapelessStack> shapelessSet) {
		return !sanitySet.contains(shapelessSet);
	}
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		return String.format(
						"recipes.addShapeless(%s, %s, %s);",
						quote(recipeName),
						stackToBracket(output),
						stacksToBracketedList(inputs)
		);
	}

}

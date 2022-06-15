package quaternary.youreanexpertharry.heck.methods;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShapedThreeByThreeMethod extends AbstractCraftingMethod {
	public ShapedThreeByThreeMethod() {
		super(9);
	}

	public static final Set<List<Heck.GoodItemStack>> sanitySet = new HashSet<>();
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		return String.format(
						"recipes.addShaped(%s, %s, \n [%s,\n  %s,\n  %s]);",
						quote(recipeName),
						stackToBracket(output),
						stacksToBracketedList(inputs.subList(0, 3)),
						stacksToBracketedList(inputs.subList(3, 6)),
						stacksToBracketedList(inputs.subList(6, 9))
		);
	}

	public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
		List<ItemStack> recipeStacks = new ArrayList<>(this.inputCount);
		List<Heck.GoodItemStack> sanityList = new ArrayList<>(this.inputCount);

		boolean sanity = false;

		while (!(sanity)) {
			recipeStacks.clear();
			sanityList.clear();
			for(int a = 0; a < this.inputCount; a++) {
				recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
			}
			recipeStacks.forEach(is -> sanityList.add(new Heck.GoodItemStack(is)));

			//YoureAnExpertHarry.LOGGER.info("Sanity-checking s3b3");
			//YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
			sanity = this.sanityCheck(sanityList);
		}
		//YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
		sanitySet.add(sanityList);
		if (allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
		String b = writeZenscript("youre_an_expert_gary_" + allHeck.recipeCount, outputGood.actualStack, recipeStacks);

		return new MutablePair<>(new MutablePair<>(recipeStacks, b), Boolean.TRUE);

	}

	private boolean sanityCheck(List<Heck.GoodItemStack> stackList) {
		//YoureAnExpertHarry.LOGGER.info("check failed");
		return !sanitySet.contains(stackList);
	}
}

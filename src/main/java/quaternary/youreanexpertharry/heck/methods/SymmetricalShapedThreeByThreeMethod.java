package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
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

public class SymmetricalShapedThreeByThreeMethod extends AbstractCraftingMethod {
	public SymmetricalShapedThreeByThreeMethod() {
		super(6);
	}

	public static final Set<List<Heck.GoodItemStack>> sanitySet = new HashSet<>();

	public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
		List<ItemStack> recipeStacks = new ArrayList<>(this.inputCount);
		List<Heck.GoodItemStack> sanityList = new ArrayList<>(this.inputCount);

		boolean sanity = false;
		int attemptCount = 0;

		while (!(sanity)) {

			recipeStacks.clear();
			sanityList.clear();
			for(int a = 0; a < this.inputCount; a++) {
				recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
			}
			recipeStacks.forEach(is -> sanityList.add(new Heck.GoodItemStack(is)));

			//YoureAnExpertHarry.LOGGER.info("Sanity-checking ss3b3");
			//YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
			sanity = this.sanityCheck(sanityList);
			attemptCount++;
		}
		//YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
		sanitySet.add(sanityList);
		if (allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
		String b = writeZenscript("youre_an_expert_harry_" + allHeck.recipeCount, outputGood.actualStack, recipeStacks);

		return new MutablePair<>(new MutablePair<>(recipeStacks, b), Boolean.TRUE);

	}

	private boolean sanityCheck(List<Heck.GoodItemStack> stackList) {
		return !sanitySet.contains(stackList);
	}
	
	@Override
	public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
		ItemStack topCorners = inputs.get(0);
		ItemStack top = inputs.get(1);
		ItemStack sides = inputs.get(2);
		ItemStack middle = inputs.get(3);
		ItemStack bottomCorners = inputs.get(4);
		ItemStack bottom = inputs.get(5);
		
		return String.format(
						"recipes.addShaped(%s, %s, \n [%s,\n  %s,\n  %s]);",
						quote(recipeName),
						stackToBracket(output),
						stacksToBracketedList(ImmutableList.of(topCorners, top, topCorners)),
						stacksToBracketedList(ImmutableList.of(sides, middle, sides)),
						stacksToBracketedList(ImmutableList.of(bottomCorners, bottom, bottomCorners))
		);
	}
}

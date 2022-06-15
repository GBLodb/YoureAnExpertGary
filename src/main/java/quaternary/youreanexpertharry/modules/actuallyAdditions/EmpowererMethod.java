package quaternary.youreanexpertharry.modules.actuallyAdditions;

import com.google.common.collect.ImmutableList;
import de.ellpeck.actuallyadditions.mod.blocks.BlockEmpowerer;
import de.ellpeck.actuallyadditions.mod.blocks.BlockDisplayStand;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.*;


public class EmpowererMethod extends AbstractHeckMethod {

    public EmpowererMethod() {super(4);}

    public static final Set<Heck.GoodItemStack> sanitySet = new HashSet<>();

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.actuallyadditions.Empowerer;");
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        for (EmpowererRecipe recipe : ActuallyAdditionsAPI.EMPOWERER_RECIPES) {
            if (recipe.getOutput() != null
                    && (new Heck.GoodItemStack(recipe.getOutput())).equals(new Heck.GoodItemStack(output))) {
                return String.format(
                        "Empowerer.removeRecipe(%s);\n",
                        stackToBracket(output)
                );
            }
        }
        return "";
    }

    @Override
    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        int energyCost = Heck.random.nextInt(Integer.MAX_VALUE / 2);
        int time = Heck.random.nextInt(Integer.MAX_VALUE / 4);
        return String.format(
                "Empowerer.addRecipe(%s, %s, %s, %s, %s, %s, %s);\n",
                stackToBracket(output),
                stackToBracket(inputs.get(0)),
                stackToBracket(inputs.get(1)),
                stackToBracket(inputs.get(2)),
                stackToBracket(inputs.get(3)),
                energyCost, time
        );
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(
                new ItemStack(BlockEmpowerer.getBlockById(0)),
                new ItemStack(BlockDisplayStand.getBlockById(0))
        );
    }

    @Override
    public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        List<ItemStack> recipeStacks = new ArrayList<>(4);
        Heck.GoodItemStack sanityItem = null;
        String b = null;

        boolean sanity = false;
        int attemptCount = 0;
        boolean success = true;

        while(!sanity) {
            recipeStacks.clear();
            attemptCount++;
            if(attemptCount > 250) {
                success = false;
                break;
            }
            for(int a = 0; a < this.inputCount; a++) {
                recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
            }
            sanityItem = new Heck.GoodItemStack(recipeStacks.get(0));

            sanity = this.sanityCheck(sanityItem);
        }

        if(success) {
            sanitySet.add(sanityItem);
            if(allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
            b = writeZenscript("youre_an_expert_harry_" + allHeck.recipeCount, outputGood.actualStack, recipeStacks);
        }

        return new MutablePair<>(new MutablePair<>(recipeStacks, b), success);
    }

    private boolean sanityCheck(Heck.GoodItemStack sanityItem) {
        return !sanitySet.contains(sanityItem);
    }
}

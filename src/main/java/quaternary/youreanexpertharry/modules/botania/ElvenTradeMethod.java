package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.etc.ShapelessStack;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.block.ModBlocks;

import java.util.*;

public class ElvenTradeMethod extends AbstractHeckMethod {

    public ElvenTradeMethod() {super(2);}

    public static Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

    public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {

        List<ItemStack> recipeStacks = new ArrayList<>(inputCount);
        HashSet<ShapelessStack> shapelessSet = new HashSet<>();
        String b = null;

        boolean sanity = false;
        int attemptCount = 0;
        boolean success = true;

        while (!(sanity)) {
            recipeStacks.clear();
            shapelessSet.clear();
            attemptCount++;
            if (attemptCount > 500) {
                success = false;
                break;
            }
            for(int a = 0; a < inputCount; a++) {
                recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
            }
            recipeStacks.forEach(is -> ShapelessStack.shapelessAdd(shapelessSet, is));

            YoureAnExpertHarry.LOGGER.info("Sanity-checking elven trade");
            YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
            sanity = this.sanityCheck(shapelessSet);
        }
        YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
        if (success) {
            sanitySet.add(shapelessSet);
            if (allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
            b = writeZenscript("youre_an_expert_harry_" + allHeck.recipeCount, outputGood.actualStack, recipeStacks);
        }

        return new MutablePair<>(new MutablePair<>(recipeStacks, b), new Boolean(success));

    }

    private boolean sanityCheck(HashSet<ShapelessStack> shapelessSet) {
        if (sanitySet.contains(shapelessSet)) return false;
        return true;
    }

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.botania.ElvenTrade;");
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        for (RecipeElvenTrade r : BotaniaAPI.elvenTradeRecipes) {
            if (r.getOutputs() != null && (new Heck.GoodItemStack(r.getOutputs().get(0))).equals(new Heck.GoodItemStack(output))) {
                return String.format(
                        "ElvenTrade.removeRecipe(%s);",
                        stackToBracket(output)
                );
            }
        }
        return ("");
    }

    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {

        return String.format(
                    "ElvenTrade.addRecipe([%s], %s);",
                    stackToBracket(output),
                    stacksToBracketedList(inputs)
        );

    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.pool), new ItemStack(ModBlocks.pylon, 1, 1),
                new ItemStack(ModBlocks.livingwood), new ItemStack(ModBlocks.livingwood, 1, 5),
                new ItemStack(ModBlocks.alfPortal));
    }
}

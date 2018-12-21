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
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.block.ModBlocks;

import java.util.*;

public class RunicAltarMethod extends AbstractHeckMethod {

    public RunicAltarMethod() {super(9);}

    public static Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

    public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        int inputSize = Heck.random.nextInt(9) + 1;
        List<ItemStack> recipeStacks = new ArrayList<>(inputSize);
        HashSet<ShapelessStack> shapelessSet = new HashSet<>();

        boolean sanity = false;

        while (!(sanity)) {
            recipeStacks.clear();
            shapelessSet.clear();
            for(int a = 0; a < inputSize; a++) {
                recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
            }
            recipeStacks.forEach(is -> ShapelessStack.shapelessAdd(shapelessSet, is));

            YoureAnExpertHarry.LOGGER.info("Sanity-checking runic altar");
            YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
            sanity = this.sanityCheck(shapelessSet);
        }
        YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
        sanitySet.add(shapelessSet);
        if (allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
        String b = writeZenscript("youre_an_expert_harry_" + allHeck.recipeCount, outputGood.actualStack, recipeStacks);

        return new MutablePair<>(new MutablePair<>(recipeStacks, b), new Boolean(true));

    }

    private boolean sanityCheck(HashSet<ShapelessStack> shapelessSet) {
        if (sanitySet.contains(shapelessSet)) return false;
        return true;
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        for (RecipeRuneAltar r : BotaniaAPI.runeAltarRecipes) {
            if (r.getOutput() != null && (new Heck.GoodItemStack(r.getOutput())).equals(new Heck.GoodItemStack(output))) {
                return String.format(
                        "RuneAltar.removeRecipe(%s);\n",
                        stackToBracket(output)
                );
            }
        }
        return ("");
    }

    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        int manaCost = Heck.random.nextInt(8) * 1000 + 5000;
        return String.format(
                "RuneAltar.addRecipe(%s, %s, %s);",
                stackToBracket(output),
                stacksToBracketedList(inputs),
                manaCost
        );
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.runeAltar), new ItemStack(ModBlocks.livingrock));
    }

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.botania.RuneAltar;");
    }

}

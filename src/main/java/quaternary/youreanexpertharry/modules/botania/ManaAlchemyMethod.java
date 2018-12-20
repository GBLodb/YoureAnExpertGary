package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.block.ModBlocks;

import java.util.*;

public class ManaAlchemyMethod extends AbstractHeckMethod {

    public ManaAlchemyMethod() {
        super(1);
    }

    public Pair<List<ItemStack>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        List<ItemStack> recipeStacks = new ArrayList<>(1);
        Heck.GoodItemStack sanityItem = null;

        boolean sanity = false;
        int attemptCount = 0;
        boolean success = true;

        while (!(sanity)) {
            recipeStacks.clear();
            attemptCount++;
            if (attemptCount > 250) {
                success = false;
                break;
            }
            for(int a = 0; a < this.inputCount; a++) {
                recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
            }
            sanityItem = new Heck.GoodItemStack(recipeStacks.get(0));

            YoureAnExpertHarry.LOGGER.info("Sanity-checking Botania alchemy");
            YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
            sanity = this.sanityCheck(sanityItem);
        }
        YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
        ManaInfusionMethod.sanitySet.add(sanityItem);

        return new MutablePair<>(recipeStacks, new Boolean(success));

    }

    private boolean sanityCheck(Heck.GoodItemStack sanityItem) {
        if (ManaInfusionMethod.sanitySet.contains(sanityItem)) return false;
        return true;
    }

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.empty();
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        for (RecipeManaInfusion r : BotaniaAPI.manaInfusionRecipes) {
            if (r.getOutput() != null && (new Heck.GoodItemStack(r.getOutput())).equals(new Heck.GoodItemStack(output))) {
                return String.format(
                        "ManaInfusion.removeRecipe(%s);",
                        stackToBracket(output)
                );
            }
        }
        return ("");
    }

    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        int manaCost = Heck.random.nextInt(7000) + 3000;
        return String.format(
                "ManaInfusion.addAlchemy(%s, %s, %s);",
                stackToBracket(output),
                stackToBracket(inputs.get(0)),
                manaCost
        );
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.pool), new ItemStack(ModBlocks.alchemyCatalyst));
    }

}

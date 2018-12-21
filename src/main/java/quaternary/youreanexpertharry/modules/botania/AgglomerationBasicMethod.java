package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.etc.ShapelessStack;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import java.util.*;

public class AgglomerationBasicMethod extends AbstractHeckMethod {

    public AgglomerationBasicMethod() {super(3);}

    public static Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

    public Pair<List<ItemStack>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        List<ItemStack> recipeStacks = new ArrayList<>(inputCount);
        HashSet<ShapelessStack> shapelessSet = new HashSet<>();

        boolean sanity = false;

        while (!(sanity)) {
            recipeStacks.clear();
            shapelessSet.clear();
            for(int a = 0; a < inputCount; a++) {
                while (true) {
                    ItemStack is = Heck.chooseItem(allHeck, outputGood, base);
                    if (!(recipeStacks.contains(is))) {
                        recipeStacks.add(is);
                        break;
                    }
                }
            }
            recipeStacks.forEach(is -> ShapelessStack.shapelessAdd(shapelessSet, is));

            YoureAnExpertHarry.LOGGER.info("Sanity-checking basic agglomeration");
            YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
            sanity = this.sanityCheck(shapelessSet);
        }
        YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
        sanitySet.add(shapelessSet);

        return new MutablePair<>(recipeStacks, new Boolean(true));

    }

    private boolean sanityCheck(HashSet<ShapelessStack> shapelessSet) {
        if (sanitySet.contains(shapelessSet)) return false;
        return true;
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        return ("");
    }

    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        int manaCost = Heck.random.nextInt(5) * 100000 + 100000;
        return String.format(
                "Agglomeration.addRecipe(AgglomerationRecipe.create().output(%s).inputs(%s).manaCost(%s));",
                stackToBracket(output),
                stacksToBracketedList(inputs),
                manaCost
        );
    }

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.botaniatweaks.AgglomerationRecipe;\nimport mods.botaniatweaks.Agglomeration;");
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.terraPlate), new ItemStack(ModBlocks.livingrock), new ItemStack(Blocks.LAPIS_BLOCK),
                new ItemStack(ModItems.spark));
    }

}

package quaternary.youreanexpertharry.modules.botania;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

public class AgglomerationAdvancedMethod extends AbstractHeckMethod {

    public AgglomerationAdvancedMethod() {super(5);}

    public static final Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

    public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        List<ItemStack> recipeStacks = new ArrayList<>(inputCount);
        HashSet<ShapelessStack> shapelessSet = new HashSet<>();
        String s = null;

        boolean sanity = false;
        boolean success = true;

        OUTER:
        while (!(sanity)) {
            recipeStacks.clear();
            shapelessSet.clear();
            for(int a = 0; a < 3; a++) {
                while (true) {
                    ItemStack is = Heck.chooseItem(allHeck, outputGood, base);
                    if (!(recipeStacks.contains(is))) {
                        recipeStacks.add(is);
                        break;
                    }
                }
            }
            int attemptCount = 0;
            for(int a = 0; a < 2; a++) {
                INNER:
                while (true) {
                    ItemStack is = Heck.chooseItem(allHeck, outputGood, base);
                    Item i = is.getItem();
                    if (is.getItem() instanceof ItemBlock) {
                        Block b = ((ItemBlock) i).getBlock();
                        if (b != null) {
                            if (b.getStateFromMeta(is.getMetadata()).getPropertyKeys().stream().noneMatch(p -> p instanceof PropertyDirection)) {
                                recipeStacks.add(is);
                                break;
                            }
                        }
                    }
                    attemptCount++;
                    if (attemptCount > 250) {
                        success = false;
                        recipeStacks.clear();
                        shapelessSet.clear();
                        break OUTER;
                    }
                }
            }
            recipeStacks.forEach(is -> ShapelessStack.shapelessAdd(shapelessSet, is));

            YoureAnExpertHarry.LOGGER.info("Sanity-checking advanced agglomeration;");
            YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
            sanity = this.sanityCheck(shapelessSet);
        }
        YoureAnExpertHarry.LOGGER.info("Sanity succeeded");

        if (success) {
            sanitySet.add(shapelessSet);
            s = writeZenscript("youre_an_expert_gary_" + allHeck.recipeCount, outputGood.actualStack, recipeStacks);
            if (allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
        }


        return new MutablePair<>(new MutablePair<>(recipeStacks, s), success);

    }

    private boolean sanityCheck(HashSet<ShapelessStack> shapelessSet) {
        return !sanitySet.contains(shapelessSet);
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        return ("");
    }

    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        int manaCost = Heck.random.nextInt(5) * 100000 + 100000;
        return String.format(
                "Agglomeration.addRecipe(AgglomerationRecipe.create().output(%s).inputs([%s, %s, %s]).manaCost(%s).multiblock("
                + "AgglomerationMultiblock.create().center(<botania:livingrock>).edge(%s).corner(%s)"
                + ".edgeReplace(<minecraft:lapis_block>).cornerReplace(<botania:livingrock>)));",
                stackToBracket(output),
                stackToBracket(inputs.get(0)),
                stackToBracket(inputs.get(1)),
                stackToBracket(inputs.get(2)),
                manaCost,
                stackToBracket(inputs.get(3)),
                stackToBracket(inputs.get(4))
        );
    }

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.botaniatweaks.AgglomerationMultiblock;");
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.terraPlate), new ItemStack(ModBlocks.livingrock), new ItemStack(Blocks.LAPIS_BLOCK),
                new ItemStack(ModItems.spark));
    }

}

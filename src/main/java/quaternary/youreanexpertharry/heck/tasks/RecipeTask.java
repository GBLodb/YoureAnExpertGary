package quaternary.youreanexpertharry.heck.tasks;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;
import quaternary.youreanexpertharry.settings.YAEHSettings;

import java.util.ArrayList;
import java.util.List;

public class RecipeTask implements IHeckTask {
    public Heck.GoodItemStack outputGood;
    AbstractHeckMethod method;
    int tier;
    HeckData allHeck;
    YAEHSettings settings;

    public RecipeTask(HeckData allHeck, YAEHSettings settings, int tier, Heck.GoodItemStack outputGood) {
        this.allHeck = allHeck;
        this.settings = settings;
        this.outputGood = outputGood;
        this.tier = tier;
    }

    public String execute() throws Heckception {
        method = Heck.chooseMethod(settings, tier, null);
        List<ItemStack> recipeStacks = new ArrayList<>();
        String zs = "";
        for (int i = 0; i < 6; i++) {
            Pair<Pair<List<ItemStack>, String>, Boolean> attempt;
            Pair<List<ItemStack>, Boolean> attemptOld;
            attempt = method.chooseInputs(allHeck, outputGood, tier == 0);
            if (attempt.getRight()) {
                recipeStacks = attempt.getLeft().getLeft();
                zs = attempt.getLeft().getRight();
                allHeck.usedMethods.add(method);
                break;
            }
            method = Heck.chooseMethod(settings, tier == 0 ? 1 : tier, method);
        }

        if (recipeStacks.size() == 0) throw new Heckception("ran out of possible recipes, somehow!");

        allHeck.usedMethods.add(method);

        System.out.println(method);

        String b = "//Recipe " +
                allHeck.recipeCount +
                '\n' +
                method.removeRecipe(outputGood.actualStack) +
                '\n' +
                zs +
                '\n';

        allHeck.recipeCount++;

        return b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RecipeTask) {
            return ((RecipeTask) obj).outputGood.equals(this.outputGood);
        } return false;
    }

    @Override
    public int hashCode() {
        return outputGood.hashCode();
    }
}

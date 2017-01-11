package wurmatron.viral.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSyringe extends Item {

    public static final String[] NAMES = new String[]{"Empty", "Filled", "Cure", "Immunity"};

    public ItemSyringe() {
        setCreativeTab(CreativeTabs.MISC);
        setHasSubtypes(true);
        setRegistryName("syringe");
        setMaxStackSize(1);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> sub) {
        for (int s = 0; s < NAMES.length; s++)
            sub.add(new ItemStack(item, 1, s));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if(stack.getItemDamage() < NAMES.length)
            return "item.syringe" + NAMES[stack.getItemDamage()];
        return "item.syringe.name";
    }
}

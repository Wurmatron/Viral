package wurmatron.viral.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import wurmatron.viral.Viral;

public class ItemSyringe extends Item {

  public ItemSyringe() {
    setCreativeTab(CreativeTabs.MISC);
    setHasSubtypes(true);
    setUnlocalizedName("syringe");
    setMaxStackSize(1);
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> sub) {
    if (tab == CreativeTabs.MISC)
      for (int s = 0; s < EnumType.values().length; s++)
        sub.add(new ItemStack(Viral.syringe, 1, s));
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    if (stack.getItemDamage() < EnumType.values().length)
      return "item.syringe" + EnumType.values()[stack.getItemDamage()].name;
    return "item.syringe.name";
  }

  public enum EnumType implements IStringSerializable {
    EMPTY(0, "Empty"),
    FILLED(1, "Filled"),
    CURE(2, "Cure"),
    IMMUNITY(3, "Immunity");

    public final int meta;
    public final String name;

    EnumType(final int meta, final String name) {
      this.meta = meta;
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }
  }
}

package wurmatron.viral.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import wurmatron.viral.Viral;

public class ItemSyringe extends Item {

  public ItemSyringe() {
    super(new Properties().group(ItemGroup.MISC).maxStackSize(1));
    setRegistryName("syringe");
  }

  @Override
  public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
    super.fillItemGroup(group, items);
    if (group == ItemGroup.MISC) {
      for (int s = 0; s < EnumType.values().length; s++) {
        ItemStack stack = new ItemStack(Viral.syringe, 1);
        stack.setCount(s);
        items.add(stack);
      }
    }
  }


  @Override
  public ITextComponent getDisplayName(ItemStack stack) {
    if (stack.getDamage() < EnumType.values().length) {
      return new TranslationTextComponent("item.syringe" + EnumType.values()[stack.getDamage()].name);
    }
    return new TranslationTextComponent("item.syringe.name");
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

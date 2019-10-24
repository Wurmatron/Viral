package wurmatron.viral.common.items;


import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemBasic extends Item {

  private String name;

  public ItemBasic(Properties properties,String name) {
    super(properties.group(ItemGroup.MISC));
    setRegistryName(name);
    this.name = name;
  }

  @Override
  public ITextComponent getDisplayName(ItemStack stack) {
    return new TranslationTextComponent( "item." + name + ".name");
  }

}

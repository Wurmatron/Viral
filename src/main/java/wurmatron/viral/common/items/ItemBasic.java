package wurmatron.viral.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBasic extends Item {

  private String name;

  public ItemBasic(String name) {
    setCreativeTab(CreativeTabs.MISC);
    setUnlocalizedName(name);
    this.name = name;
  }

  @Override
  public String getUnlocalizedName() {
    return "item." + name + ".name";
  }
}

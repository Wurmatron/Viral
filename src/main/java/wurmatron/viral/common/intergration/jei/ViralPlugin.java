package wurmatron.viral.common.intergration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;
import wurmatron.viral.Viral;

@JEIPlugin
public class ViralPlugin implements IModPlugin {

  @Override
  public void register(IModRegistry registry) {
    registry.addDescription(Viral.syringeFilled, "description.filledSyringe.name");
    registry.addDescription(Viral.syringeCure, "description.cureSyringe.name");
    registry.addDescription(Viral.syringeImunity, "description.imunitySyringe.name");
    registry.addDescription(new ItemStack(Viral.glowstick), "description.glowstick.name");
    registry.addDescription(new ItemStack(Viral.glowstick, 1, 1), "description.glowstick.name");
  }
}

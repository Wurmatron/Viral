package wurmatron.viral.common.intergration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import wurmatron.viral.Viral;

@JEIPlugin
public class ViralPlugin implements IModPlugin {

  @Override
  public void register(IModRegistry registry) {
    registry.addDescription(Viral.syringeFilled, "description.filledSyringe.name");
    registry.addDescription(Viral.syringeCure, "description.cureSyringe.name");
    registry.addDescription(Viral.syringeImunity, "description.imunitySyringe.name");
  }
}

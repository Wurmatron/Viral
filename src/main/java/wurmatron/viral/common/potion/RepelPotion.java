package wurmatron.viral.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wurmatron.viral.common.reference.Global;

public class RepelPotion extends Potion {

  private final ResourceLocation iconTexture =
      new ResourceLocation(Global.MODID, "textures/gui/repel.png");

  public RepelPotion(boolean isBadEffectIn, int liquidColor) {
    super(isBadEffectIn, liquidColor);
    setPotionName("effect.repel.name");
    setRegistryName(new ResourceLocation(Global.MODID, "repel"));
  }

  @Override
  public boolean shouldRenderHUD(PotionEffect effect) {
    return true;
  }

  @Override
  public boolean hasStatusIcon() {
    return true;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
    if (mc.currentScreen != null) {
      mc.getTextureManager().bindTexture(iconTexture);
      Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
    mc.getTextureManager().bindTexture(iconTexture);
    Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
  }
}

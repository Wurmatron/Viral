package wurmatron.viral.common.event;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.Viral;

public class GlowStickEvents {

  @SubscribeEvent
  public void onEntitySetTarget(LivingSetAttackTargetEvent e) {
    if (e.getTarget() != null && e.getTarget() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) e.getTarget();
      if (player.isPotionActive(Viral.repel)) {
        ((EntityLiving) e.getEntity()).setAttackTarget(null);
      }
    }
  }
}

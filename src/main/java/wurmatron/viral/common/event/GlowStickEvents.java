package wurmatron.viral.common.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import wurmatron.viral.common.potion.RepelEffect;

public class GlowStickEvents {

  @SubscribeEvent
  public void onEntitySetTarget(LivingSetAttackTargetEvent e) {
    if (e.getTarget() != null && e.getTarget() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) e.getTarget();
      if (player.isPotionActive(new RepelEffect())) {
        ((LivingEntity) e.getEntity()).setRevengeTarget(null);
      }
    }
  }
}

//package wurmatron.viral.common.event;
//
//import java.util.Random;
//import net.minecraft.particles.ParticleTypes;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import wurmatron.viral.Viral;
//import wurmatron.viral.common.capabilities.IViral;
//import wurmatron.viral.common.capabilities.ViralProvider;
//import wurmatron.viral.common.config.ConfigHandler;
//
//public class InteractEvent {
//
//  @SubscribeEvent
//  public void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
//    IViral status = e.getTarget().getCapability(ViralProvider.VIRAL, null).orElseGet(() -> {
//      return new IViral() {
//        @Override
//        public int status() {
//          return 0;
//        }
//
//        @Override
//        public void set(int status) {
//
//        }
//      };
//    });
//    if (e.getEntityPlayer().getHeldItemMainhand() != null) {
//      if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeEmpty)) {
//        if (status.status() == 1
//            && e.getEntityPlayer().world.rand.nextInt(100) < ConfigHandler.chanceToCollect) {
//          e.getItemStack().setDamage(1);
//        } else if (status.status() == 1 && !e.getEntityPlayer().world.isRemote) {
//          e.getEntityPlayer().sendMessage(new TranslationTextComponent("chat.collect.name"));
//          e.getEntityPlayer().inventory.deleteStack(e.getEntityPlayer().getHeldItemMainhand());
//        }
//      } else if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeFilled)) {
//        if (status.status() == 0) {
//          status.set(1);
//          if (!e.getEntityPlayer().isCreative()) {
//            e.getEntityPlayer().inventory.deleteStack(e.getEntityPlayer().getHeldItemMainhand());
//          }
//          for (int i = 0; i <= 20; i++) {
//            e.getTarget()
//                .world
//                .addParticle(
//                    ParticleTypes.DRAGON_BREATH,
//                    e.getTarget().posX,
//                    e.getTarget().posY,
//                    e.getTarget().posZ,
//                    new Random().nextDouble(),
//                    new Random().nextDouble() + 1,
//                    new Random().nextDouble());
//          }
//        }
//      } else if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeCure)) {
//        if (status.status() == 1) {
//          status.set(0);
//          if (!e.getEntityPlayer().isCreative()) {
//            e.getEntityPlayer().inventory.deleteStack(e.getEntityPlayer().getHeldItemMainhand());
//          }
//          for (int i = 0; i <= 20; i++) {
//            e.getTarget()
//                .world
//                .addParticle(
//                    ParticleTypes.DRAGON_BREATH,
//                    e.getTarget().posX,
//                    e.getTarget().posY,
//                    e.getTarget().posZ,
//                    new Random().nextDouble(),
//                    new Random().nextDouble() + 1,
//                    new Random().nextDouble());
//          }
//        }
//      } else if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeImunity)) {
//        if (status.status() == 0) {
//          status.set(2);
//          if (!e.getEntityPlayer().isCreative()) {
//            e.getEntityPlayer().inventory.deleteStack(e.getEntityPlayer().getHeldItemMainhand());
//          }
//          for (int i = 0; i <= 100; i++) {
//            e.getTarget()
//                .world
//                .addParticle(
//                    ParticleTypes.DRAGON_BREATH,
//                    e.getTarget().posX,
//                    e.getTarget().posY,
//                    e.getTarget().posZ,
//                    new Random().nextDouble(),
//                    new Random().nextDouble() + 1,
//                    new Random().nextDouble());
//          }
//        } else if (status.status() == 1 && !e.getEntityPlayer().world.isRemote) {
//          e.getEntityPlayer().sendMessage(new TranslationTextComponent("chat.cannotCure.name"));
//        }
//      }
//    }
//  }
//}

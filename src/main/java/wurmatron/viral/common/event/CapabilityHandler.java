//package wurmatron.viral.common.event;
//
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import wurmatron.viral.common.capabilities.ViralProvider;
//import wurmatron.viral.common.reference.Global;
//
//public class CapabilityHandler {
//
//  private static final ResourceLocation VIRAL = new ResourceLocation(Global.MODID, "viral");
//
//  @SubscribeEvent
//  public void attachCapability(AttachCapabilitiesEvent event) {
//    if (event.getObject() instanceof LivingEntity) {
//      event.addCapability(VIRAL, new ViralProvider());
//    }
//  }
//}

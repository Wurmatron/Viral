package wurmatron.viral.common.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.reference.Global;

public class CapabilityHandler {

    private static final ResourceLocation VIRAL = new ResourceLocation(Global.MODID, "viral");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent.Entity event) {
        if (event.getEntity() instanceof EntityLivingBase)
            event.addCapability(VIRAL, new ViralProvider());
    }
}

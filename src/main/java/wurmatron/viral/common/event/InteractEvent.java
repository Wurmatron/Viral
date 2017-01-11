package wurmatron.viral.common.event;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.Viral;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.utils.LogHandler;

import java.util.Random;

public class InteractEvent {

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
        IViral status = e.getTarget().getCapability(ViralProvider.VIRAL, null);
        if (e.getEntityPlayer().getHeldItemMainhand() != null)
            if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeEmpty)) {
                if (status.status() == 1)
                    e.getItemStack().setItemDamage(1);
            } else if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeFilled)) {
                if (status.status() == 0) {
                    status.set(1);
                    if (!e.getEntityPlayer().capabilities.isCreativeMode)
                        e.getEntityPlayer().inventory.deleteStack(e.getEntityPlayer().getHeldItemMainhand());
                    for (int i = 0; i <= 20; i++)
                        e.getTarget().worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, e.getTarget().posX, e.getTarget().posY, e.getTarget().posZ, new Random().nextDouble(), new Random().nextDouble() + 1, new Random().nextDouble());
                }
            } else if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeCure)) {
                if (status.status() == 1) {
                    status.set(0);
                    if (!e.getEntityPlayer().capabilities.isCreativeMode)
                        e.getEntityPlayer().inventory.deleteStack(e.getEntityPlayer().getHeldItemMainhand());
                    for (int i = 0; i <= 20; i++)
                        e.getTarget().worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, e.getTarget().posX, e.getTarget().posY, e.getTarget().posZ, new Random().nextDouble(), new Random().nextDouble() + 1, new Random().nextDouble());
                }
            } else if (e.getEntityPlayer().getHeldItemMainhand().isItemEqual(Viral.syringeImunity)) {
                if (status.status() == 0) {
                    status.set(2);
                    if (!e.getEntityPlayer().capabilities.isCreativeMode)
                        e.getEntityPlayer().inventory.deleteStack(e.getEntityPlayer().getHeldItemMainhand());
                    for (int i = 0; i <= 100; i++)
                        e.getTarget().worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, e.getTarget().posX, e.getTarget().posY, e.getTarget().posZ, new Random().nextDouble(), new Random().nextDouble() + 1, new Random().nextDouble());
                } else if (status.status() == 1 && !e.getEntityPlayer().worldObj.isRemote)
                    e.getEntityPlayer().addChatComponentMessage(new TextComponentTranslation("chat.cannotCure.name"));
            }
    }
}

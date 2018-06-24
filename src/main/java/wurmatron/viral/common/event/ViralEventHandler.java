package wurmatron.viral.common.event;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.config.ConfigHandler;
import wurmatron.viral.common.utils.LogHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class ViralEventHandler {

  private static int radius = ConfigHandler.radius;
  private Random rand = new Random();
  private int counter = 0;
  private float passiveDamage = new BigDecimal(ConfigHandler.passiveDamage).floatValue();

  @SubscribeEvent
  public void onEntitySpawn(EntityJoinWorldEvent e) {
    if (!(e.getEntity() instanceof EntityPlayer) && e.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase entity = (EntityLivingBase) e.getEntity();
      IViral status = entity.getCapability(ViralProvider.VIRAL, null);
      if (getChancePercentage() >= 100) {
        status.set(1);
      } else if (rand.nextInt(getChancePercentage()) == 0) {
        status.set(1);
      }
    }
  }

  // TODO Imunity on dice roll
  @SubscribeEvent
  public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
    IViral status = e.getEntityLiving().getCapability(ViralProvider.VIRAL, null);
    if (status.status() == 1) {
      if (ConfigHandler.particles > 0) {
        if (ConfigHandler.particles == 1 && counter == 10) {
          e.getEntityLiving().world
              .spawnParticle(EnumParticleTypes.DRAGON_BREATH, e.getEntityLiving().posX,
                  e.getEntityLiving().posY, e.getEntityLiving().posZ,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5,
                  (rand.nextDouble() * (double) e.getEntityLiving().height) / 5,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5);
          counter = 0;
        } else if (ConfigHandler.particles == 2) {
          e.getEntityLiving().world
              .spawnParticle(EnumParticleTypes.DRAGON_BREATH, e.getEntityLiving().posX,
                  e.getEntityLiving().posY, e.getEntityLiving().posZ,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5,
                  (rand.nextDouble() * (double) e.getEntityLiving().height) / 5,
                  ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5);
        } else if (ConfigHandler.particles != 0 && counter >= 0) {
          counter++;
        }
      }
      if (!e.getEntityLiving().world.isRemote
          && e.getEntityLiving().world.getWorldTime() % ConfigHandler.time == 0) {
        spreadViral(e.getEntityLiving());
      }
      if (!(e.getEntityLiving() instanceof EntityAnimal)) {
        e.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(
            e.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                .getBaseValue() * 2);
        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(5), 100));
        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(11), 100));
        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(1), 100, 2));
      } else if (e.getEntityLiving() instanceof EntityAnimal && ConfigHandler.infectPassive) {
        e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 4));
        if (ConfigHandler.hurtPassive
            && ((EntityAnimal) e.getEntityLiving()).world.getWorldTime() % 1000 == 0
            && passiveDamage > 0) {
          e.getEntityLiving().attackEntityFrom(DamageSource.MAGIC, passiveDamage);
        }
      } else {
        status.set(0);
      }
    }
  }

  private void spreadViral(EntityLivingBase entity) {
    List<Entity> area = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
        entity.getEntityBoundingBox().expand(radius, radius, radius));
    if (area.size() > 0) {
      for (Entity e : area) {
        if (!(e instanceof EntityPlayer) && e instanceof EntityLivingBase) {
          if (e instanceof IAnimals && !ConfigHandler.infectPassive) {
            return;
          }
          EntityLivingBase ent = (EntityLivingBase) e;
          if (!ent.world.isRemote && rand.nextInt(getChancePercentage() - 1) == 0) {
            IViral status = ent.getCapability(ViralProvider.VIRAL, null);
            if (status.status() == 0) {
              status.set(1);
              LogHandler.debug(
                  "Infected " + ent.getDisplayName().getUnformattedComponentText() + " X: "
                      + ent.posX + " , Y: " + ent.posY + " , Z: " + ent.posZ);
            }
          }
        }
      }
    }
  }

  private int getChancePercentage() {
    return (int) (ConfigHandler.chance * 100);
  }

  @SideOnly(Side.CLIENT)
  private static List<Entity> layers = new ArrayList<>();

  @SubscribeEvent
  public void onRenderEntity(RenderLivingEvent.Pre e) {
    if (layers.size() <= 0 || !layers.contains(e.getEntity())) {
      e.getRenderer().addLayer(new LayerRenderer() {
        @Override
        public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing,
            float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
            float headPitch, float scale) {
          GlStateManager.pushMatrix();
          GlStateManager.rotate(180, 0, 0, 1);
          GlStateManager.scale(0.6, 0.6, 0.6);
          GlStateManager.rotate((ageInTicks) / 20.0F * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
          GlStateManager.translate(0, (e.getEntity().height - 0.3) + (Math.sin(ageInTicks/20))/3, 0);
          Minecraft.getMinecraft().getRenderItem()
              .renderItem(new ItemStack(Items.WHEAT), ItemCameraTransforms.TransformType.FIXED);
          GlStateManager.popMatrix();
        }

        @Override
        public boolean shouldCombineTextures() {
          return true;
        }
      });
      layers.add(e.getEntity());
    }
  }
}

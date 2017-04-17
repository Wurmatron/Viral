package wurmatron.viral.common.event;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.config.Settings;
import wurmatron.viral.common.utils.LogHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class ViralEventHandler {

		private static int    radius        = Settings.range;
		private        Random rand          = new Random();
		private        int    counter       = 0;
		private        float  passiveDamage = new BigDecimal(Settings.passiveDamage).floatValue();

		@SubscribeEvent
		public void onEntitySpawn(EntityJoinWorldEvent e) {
				if (!(e.getEntity() instanceof EntityPlayer) && e.getEntity() instanceof EntityLivingBase) {
						EntityLivingBase entity = (EntityLivingBase) e.getEntity();
						IViral           status = entity.getCapability(ViralProvider.VIRAL, null);
						if (getChancePercentage() >= 1) status.set(1);
						else if (rand.nextInt(getChancePercentage()) == 0) status.set(1);
				}
		}

		@SubscribeEvent
		public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
				IViral status = e.getEntityLiving().getCapability(ViralProvider.VIRAL, null); if (status.status() == 1) {
						if (Settings.particles > 0) if (Settings.particles == 1 && counter == 10) {
								e.getEntityLiving().worldObj.spawnParticle(EnumParticleTypes.DRAGON_BREATH, e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5, (rand.nextDouble() * (double) e.getEntityLiving().height) / 5, ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5);
								counter = 0;
						} else if (Settings.particles == 2)
								e.getEntityLiving().worldObj.spawnParticle(EnumParticleTypes.DRAGON_BREATH, e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5, (rand.nextDouble() * (double) e.getEntityLiving().height) / 5, ((rand.nextDouble() - 0.5D) * (double) e.getEntityLiving().width) / 5);
						else if (Settings.particles != 0 && counter >= 0) counter++;
						if (!e.getEntityLiving().worldObj.isRemote && e.getEntityLiving().worldObj.getWorldTime() % Settings.time == 0)
								spreadViral(e.getEntityLiving()); if (!(e.getEntityLiving() instanceof EntityAnimal)) {
								e.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(e.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() * 2);
								e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(5), 100));
								e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(11), 100));
								e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(1), 100, 2));
						} else if (e.getEntityLiving() instanceof EntityAnimal && Settings.infectPassive) {
								e.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 4));
								if (Settings.hurtPassive && e.getEntityLiving().worldObj.getWorldTime() % 1000 == 0 && passiveDamage > 0)
										e.getEntityLiving().attackEntityFrom(DamageSource.magic, passiveDamage);
						} else status.set(0);
				}
		}

		private void spreadViral(EntityLivingBase entity) {
				List<Entity> area = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().expand(radius, radius, radius));
				if (area.size() > 0) {
						for (Entity e : area) {
								if (!(e instanceof EntityPlayer) && e instanceof EntityLivingBase) {
										if (e instanceof IAnimals && !Settings.infectPassive) return; EntityLivingBase ent = (EntityLivingBase) e;
										if (!ent.worldObj.isRemote && rand.nextInt(getChancePercentage()) == 0) {
												IViral status = ent.getCapability(ViralProvider.VIRAL, null); if (status.status() == 0) {
														status.set(1);
														LogHandler.debug("Infected " + ent.getDisplayName().getUnformattedComponentText() + " X: " + ent.posX + " , Y: " + ent.posY + " , Z: " + ent.posZ);
												}
										}
								}
						}
				}
		}

		private int getChancePercentage() {
				if (Settings.chance < 1) if (Settings.chance >= 0.1) return 100 - (int) (Settings.chance * 100);
				else if (Settings.chance >= 0.01) return 1000 - (int) (Settings.chance * 1000); return 1;
		}
}

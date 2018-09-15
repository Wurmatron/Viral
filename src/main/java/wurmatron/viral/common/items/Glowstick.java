package wurmatron.viral.common.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.Viral;

public class Glowstick extends Item {

  public Glowstick() {
    setCreativeTab(CreativeTabs.MISC);
    setHasSubtypes(true);
    setUnlocalizedName("glowstick");
    setMaxStackSize(1);
  }

  @Override
  public void onCreated(ItemStack stack, World world, EntityPlayer player) {
    super.onCreated(stack, world, player);
    NBTTagCompound data = new NBTTagCompound();
    data.setInteger("time", 0);
    stack.setTagCompound(data);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,
      EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.hasTagCompound() && stack.getTagCompound().hasKey("time")
        && stack.getTagCompound().getInteger("time") > 0) {
      stack.setItemDamage(1);
    } else {
      NBTTagCompound data = new NBTTagCompound();
      data.setInteger("time", 100);
      stack.setTagCompound(data);
    }
    return super.onItemRightClick(world, player, hand);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tip,
      ITooltipFlag flag) {
    super.addInformation(stack, world, tip, flag);
    if (stack.hasTagCompound() && stack.getTagCompound().hasKey("time")) {
      tip.add(I18n.translateToLocal("tooltip.timeRemaining.name")
          .replaceAll("%TIME%", "" + stack.getTagCompound().getInteger("time")));
    }
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == this.getCreativeTab()) {
      items.add(create(180));
      items.add(create(300));
      items.add(create(600));
    }
  }

  public ItemStack create(int time) {
    ItemStack glowstick = new ItemStack(this, 1, 0);
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("time", time);
    glowstick.setTagCompound(nbt);
    return glowstick;
  }


  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int slot,
      boolean isSelected) {
    super.onUpdate(stack, world, entity, slot, isSelected);
    if (entity instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) entity;
      if (stack.getItemDamage() == 1 && stack.hasTagCompound() && stack.getTagCompound()
          .hasKey("time") && stack.getTagCompound().getInteger("time") > 0) {
        int timeLeft = stack.getTagCompound().getInteger("time");
        if (timeLeft > 0) {
          if (!entity.world.isRemote && entity.world.getWorldTime() % 20 == 0) {
            stack.getTagCompound()
                .setInteger("time", stack.getTagCompound().getInteger("time") - 1);
          }
          player.addPotionEffect(new PotionEffect(Viral.repel, 100, 0));
        }
      } else if (stack.hasTagCompound() && stack.getTagCompound().hasKey("time")
          && stack.getTagCompound().getInteger("time") <= 0) {
        player.inventory.deleteStack(stack);
        player.inventory.addItemStackToInventory(new ItemStack(Viral.glowstickBroken));
      }
    }
  }

  @SubscribeEvent
  public void onEntitySetTarget(LivingSetAttackTargetEvent e) {
    if (e.getTarget() != null && e.getTarget() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) e.getTarget();
      if (player.isPotionActive(Viral.repel)) {
        ((EntityLiving) e.getEntity()).setAttackTarget(null);
      }
    }
  }

  @Override
  public String getUnlocalizedName() {
    return "item.glowstick.name";
  }
}

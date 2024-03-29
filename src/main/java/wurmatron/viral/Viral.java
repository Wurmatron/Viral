package wurmatron.viral;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wurmatron.viral.client.proxy.ClientProxy;
import wurmatron.viral.common.blocks.ViralInterdictionTorch;
import wurmatron.viral.common.blocks.ViralInterdictionTorchInverted;
import wurmatron.viral.common.blocks.ViralShield;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralData;
import wurmatron.viral.common.capabilities.ViralStorage;
import wurmatron.viral.common.event.CapabilityHandler;
import wurmatron.viral.common.event.GlowStickEvents;
import wurmatron.viral.common.event.InteractEvent;
import wurmatron.viral.common.event.ViralEventHandler;
import wurmatron.viral.common.items.Glowstick;
import wurmatron.viral.common.items.ItemBasic;
import wurmatron.viral.common.items.ItemSyringe;
import wurmatron.viral.common.potion.RepelPotion;
import wurmatron.viral.common.proxy.CommonProxy;
import wurmatron.viral.common.reference.Global;
import wurmatron.viral.common.reference.Registry;
import wurmatron.viral.common.utils.LogHandler;

@Mod(modid = Global.MODID, name = Global.NAME, version = Global.VERSION)
public class Viral {

  // Items
  public static final ItemSyringe syringe = new ItemSyringe();
  public static final ItemStack syringeEmpty = new ItemStack(syringe, 1, 0);
  public static final ItemStack syringeFilled = new ItemStack(syringe, 1, 1);
  public static final ItemStack syringeCure = new ItemStack(syringe, 1, 2);
  public static final ItemStack syringeImunity = new ItemStack(syringe, 1, 3);
  public static final Glowstick glowstick = new Glowstick();
  public static final Item glowstickBroken = new ItemBasic("glowstickBroken").setMaxStackSize(4);
  public static final Item mobMash = new ItemBasic("mobMash");
  // Blocks
  public static final ViralInterdictionTorch torchInterdiction = new ViralInterdictionTorch();
  public static final ViralInterdictionTorchInverted torchInterdictionInverted =
      new ViralInterdictionTorchInverted();
  public static final ViralShield shield = new ViralShield();
  // Potions
  public static RepelPotion repel = new RepelPotion(false, 0);

  @Mod.Instance(Global.MODID)
  public static Viral instance;

  @SidedProxy(serverSide = Global.COMMONPROXY, clientSide = Global.CLIENTPROXY)
  public static CommonProxy proxy;

  @Mod.EventHandler
  public void onPreInit(FMLPreInitializationEvent e) {
    Registry.registerItem(syringe, syringe.getUnlocalizedName().substring(5));
    Registry.registerBlock(torchInterdiction, torchInterdiction.getUnlocalizedName().substring(5));
    Registry.registerBlock(torchInterdictionInverted, "torchInterdictionInverted");
    Registry.registerBlock(shield, "shield");
    Registry.registerItem(glowstick, "glowstick");
    Registry.registerItem(glowstickBroken, "glowstickBroken");
    Registry.registerItem(mobMash, "mobMash");
    Registry.registerPotion(repel);
    MinecraftForge.EVENT_BUS.register(new Registry());
    MinecraftForge.EVENT_BUS.register(new ClientProxy());
    MinecraftForge.EVENT_BUS.register(new GlowStickEvents());
  }

  @Mod.EventHandler
  public void onInit(FMLInitializationEvent e) {
    CapabilityManager.INSTANCE.register(IViral.class, new ViralStorage(), ViralData.class);
    MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
    MinecraftForge.EVENT_BUS.register(new ViralEventHandler());
    MinecraftForge.EVENT_BUS.register(new InteractEvent());
    proxy.register();
  }

  @Mod.EventHandler
  public void onPostInit(FMLPostInitializationEvent e) {
    LogHandler.info("Adding Recipes");
    GameRegistry.addSmelting(Viral.syringeFilled, Viral.syringeEmpty, 0f);
  }
}

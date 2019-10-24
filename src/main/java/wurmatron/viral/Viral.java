package wurmatron.viral;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wurmatron.viral.common.config.ConfigHolder;
import wurmatron.viral.common.event.GlowStickEvents;
import wurmatron.viral.common.items.Glowstick;
import wurmatron.viral.common.items.ItemBasic;
import wurmatron.viral.common.items.ItemSyringe;
import wurmatron.viral.common.potion.RepelEffect;
import wurmatron.viral.common.potion.RepelPotion;
import wurmatron.viral.common.reference.Global;
import wurmatron.viral.common.reference.Registry;

@Mod(Global.MODID)
public class Viral {

  public static final Logger LOGGER = LogManager.getLogger();

  // Items
  public static final ItemSyringe syringe = new ItemSyringe();
  public static final ItemStack syringeEmpty = new ItemStack(syringe, 1);

  public static final ItemStack syringeFilled = new ItemStack(syringe, 1);
  public static final ItemStack syringeCure = new ItemStack(syringe, 1);
  public static final ItemStack syringeImmunity = new ItemStack(syringe, 1);
  public static final Glowstick glowstick = new Glowstick();
  public static final Item glowstickBroken = new ItemBasic(new Properties().maxStackSize(4), "glowstick_broken");
  public static final Item mobMash = new ItemBasic(new Properties(), "mob_mash");

  public Viral() {
    // Basic Setup
    LOGGER.info("Loading Viral " + Global.VERSION);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    MinecraftForge.EVENT_BUS.register(this);
    // Item Setup
    syringeFilled.setDamage(1);
    syringeCure.setDamage(2);
    syringeImmunity.setDamage(3);
    MinecraftForge.EVENT_BUS.register(new GlowStickEvents());
    // Config
    ModLoadingContext loadContext = ModLoadingContext.get();
    loadContext.registerConfig(Type.CLIENT, ConfigHolder.CLIENT_SPEC);
    loadContext.registerConfig(Type.SERVER, ConfigHolder.SERVER_SPEC);
    //  Potion Setup
    Registry.potions.add(new RepelPotion(new EffectInstance(new RepelEffect())));
  }

  public void setup(FMLCommonSetupEvent e) {
  }

  public void setupClient(FMLClientSetupEvent e) {

  }

  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent e) {

  }

//  // Blocks
//  public static final ViralInterdictionTorch torchInterdiction = new ViralInterdictionTorch();
//  public static final ViralInterdictionTorchInverted torchInterdictionInverted =
//      new ViralInterdictionTorchInverted();
//  public static final ViralShield shield = new ViralShield();
//  // Potions
//  public static RepelPotion repel = new RepelPotion(false, 0);
//
//  @Mod.Instance(Global.MODID)
//  public static Viral instance;
//
//  @SidedProxy(serverSide = Global.COMMONPROXY, clientSide = Global.CLIENTPROXY)
//  public static CommonProxy proxy;
//
//  @Mod.EventHandler
//  public void onPreInit(FMLPreInitializationEvent e) {
//    Registry.registerItem(syringe, syringe.getUnlocalizedName().substring(5));
//    Registry.registerBlock(torchInterdiction, torchInterdiction.getUnlocalizedName().substring(5));
//    Registry.registerBlock(torchInterdictionInverted, "torchInterdictionInverted");
//    Registry.registerBlock(shield, "shield");
//    Registry.registerItem(glowstick, "glowstick");
//    Registry.registerItem(glowstickBroken, "glowstickBroken");
//    Registry.registerItem(mobMash, "mobMash");
//    Registry.registerPotion(repel);
//    MinecraftForge.EVENT_BUS.register(new Registry());
//    MinecraftForge.EVENT_BUS.register(new ClientProxy());
//    MinecraftForge.EVENT_BUS.register(new GlowStickEvents());
//  }
//
//  @Mod.EventHandler
//  public void onInit(FMLInitializationEvent e) {
//    CapabilityManager.INSTANCE.register(IViral.class, new ViralStorage(), ViralData.class);
//    MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
//    MinecraftForge.EVENT_BUS.register(new ViralEventHandler());
//    MinecraftForge.EVENT_BUS.register(new InteractEvent());
//    proxy.register();
//  }
//
//  @Mod.EventHandler
//  public void onPostInit(FMLPostInitializationEvent e) {
//    LogHandler.info("Adding Recipes");
//    GameRegistry.addSmelting(Viral.syringeFilled, Viral.syringeEmpty, 0f);
//  }
}

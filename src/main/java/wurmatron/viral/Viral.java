package wurmatron.viral;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import wurmatron.viral.common.blocks.ViralInterdictionTorch;
import wurmatron.viral.common.blocks.ViralInterdictionTorchInverted;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralData;
import wurmatron.viral.common.capabilities.ViralStorage;
import wurmatron.viral.common.config.ConfigHandler;
import wurmatron.viral.common.config.Settings;
import wurmatron.viral.common.event.CapabilityHandler;
import wurmatron.viral.common.event.InteractEvent;
import wurmatron.viral.common.event.ViralEventHandler;
import wurmatron.viral.common.items.ItemSyringe;
import wurmatron.viral.common.proxy.CommonProxy;
import wurmatron.viral.common.reference.Global;
import wurmatron.viral.common.utils.LogHandler;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, guiFactory = Global.GUIFACTORY)
public class Viral {

	public static final ItemSyringe syringe = new ItemSyringe ();
	public static final ItemStack syringeEmpty = new ItemStack (syringe,1,0);
	public static final ItemStack syringeFilled = new ItemStack (syringe,1,1);
	public static final ItemStack syringeCure = new ItemStack (syringe,1,2);
	public static final ItemStack syringeImunity = new ItemStack (syringe,1,3);
	public static final ViralInterdictionTorch torchInterdiction = new ViralInterdictionTorch ();
	public static final ViralInterdictionTorchInverted torchInterdictionInverted = new ViralInterdictionTorchInverted ();
	@Mod.Instance (Global.MODID)
	public static Viral instance;
	@SidedProxy (serverSide = Global.COMMONPROXY, clientSide = Global.CLIENTPROXY)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent e) {
		ConfigHandler.preInit (e);
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent e) {
		CapabilityManager.INSTANCE.register (IViral.class,new ViralStorage (),ViralData.class);
		MinecraftForge.EVENT_BUS.register (new CapabilityHandler ());
		MinecraftForge.EVENT_BUS.register (new ViralEventHandler ());
		MinecraftForge.EVENT_BUS.register (new InteractEvent ());
		GameRegistry.register (syringe);
		GameRegistry.registerWithItem (torchInterdiction);
		GameRegistry.registerWithItem (torchInterdictionInverted);
		if (Settings.recipes) {
			LogHandler.info ("Adding Recipes");
			GameRegistry.addRecipe (new ShapedOreRecipe (Viral.syringeEmpty," A ","IGI"," S ",'A',new ItemStack (Items.ARROW),'I',"ingotIron",'G',"blockGlass",'S',"stickWood"));
			GameRegistry.addSmelting (Viral.syringeFilled,Viral.syringeEmpty,0f);
			GameRegistry.addRecipe (new ShapelessOreRecipe (Viral.syringeCure,Viral.syringeFilled,"dustGlowstone","treeSapling","ingotIron"));
			GameRegistry.addRecipe (new ShapelessOreRecipe (Viral.syringeImunity,Viral.syringeCure,new ItemStack (Items.GOLDEN_APPLE,1,0),Items.DIAMOND));
			GameRegistry.addRecipe (new ShapedOreRecipe (Viral.torchInterdiction,"DXD","XAX"," A ",'D',"gemDiamond",'X',Viral.syringeImunity,'A',Items.NETHERBRICK));
			GameRegistry.addRecipe (new ShapelessOreRecipe (Viral.torchInterdictionInverted,Viral.torchInterdiction,Viral.syringeEmpty));
		}
		proxy.register ();
	}
}

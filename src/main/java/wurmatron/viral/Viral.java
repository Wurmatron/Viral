package wurmatron.viral;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wurmatron.viral.common.config.ConfigHandler;
import wurmatron.viral.common.proxy.CommonProxy;
import wurmatron.viral.common.refrence.Global;

@Mod(modid = Global.MODID, name = Global.NAME, version = Global.VERSION, guiFactory = Global.GUIFACTORY)
public class Viral {

		@Mod.Instance(Global.MODID)
		public static Viral instance;

		@SidedProxy (serverSide = Global.COMMONPROXY, clientSide = Global.CLIENTPROXY)
		public static CommonProxy proxy;

		@Mod.EventHandler
		public void onPreInit(FMLPreInitializationEvent e) {
				ConfigHandler.preInit(e);
		}
}

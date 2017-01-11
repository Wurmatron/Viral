package wurmatron.viral.client.proxy;

import net.minecraftforge.common.MinecraftForge;
import wurmatron.viral.common.config.ConfigHandler;
import wurmatron.viral.common.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void register() {
        super.register();
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }
}

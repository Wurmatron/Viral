package wurmatron.viral.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ViralStorage implements Capability.IStorage<IViral> {

    @Override
    public NBTBase writeNBT(Capability<IViral> capability, IViral instance, EnumFacing side) {
        return new NBTTagInt(instance.status());
    }

    @Override
    public void readNBT(Capability<IViral> capability, IViral instance, EnumFacing side, NBTBase nbt) {
        instance.set(((NBTTagInt) nbt).getInt());
    }
}

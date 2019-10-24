package wurmatron.viral.common.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class ViralStorage implements Capability.IStorage<IViral> {

  @Override
  public INBT writeNBT(Capability<IViral> capability, IViral instance, Direction side) {
    return new IntNBT(instance.status());
  }

  @Override
  public void readNBT(
      Capability<IViral> capability, IViral instance, Direction side, INBT nbt) {
    instance.set(((IntNBT) nbt).getInt());
  }
}

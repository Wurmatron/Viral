package io.wurmatron.viral.api.capabilities;


import javax.annotation.Nullable;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ViralStorage implements IStorage<IViral> {

  @Nullable
  @Override
  public INBT writeNBT(Capability<IViral> capability, IViral instance, Direction side) {
    return new IntArrayNBT(new int[]{instance.type(), instance.status()});
  }

  @Override
  public void readNBT(Capability<IViral> capability, IViral instance, Direction side,
      INBT nbt) {
    int[] viralData = ((IntArrayNBT) nbt).getIntArray();
    instance.setType(viralData[0]);
    instance.set(viralData[1]);
  }
}

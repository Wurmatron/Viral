package io.wurmatron.viral.api;

import io.wurmatron.viral.api.capabilities.IViral;
import io.wurmatron.viral.api.capabilities.ViralProvider;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public class ViralAPI {

  public static IViral getViral(LivingEntity living) {
    LazyOptional<IViral> VIRAL = living.getCapability(ViralProvider.VIRAL, null);
    IViral viral = null;
    try {
      viral = VIRAL.orElseThrow(new NonNullSupplier<Exception>() {
        @Nonnull
        @Override
        public Exception get() {
          return new NoSuchElementException();
        }
      });
    } catch (Exception f) {
    }
    return viral;
  }

  public static boolean hasViral(LivingEntity living) {
    return getViral(living) != null;
  }

}

package io.wurmatron.viral.api.capabilities;

import java.util.concurrent.Callable;

public class ViralFactory  implements Callable<IViral> {

  @Override
  public IViral call() throws Exception {
    return new Viral();
  }
}

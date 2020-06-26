package io.wurmatron.viral.api.capabilities;

public class Viral implements IViral {

  private int type;
  private int status;

  public Viral() {
    this.type = -1;
    this.status = 0;
  }

  @Override
  public int type() {
    return type;
  }

  @Override
  public void setType(int type) {
    this.type = type;
  }

  @Override
  public int status() {
    return status;
  }

  @Override
  public void set(int status) {
    this.status = status;
  }
}

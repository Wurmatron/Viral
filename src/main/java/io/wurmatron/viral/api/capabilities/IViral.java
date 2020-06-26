package io.wurmatron.viral.api.capabilities;

/**
 * Interface used for the creation of the Viral Capabilities
 */
public interface IViral {

  /**
   * @return the type of viral this is
   */
  int type();

  /**
   * @param type ID of the viral
   */
  void setType(int type);

  /**
   * Get the current status of the viral
   */
  int status();

  /**
   * @param status new status of the viral
   */
  void set(int status);

}

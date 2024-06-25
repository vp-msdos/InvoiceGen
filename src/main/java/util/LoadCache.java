package util;

public class LoadCache extends Thread {
   public void run() {
      PopulateProperties.getInstance();
      PopulateProperties.fillPropertiesInMap();
   }
}

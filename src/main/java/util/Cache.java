package util;

public class Cache {
   public static synchronized String getValueFromCache(String key) {
      PopulateProperties.getInstance();
      return (String)PopulateProperties.getPropMap().get(key);
   }
}

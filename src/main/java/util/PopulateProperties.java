package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PopulateProperties {
   static PopulateProperties popProp;
   static Map<String, String> propMap;

   private PopulateProperties() {
   }

   public static PopulateProperties getInstance() {
      if (popProp == null) {
         popProp = new PopulateProperties();
      }

      return popProp;
   }

   public static void fillPropertiesInMap() {
      propMap = new HashMap();

      try {
         InputStream fileInput = popProp.getClass().getResourceAsStream("/invgen.properties");
         Properties properties = new Properties();
         properties.load(fileInput);
         fileInput.close();
         Enumeration enuKeys = properties.keys();

         while(enuKeys.hasMoreElements()) {
            String key = (String)enuKeys.nextElement();
            String value = properties.getProperty(key);
            System.out.println(key + ": " + value);
            propMap.put(key, value);
         }
      } catch (FileNotFoundException var5) {
         var5.printStackTrace();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public static Map<String, String> getPropMap() {
      return propMap;
   }
}

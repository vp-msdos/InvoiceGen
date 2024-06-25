package jaxbutil;

import data.UserInformation;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil {
   public static int MAX_SCORE_MULTIPLICATION = 5;
   public static final String INTEREST_CT_ID = "13";
   public static final String INTEREST_K_ID = "20";
   public static final int HIGHLY_COMPETITIVE = 1;
   public static final int COMPETITIVE = 2;
   public static final int NOT_SO_COMPETITIVE = 3;
   public static final String FIRST_PRIZE_1 = "1st prize";
   public static final String FIRST_PRIZE_2 = "first prize";
   private static final String XML_INPUT_FOLDER_PATH = "C:/EtashaCareerHighway/Secured/Input/XML/";
   private static final String IMAGE_INPUT_FOLDER_PATH = "C:/EtashaCareerHighway/Secured/Input/Images/Image_Repository/";
   private static final String XML_OUTPUT_FOLDER_PATH = "C:/EtashaCareerHighway/Secured/Output/XML/";
   private static final String CITY_INFO_FILE_NAME = "ECH_City_Info";
   private static final String OCCUPATION_INFO_FILE_NAME = "ECH_Occupation_Info";
   private static final String STREAM_INFO_FILE_NAME = "ECH_Stream_Info";
   private static final String CAREER_INFO_FILE_NAME = "ECH_Career_Info";
   private static final String INTEREST_INFO_FILE_NAME = "ECH_Interest_Info";
   private static final String SEGMENT_INFO_FILE_NAME = "ECH_Segment_Info";
   private static final String ECH_USER = "ECH_User";
   private static final String USER_ADJECTIVE = "ECH_User_Adjective";
   private static final String USER_HIGHLIGHTE = "ECH_User_Highlight";
   private static final String USER_ROLEMODEL = "ECH_User_RoleModel";
   private static final String USER_DREAMS = "ECH_User_Dream";
   private static final String USER_FAVSUBJECT = "ECH_User_Fav_Subject";
   private static final String USER_ACHIEVEMENT = "ECH_User_Achievement";
   private static final String USER_CAREER_OPTION = "ECH_User_Career_Option";
   private static final String USER_CHOSEN_CAREER_OPTION = "ECH_User_Chosen_Career_Option";
   private static final String USER_ACTION_PLAN = "ECH_User_Action_Plan";
   private static final String USER_CATEGORY_SCORE = "ECH_User_Category_Score";
   private static final String USER_INTEREST_SCORE = "ECH_User_Interest_Score";
   private static final String USER_ASSESSMENT = "ECH_User_Assessment";
   private static final String USER_ASSESSMENT_SUBMISSION = "ECH_User_Assessment_Submission";
   private static final String USER_INTEREST_INVENTORY = "ECH_User_Interest_Inventory";
   private static final String ECH_INTEREST_CATEGORY_CAREER_MAP = "ECH_Interest_Category_Career_Map_Info";
   private static final String XML_EXTN = ".xml";
   private static final String UNDERSCORE = "_";
   private static final String INFO = "Info";
   private static JaxbUtil jaxbUtil = null;
   private JAXBContext jaxbContext = null;

   protected JaxbUtil() {
   }

   public static JaxbUtil getInstance() {
      if (jaxbUtil == null) {
         jaxbUtil = new JaxbUtil();
      }

      return jaxbUtil;
   }

   public boolean generateUserInformationXml(UserInformation userInfo, String path) throws JAXBException {
      this.jaxbContext = JAXBContext.newInstance(UserInformation.class);
      Marshaller jaxbMarshaller = this.jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty("jaxb.formatted.output", true);
      String fileName = path + "User" + "Info" + ".xml";
      jaxbMarshaller.marshal(userInfo, new File(fileName));
      return true;
   }

   public UserInformation getUserInformation(String path) throws JAXBException {
      this.jaxbContext = JAXBContext.newInstance(UserInformation.class);
      Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
      String fileName = path + "User" + "Info" + ".xml";
      UserInformation userInformation = (UserInformation)unmarshaller.unmarshal(new File(fileName));
      return userInformation;
   }
}

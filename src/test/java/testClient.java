import java.sql.SQLException;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.cas.client.ClientInfo;
import org.guanzon.cas.client.services.ClientControllers;
import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testClient {

    static GRiderCAS instance;
    static ClientInfo record;
    static LogWrapper logWrapper;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("sys.default.path.temp", "D:/GGC_Maven_Systems/temp/");
        System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/new/");

        instance = MiscUtil.Connect();
        logWrapper = new LogWrapper("CAS", System.getProperty("sys.default.path.temp") + "cas-error.log");

        try {
            ClientControllers client = new ClientControllers(instance, null);
            record = client.ClientInfo();
        } catch (SQLException | GuanzonException e) {
            Assert.fail(e.getMessage());
        }
        
    }

    @Test
    public void testNewRecord() {
        try {
            JSONObject loJSON;
            
            loJSON = record.newRecord();
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }
            
            record.setClientType(ClientType.INSTITUTION);
            loJSON = record.getModel().setCompanyName("MTC Trading Limited");
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }
            
            loJSON = record.getModel().setLastName("Cuison");
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }
            
            loJSON = record.getModel().setFirstName("Michael");
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }
            
            loJSON = record.getModel().setMiddleName("Torres");
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }
            
            loJSON = record.getModel().setSuffixName("Jr.");
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }
            
            loJSON = record.getModel().setBirthDate(SQLUtil.toDate("1991-07-07", SQLUtil.FORMAT_SHORT_DATE));
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }
            
            record.Mobile(0).setMobileNo("09260375777");
            record.Mobile(0).isPrimaryMobile(true);
            
            record.addMobile();
            record.Mobile(1).setMobileNo("09176340516");
            
            record.Mail(0).setMailAddress("michael_cuison07@yahoo.com");
            record.Mail(0).isPrimaryEmail(true);
            
            record.SocMed(0).setAccount("xurpas7");
            
            record.Address(0).setHouseNo("027");
            record.Address(0).setAddress("Pogo grande");
            record.Address(0).setTownId("0314");
            
            record.InstiContact(0).setContactPersonName("Michael Cuison");
            record.InstiContact(0).setContactPersonPosition("CEO");
            record.InstiContact(0).setMobileNo("09260375777");
            record.InstiContact(0).isPrimaryContactPersion(true);
            
            loJSON = record.saveRecord();
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }  
        } catch (SQLException | GuanzonException | CloneNotSupportedException e) {
            Assert.fail(e.getMessage());
        }
    }
    
//    @Test
//    public void testOpenRecords() {
//        JSONObject loJSON;
//        System.out.println("this is a test");
//        loJSON = record.Master().openRecord("M00125000002");
//        if ("error".equals((String) loJSON.get("result"))){
//            Assert.fail((String) loJSON.get("message"));
//        } 
//        record.OpenClientAddress("M00125000002");
//        record.OpenClientMobile("M00125000002");
//        record.OpenClientMail("M00125000002");
//        record.OpenClientSocialMedia("M00125000002");
//        
//        
//        System.out.println("the test test");
//        System.out.println(record.Address(0).getModel().getClientId());
//        
//        System.out.println(String.valueOf(record.getAddressCount()));
//        System.out.println(String.valueOf(record.getMobileCount()));
//        System.out.println(String.valueOf(record.getMailCount()));
//        System.out.println(String.valueOf(record.getSocMedCount()));
//   
//    }
//    
//    
//    @Test
//    public void testNewRecord() {
//        JSONObject loJSON;
//        System.out.println("this is a test");
//        loJSON = record.Master().openRecord("M00125000002");
//        if ("error".equals((String) loJSON.get("result"))){
//            Assert.fail((String) loJSON.get("message"));
//        } 
//
//        loJSON = record.New();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }              
//                loJSON = record.Master().getModel().setCompanyName("Michael, Cuison Corporation");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }    
//        //master
//        loJSON = record.Master().getModel().setClientType(ClientType.INDIVIDUAL);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }              
//        
//        loJSON = record.Master().getModel().setLastName("Cuison");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }              
//        
//        loJSON = record.Master().getModel().setMiddleName("Torres");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }              
//        
//        loJSON = record.Master().getModel().setFirstName("Michael");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        //mobile
//        loJSON = record.Mobile(0).getModel().setMobileNo("09260375777");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mobile(0).getModel().isPrimaryMobile(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mobile(0).getModel().setMobileNetwork(MobileNetwork.GLOBE);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mobile(0).getModel().isPrimaryMobile(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addMobile();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.Mobile(1).getModel().setMobileNo("09176340516");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.Mobile(1).getModel().setMobileNetwork(MobileNetwork.GLOBE);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addMobile();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        //address
//        loJSON = record.Address(0).getModel().setHouseNo("027");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().setAddress("Purok Centro");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().setBarangayId("1100134");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().setTownId("0314");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().isPrimaryAddress(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().setLatitude("1");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//
//        
//        
//        //email
//        loJSON = record.Mail(0).getModel().setMailAddress("michael_cuison07@yahoo.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mail(0).getModel().isPrimaryEmail(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mail(0).getModel().isPrimaryEmail(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addMail();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.Mail(1).getModel().setMailAddress("xurpas7@gmail.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.addMail();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        //social media
//        loJSON = record.SocialMedia(0).getModel().setAccount("michael_cuison07@yahoo.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.SocialMedia(0).getModel().setSocMedType(SocMedAccountType.FACEBOOK);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addSocialMedia();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.SocialMedia(1).getModel().setAccount("xurpas7@gmail.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.SocialMedia(1).getModel().setSocMedType(SocMedAccountType.X);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addSocialMedia();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.Save();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }  
//    }
//    
//    
//    
//    
//        @Test
//    public void testNewRecord() {
//        JSONObject loJSON;
//       loJSON =  record.Master().searchCitizenship("", true);
//          if ("error".equals((String) loJSON.get("result"))) {
//           Assert.fail((String) loJSON.get("message"));
//       }       
//    }
//    
//    @Test
//    public void testNewRecordIns() {
//        JSONObject loJSON;
//
//        loJSON = record.New();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }              
//        
//        
//        //master
//        loJSON = record.Master().getModel().setClientType(ClientType.INSTITUTION);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }              
//        
//        loJSON = record.Master().getModel().setCompanyName("Michael Cuison Corporation");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }              
//        
//        //mobile
//        loJSON = record.Mobile(0).getModel().setMobileNo("09260375777");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mobile(0).getModel().isPrimaryMobile(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mobile(0).getModel().setMobileNetwork(MobileNetwork.GLOBE);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mobile(0).getModel().isPrimaryMobile(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addMobile();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.Mobile(1).getModel().setMobileNo("09176340516");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.Mobile(1).getModel().setMobileNetwork(MobileNetwork.GLOBE);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addMobile();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        //address
//        loJSON = record.Address(0).getModel().setHouseNo("027");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().setAddress("Purok Centro");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().setBarangayId("1100134");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().setTownId("0314");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Address(0).getModel().isPrimaryAddress(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        //email
//        loJSON = record.Mail(0).getModel().setMailAddress("michael_cuison07@yahoo.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mail(0).getModel().isPrimaryEmail(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Mail(0).getModel().isPrimaryEmail(true);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addMail();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.Mail(1).getModel().setMailAddress("xurpas7@gmail.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.addMail();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        //social media
//        loJSON = record.SocialMedia(0).getModel().setAccount("michael_cuison07@yahoo.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.SocialMedia(0).getModel().setSocMedType(SocMedAccountType.FACEBOOK);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addSocialMedia();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.SocialMedia(1).getModel().setAccount("xurpas7@gmail.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        loJSON = record.SocialMedia(1).getModel().setSocMedType(SocMedAccountType.X);
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.addSocialMedia();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }   
//        
//        
//        
//        
//        //institution contact person
//        loJSON = record.InstitutionContactPerson(0).getModel().setContactPersonName("Michael Cuison");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.InstitutionContactPerson(0).getModel().setContactPersonPosition("CEO");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.InstitutionContactPerson(0).getModel().setMobileNo("09176340516");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.InstitutionContactPerson(0).getModel().setMailAddress("michael_cuison07@yahoo.com");
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }
//        
//        loJSON = record.Save();
//        if ("error".equals((String) loJSON.get("result"))) {
//            Assert.fail((String) loJSON.get("message"));
//        }  
//    }  

    @AfterClass
    public static void tearDownClass() {
        record = null;
        instance = null;
    }
}

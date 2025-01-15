
import org.guanzon.cas.client.account.Account_Accreditation;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.MobileNetwork;
import org.guanzon.appdriver.constant.SocMedAccountType;
import org.guanzon.cas.client.Client;
import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testAccountClientAccreditation {

    static GRider instance;
    static Account_Accreditation record;
    static LogWrapper logWrapper;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("sys.default.path.temp", "D:/GGC_Maven_Systems/temp/");
        System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/new/");

        instance = MiscUtil.Connect();
        logWrapper = new LogWrapper("CAS", System.getProperty("sys.default.path.temp") + "cas-error.log");

        record = new Account_Accreditation(instance, true);
    }

    @Test
    public void testClientAccreditation() {
        JSONObject loJSON;
        System.out.println("this is a test");
        
        loJSON = record.newTransaction();
        System.out.println(record.getAccount(0, "sTransNox"));
        
        loJSON = record.newTransaction();
        System.out.println("");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }
        
        loJSON = record.setAccount(0, "dTransact", "0000-00-00");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "cAcctType", "0");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "sClientID", "M00124000075");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "sContctID", "M00124000026");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "sRemarksx", "sample remarks");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "cTranType", "0");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "sCategrCd", "0001");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "cTranStat", "0");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "sApproved", "");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "dApproved", "0000-00-00");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "sModified", "test");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "sRemarksx", "test");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }

        loJSON = record.setAccount(0, "dModified", "0000-00-00");
        if ("error".equals((String) loJSON.get("result"))) {
            Assert.fail((String) loJSON.get("message"));
        }
        
        record.saveTransaction();

    }
    @AfterClass
    public static void tearDownClass() {
        record = null;
        instance = null;
    }
}

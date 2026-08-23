import java.sql.ResultSet;
import java.sql.SQLException;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.cas.client.Client_Address;
import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class testLazyLoadSmoke {
    static GRiderCAS instance;
    static Client_Address record;
    static String existingAddressId;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/new/");
        instance = MiscUtil.Connect();
        try {
            //read-only lookup of a real, already-existing address id - no writes anywhere in this test
            ResultSet rs = instance.executeQuery("SELECT sAddrssID FROM Client_Address WHERE sBrgyIDxx IS NOT NULL AND sBrgyIDxx != '' LIMIT 1");
            if (rs.next()) {
                existingAddressId = rs.getString(1);
            }
            MiscUtil.close(rs);

            record = new Client_Address();
            record.setApplicationDriver(instance);
            record.setWithParentClass(false);
            record.setLogWrapper(null);
            record.initialize();
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testOpenRecordThenLazyBarangayAndTown() {
        try {
            if (existingAddressId == null) {
                System.out.println("No existing Client_Address row with a Barangay set was found - skipping.");
                return;
            }

            //Calling the model layer's own openRecord() directly, bypassing the Parameter
            //controller's pbInitRec guard (Client_Address.initialize() doesn't call
            //super.initialize(), a pre-existing gap unrelated to this change) - the lazy
            //Barangay()/Town() logic under test lives entirely in the Model_Client_Address layer.
            JSONObject loJSON = record.getModel().openRecord(existingAddressId);
            if ("error".equals((String) loJSON.get("result"))) {
                Assert.fail((String) loJSON.get("message"));
            }

            System.out.println("Address Id: " + record.getModel().getAddressId());
            System.out.println("Barangay FK: " + record.getModel().getBarangayId());
            System.out.println("Barangay Name: " + record.getModel().Barangay().getBarangayName());
            System.out.println("Town Name: " + record.getModel().Town().getDescription());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @AfterClass
    public static void tearDownClass() {
        record = null;
        instance = null;
    }
}

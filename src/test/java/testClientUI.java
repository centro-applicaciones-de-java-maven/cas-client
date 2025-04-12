import org.guanzon.cas.client.ClientGUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testClientUI {
    static LogWrapper wrapper;
    static GRiderCAS grider;
    
    static ClientGUI client;
    
    @BeforeClass
    public static void setUpClass() {
        try {
            String path;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                path = "D:/GGC_Maven_Systems";
            } else {
                path = "/srv/GGC_Maven_Systems";
            }

            System.setProperty("sys.default.path.config", path);

            if (!loadProperties()) {
                System.err.println("Unable to load config.");
                System.exit(1);
            } else {
                System.out.println("Config file loaded successfully.");
            }

            GRiderCAS grider = new GRiderCAS("gRider");

            if (!grider.logUser("gRider", "M001000001")) {
                System.err.println(grider.getMessage());
                System.exit(1);
            }
            
            wrapper = new LogWrapper("CAS", System.getProperty("sys.default.path.temp") + "cas-error.log");
                     
            client = new ClientGUI();
            client.setGRider(grider);
            client.setLogWrapper(wrapper);
            
        } catch (SQLException | GuanzonException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testNewRecord() {
        try {
            CommonUtils.showModal(client);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    private static boolean loadProperties() {
        try {
            Properties po_props = new Properties();
            po_props.load(new FileInputStream(System.getProperty("sys.default.path.config") + "/config/cas.properties"));

            System.setProperty("sys.default.path.temp", System.getProperty("sys.default.path.config") + po_props.getProperty("sys.default.path.temp"));
            System.setProperty("sys.default.path.metadata", System.getProperty("sys.default.path.config") + po_props.getProperty("sys.default.path.metadata"));
            
            System.setProperty("app.global.company", po_props.getProperty("app.global.company"));
            System.setProperty("app.global.industry", po_props.getProperty("app.global.industry"));
            System.setProperty("app.global.category", po_props.getProperty("app.global.category"));
            
            System.setProperty("app.global.branch", po_props.getProperty("app.global.branch"));
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

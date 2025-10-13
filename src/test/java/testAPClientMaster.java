////
////import java.sql.SQLException;
////import org.guanzon.appdriver.base.GRider;
////import org.guanzon.appdriver.base.GRiderCAS;
////import org.guanzon.appdriver.base.GuanzonException;
////import org.guanzon.appdriver.base.MiscUtil;
////import org.guanzon.appdriver.base.SQLUtil;
////import org.guanzon.cas.client.account.AP_Client_Master;
////import org.guanzon.cas.client.services.CasClientControllers;
////import org.json.simple.JSONObject;
////import org.junit.AfterClass;
////import org.junit.Assert;
////import org.junit.BeforeClass;
////import org.junit.FixMethodOrder;
////import org.junit.Test;
////import org.junit.runners.MethodSorters;
////
////@FixMethodOrder(MethodSorters.NAME_ASCENDING)
////public class testAPClientMaster {
////    static GRiderCAS instance;
////    static AP_Client_Master record;
////
////    @BeforeClass
////    public static void setUpClass() {
////        System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/new/");
////
////        instance = MiscUtil.Connect();
////        
////        record = new CasClientControllers(instance, null).APClientMaster();
////    }
////
////    @Test
////    public void testNewRecord() {
////        try {
////            JSONObject loJSON;
////
////            loJSON = record.newRecord();
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }    
////
////    //        loJSON = record.getModel().setClientId("A00124000001");
////    //        if ("error".equals((String) loJSON.get("result"))) {
////    //            Assert.fail((String) loJSON.get("message"));
////    //        }     
////
////            loJSON = record.getModel().setAddressId("C00124000004");
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            } 
////
////            loJSON = record.getModel().setContactId("C00124000003");
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            } 
////
////            loJSON = record.getModel().setCategoryCode("0001");
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }  
////
////            loJSON = record.getModel().setdateClientSince(SQLUtil.toDate("2023-01-01", SQLUtil.FORMAT_SHORT_DATE));
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setBeginningDate(SQLUtil.toDate("2023-01-01", SQLUtil.FORMAT_SHORT_DATE));
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }  
////
////            loJSON = record.getModel().setBeginningBalance(20.00);
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            } 
////
////            loJSON = record.getModel().setTermId("0000001");
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            } 
////
////            loJSON = record.getModel().setDiscount(5.00);
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setCreditLimit(1.00);
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setAccountBalance(1.00);
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setOBalance(1.00);
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setLedgerNo(1);
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setVatable("1");
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setRecordStatus("1");
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setModifyingId(instance.getUserID());
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }
////
////            loJSON = record.getModel().setModifiedDate(instance.getServerDate());
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }     
////
////            loJSON = record.saveRecord();
////            if ("error".equals((String) loJSON.get("result"))) {
////                Assert.fail((String) loJSON.get("message"));
////            }  
////        } catch (SQLException | GuanzonException e) {
////            Assert.fail(e.getMessage());
////        }
////    }
////    
//////   
//////    @Test
//////    public void testUpdateRecord() {
//////        JSONObject loJSON;
//////
//////        loJSON = record.openRecord("M00124000119");
//////        if ("error".equals((String) loJSON.get("result"))) {
//////            Assert.fail((String) loJSON.get("message"));
//////        }      
//////        
//////        loJSON = record.updateRecord();
//////        if ("error".equals((String) loJSON.get("result"))) {
//////            Assert.fail((String) loJSON.get("message"));
//////        }      
//////        
//////        loJSON = record.getModel().setDescription("Sample item from new program structure updated.");
//////        if ("error".equals((String) loJSON.get("result"))) {
//////            Assert.fail((String) loJSON.get("message"));
//////        }     
//////        
//////        loJSON = record.getModel().setModifyingId(instance.getUserID());
//////        if ("error".equals((String) loJSON.get("result"))) {
//////            Assert.fail((String) loJSON.get("message"));
//////        }     
//////        
//////        loJSON = record.getModel().setModifiedDate(instance.getServerDate());
//////        if ("error".equals((String) loJSON.get("result"))) {
//////            Assert.fail((String) loJSON.get("message"));
//////        }     
//////        
//////        loJSON = record.saveRecord();
//////        if ("error".equals((String) loJSON.get("result"))) {
//////            Assert.fail((String) loJSON.get("message"));
//////        } 
//////    }
////    
//////    @Test
//////    public void testSearch(){
//////        JSONObject loJSON = record.searchRecord("", false);        
//////        if ("success".equals((String) loJSON.get("result"))){
//////            System.out.println(record.getModel().getRegionId());
//////            System.out.println(record.getModel().getRegioneName());
//////        } else System.out.println("No record was selected.");
//////    }
////    
////    @AfterClass
////    public static void tearDownClass() {
////        record = null;
////        instance = null;
////    }
////}

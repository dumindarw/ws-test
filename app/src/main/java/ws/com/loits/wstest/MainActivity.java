package ws.com.loits.wstest;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Hashtable;


public class MainActivity extends ActionBarActivity {

    public static final String GET_ACC_DETAILS_NAMESPACE = "http://lolc.com/dss/banking/savi/getkioskaccdetails2/1.0/";
    public static final String GET_ACC_DETAILS_URL = "http://203.189.65.69:8284/services/bankingSaviGetkioskaccdetails1_0Soap?wsdl";
    //public static final String GET_ACC_DETAILS_URL = "http://172.16.106.14:9880/services/bankingSaviGetkioskaccdetails1_0Soap?wsdl";
    public static final String GET_ACC_DETAILS_METHOD_NAME = "getkioskaccdetails";
    public static final String GET_ACC_DETAILS_SOAP_ACTION = /*GET_ACC_DETAILS_NAMESPACE + GET_ACC_DETAILS_METHOD_NAME*/"getkioskaccdetails";
    //public static final String GET_ACC_DETAILS_SOAP_ACTION = "http://203.189.65.69:8284/services/bankingSaviGetkioskaccdetails1_0Soap.bankingSaviGetkioskaccdetails1_0SoapHttpSoap11Endpoint";

    public static final String SAVE_DEPOSITS_NAMESPACE = "http://lolc.com/dss/banking/savi/bankingSaviFundtransfer/1.0/";
    public static final String SAVE_DEPOSITS_URL = "http://203.189.65.69:8284/services/bankingSaviFundtransfer1_0Soap?wsdl";
    public static final String SAVE_DEPOSITS_METHOD_NAME = "saviFundtransfer";
    public static final String SAVE_DEPOSITS_SOAP_ACTION = SAVE_DEPOSITS_NAMESPACE + SAVE_DEPOSITS_METHOD_NAME;


    public static final String CEFT_NAMESPACE = "http://lolc.com/dss/banking/savi/ceft/1.0";
    public static final String CEFT_URL = "http://172.16.106.14:9880/services/bankingSaviCeft1_0Soap?wsdl";
    public static final String CEFT_METHOD_NAME = "saviCeft";
    public static final String CEFT_SOAP_ACTION = /*SAVE_DEPOSITS_NAMESPACE + SAVE_DEPOSITS_METHOD_NAME*/"saviCeft";

    public static final String INSU_NAMESPACE = "http://AsserValuesServiceManager.edgevanatage.spsolutions.biz/";
    public static final String INSU_URL = "https://203.189.65.69:8246/services/AsserValuesService?wsdl";
    public static final String INSU_METHOD_NAME = "saveAssessor";
    public static final String INSU_SOAP_ACTION =INSU_NAMESPACE+INSU_METHOD_NAME; /*"https://203.189.65.69:8246/services/AsserValuesService.AsserValuesServiceHttpsSoap11Endpoint"*/;

    public static final String INSU_IMG_NAMESPACE = "http://ImageStoreService.edgevanatage.spsolutions.biz/";
    public static final String INSU_IMG_URL = "https://203.189.65.69:8246/services/imageService?wsdl";
    public static final String INSU_IMG_METHOD_NAME = "saveImage";
    public static final String INSU_IMG_SOAP_ACTION =INSU_IMG_NAMESPACE+INSU_IMG_METHOD_NAME;

    public static final String GREG_GET_TOKEN_DETAILS_NAMESPACE = "http://api.ws.registry.carbon.wso2.org";
    public static final String GREG_GET_TOKEN_DETAILS_URL = "http://203.189.65.69:8284/services/utilityComnGetwsregistry1_0Soap?wsdl";
    public static final String GREG_GET_TOKEN_DETAILS_METHOD_NAME = "WSget";
    public static final String GREG_GET_TOKEN_DETAILS_SOAP_ACTION = GREG_GET_TOKEN_DETAILS_NAMESPACE + GREG_GET_TOKEN_DETAILS_METHOD_NAME;
    public static final String GREG_TOKEN_USER_NAME = "LOLC/user1";
    public static final String GREG_TOKEN_PASS = "user123";
    public static final String API_PATH = "/_system/governance/uris/Generic/DSSUsernameTokenCyberfin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new WSTask(this).execute();
        //new WSTaskREST(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class WSTask extends AsyncTask<Void, String, SoapPrimitive> {

        Context mActivity;

        public WSTask(Activity activity) {
            mActivity = activity;
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {

            Hashtable paramValues = new Hashtable();
            /*paramValues.put("p_account_type", "S");
            paramValues.put("p_account_number", "99910010013");
            paramValues.put("p_account_currency", "");
            paramValues.put("p_account_status", "");
            paramValues.put("p_account_holder_name", "");
            paramValues.put("p_res_code", "");
            paramValues.put("p_res_act", "");
            paramValues.put("p_err_msg", "");
            paramValues.put("p_account_category", "");*/


            /*paramValues.put("sv_ceft_scenario_no", "3");
            paramValues.put("process_type", "50");
            paramValues.put("rep_chnl", "IB");
            paramValues.put("fundtr_type", "W");
            paramValues.put("sv_ceft_benef_card_no", "");
            paramValues.put("sv_ceft_des_acc_no", "01210000781");
            paramValues.put("sv_ceft_cardholder_pan", "");
            paramValues.put("sv_ceft_cardholder_acc", "00110000001");
            paramValues.put("sv_ceft_des_bank_code", "6807");
            paramValues.put("sv_ceft_orig_bank_code", "6843");
            paramValues.put("sv_ceft_des_branch_code", "998");
            paramValues.put("sv_ceft_orig_branch_code", "001");
            paramValues.put("sv_ceft_des_acc_hold_name", "");
            paramValues.put("sv_ceft_orig_acc_hold_name", "");
            paramValues.put("sv_ceft_particulars", "");
            paramValues.put("sv_ceft_reference", "");
            paramValues.put("sv_ceft_txn_code", "52");
            paramValues.put("sv_ceft_stan", "");
            paramValues.put("sv_ceft_acquiring_code", "");
            paramValues.put("sv_ceft_ret_ref_no", "");
            paramValues.put("sv_ceft_rcv_ins_id_code", "");
            paramValues.put("sv_ceft_amount", "156.23");
            paramValues.put("sv_ceft_cur_code", "LKR");
            paramValues.put("sv_ceft_txn_date_time", "");
            paramValues.put("sv_ceft_auth_no", "");
            paramValues.put("sv_ceft_status", "");
            paramValues.put("sv_ceft_resp_code", "");
            paramValues.put("sv_ceft_resp_sub_code", "");
            paramValues.put("sv_ceft_response_msg", "");
            paramValues.put("sv_ceft_ref_from_txn", "");
            paramValues.put("sv_ceft_ref_from_seq", "");
            paramValues.put("sv_ceft_ref_to_txn", "");
            paramValues.put("sv_ceft_ref_to_seq", "");
            paramValues.put("sv_ceft_trans_date", "");
            paramValues.put("sv_ceft_trans_time", "");
            paramValues.put("sv_ceft_stl_date", "");
            paramValues.put("sv_ceft_acc_id_code", "");
            paramValues.put("sv_ceft_org_stan", "");
            paramValues.put("sv_ceft_org_txn_date_time", "");
            paramValues.put("sv_ceft_org_acquiring_code", "");
            paramValues.put("sv_ceft_org_ret_ref_no", "");*/

            /*paramValues.put("wFNo", "2600012");
            paramValues.put("aCR", "");
            paramValues.put("baldTyrePenalty", "");
            paramValues.put("payableAmount", "");
            paramValues.put("settlementMethod", "");
            paramValues.put("onsiteOffer", "");
            paramValues.put("inspectionType", "");
            paramValues.put("inspectionRemarks", "");
            paramValues.put("policeReportRequested", "");
            paramValues.put("specialRemarks", "");
            paramValues.put("investigateClaim", "");
            paramValues.put("arrivalAtAccident", "");
            paramValues.put("mileage", "");
            paramValues.put("otherCosts", "");
            paramValues.put("reason", "");
            paramValues.put("preAccidentValue", "");
            paramValues.put("sumOfInsured", "");
            paramValues.put("excess", "");
            paramValues.put("professionalFee", "");
            paramValues.put("telephone", "");
            paramValues.put("totalCost", "");
            paramValues.put("photoCount", "");
            paramValues.put("repairComplete", "");
            paramValues.put("salvageRecieved", "");*/

            paramValues.put("intimationNo","MC201600409N");
            paramValues.put("image","/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEj/tJn2pccdKQICOaDtA9aTtQOeT+V");
            paramValues.put("imageName","1.jpg");
            SoapClient client = new SoapClient();
            SoapPrimitive responseObj = null;


            /*Hashtable gRegValues = new Hashtable();
            gRegValues.put("path", API_PATH);
            SoapObject responseObj = client.getResponse(
                    GREG_GET_TOKEN_DETAILS_NAMESPACE,
                    GREG_GET_TOKEN_DETAILS_URL,
                    GREG_GET_TOKEN_DETAILS_METHOD_NAME,
                    GREG_GET_TOKEN_DETAILS_SOAP_ACTION,
                    gRegValues,
                    GREG_TOKEN_USER_NAME,
                    GREG_TOKEN_PASS);
*/
            responseObj = client.getResponse(
                    INSU_IMG_NAMESPACE,
                    INSU_IMG_URL,
                    INSU_IMG_METHOD_NAME,
                    INSU_IMG_SOAP_ACTION,
                    paramValues,
                        /*childPropInfArr[0]*/"minsu",
                        /*childPropInfArr[1]*/"minsu123", mActivity);

            /*Hashtable paramValues = new Hashtable();
            paramValues.put("txn_cur", "LKR");
            paramValues.put("txn_amt", 0.1);
            paramValues.put("from_acc_no", "01210000781");
            paramValues.put("to_acc_no", "00210000006");
            paramValues.put("fundtr_type", "L");
            paramValues.put("resp_code", "");
            paramValues.put("resp_act", "");
            paramValues.put("resp_msg", "");
            paramValues.put("act_bal", "");
            paramValues.put("act_bal_sign", "");
            paramValues.put("act_bal_cur", "");
            paramValues.put("ava_bal", "");
            paramValues.put("ava_bal_sign", "");
            paramValues.put("ava_bal_cur", "");
            paramValues.put("process_type", 40);
            paramValues.put("prim_acc_no", "");
            paramValues.put("req_type_id", "");
            paramValues.put("token_id", "");
            paramValues.put("rep_chnl", "MS");
            paramValues.put("ref_id", "");
            paramValues.put("p_ref_from_txn", "");
            paramValues.put("p_ref_from_seq", "");
            paramValues.put("p_ref_to_txn", "");
            paramValues.put("p_ref_to_seq", "");
            paramValues.put("upay_merc_sys_id", "");
            paramValues.put("to_bnk", "");
            paramValues.put("to_bnk_brh", "");
            paramValues.put("frm_bnk", "");
            paramValues.put("frm_bnk_brh", "");
            paramValues.put("slip_tr_code", "");

            SoapClient client = new SoapClient();

            SoapObject responseObj  = client.getResponse(
                    SAVE_DEPOSITS_NAMESPACE,
                    SAVE_DEPOSITS_URL,
                    SAVE_DEPOSITS_METHOD_NAME,
                    SAVE_DEPOSITS_SOAP_ACTION,
                    paramValues,
                    *//*childPropInfArr[0]*//*"cyberfin",
                    *//*childPropInfArr[1]*//*"cyber");

            SoapObject ro = responseObj;*/

            //RestClient.sendCancelRecordToStaging("", mActivity);
            //RestClient.getUserDetails("",mActivity,"DUMINDAW");
            //RestClient.saveSavingWithStaging("",mActivity);
            //RestClient.updateSavingWithStaging("",mActivity,226);

            return responseObj;
        }

        protected void onPostExecute(SoapPrimitive result) {

            if(result == null){
                Toast.makeText(mActivity,"Failed to get response !",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mActivity,result.getValue().toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class WSTaskREST extends AsyncTask<Void, String, JSONObject> {

        Context mActivity;

        public WSTaskREST(Activity activity) {
            mActivity = activity;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            //JSONObject obj = RestClient.getMEDetails(null, mActivity, "DUMINDAW");
            //JSONObject obj = RestClient.getAccountList(null, mActivity, "KUG");
           JSONObject obj = RestClient.getCustomerInfo(null,mActivity,"10035");

            return obj;
        }

        protected void onPostExecute(JSONObject result) {

            if(result == null){
                Toast.makeText(mActivity,"Failed to get response !",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mActivity,"Success !",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

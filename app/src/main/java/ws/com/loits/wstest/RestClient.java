package ws.com.loits.wstest;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 * Provides utility methods for communicating with the server.
 */

public class RestClient {

    private static final String TAG = "RestClient";

    public static final String UPLOAD_DEPOSITS_URL = "http://203.189.65.69:8284/services/msSavedepositdata1_0Rest/";
    public static final String UPLOAD_CANCELS_URL = "http://203.189.65.69:8284/services/msCanceldepositdata1_0Rest/";
    public static final String UPDATE_DEPOSITS_URL = "http://203.189.65.69:8284/services/msUpdatedepositdata1_0Rest/";
    public static final String GET_USER_DETAILS_URL = "http://203.189.65.69:8284/services/mobilesavings/user/getDetails/";
    public static final String HTTPS_URL = "https://203.189.65.69:8246/services/lendingMcroCreditappraisal1_0Rest";

    public static final String GET_ME_DETAILS_URL = "http://203.189.65.69:8284/services/msGetMEDetails1_0Rest/";
    public static final String GET_CUSTOMER_INFO_URL = "http://203.189.65.69:8284/services/msGetCustomerInfo1_0Rest/";
    public static final String GET_BRANCH_ACCOUNT_LIST_URL = "http://203.189.65.69:8284/services/msGetBranchAccountList1_0Rest/";

    public static int login(Context context) {

        try {
            URL restUrl = new URL(HTTPS_URL);

            //HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();

            HttpsURLConnection conn = (HttpsURLConnection) restUrl.openConnection();

            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    /*HostnameVerifier hv =
                            HttpsURLConnection.getDefaultHostnameVerifier();*/
                    return /*hv.verify("172.16.107.13", session)*/true;
                }
            };

            conn.setHostnameVerifier(hostnameVerifier);
            conn.setSSLSocketFactory(newSslSocketFactory2(context).getSocketFactory());

            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("password", "123");
            jsonParam.put("pin", null);
            jsonParam.put("token", null);
            jsonParam.put("username", "naween");
            jsonParam.put("validTill", null);
            jsonParam.put("valid", true);


            OutputStream os = conn.getOutputStream();

            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            conn.connect();

            int code = conn.getResponseCode();

            String msg = conn.getResponseMessage();

            if (code == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                return Integer.parseInt(responseBodyStr);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int saveSavingWithStaging(String authToken, Context context) {

        try {
            URL restUrl = new URL(UPLOAD_DEPOSITS_URL);

            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();

            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("accountNo", "0000000077");
            jsonParam.put("name", "testCust");
            jsonParam.put("amount", 0.1);
            jsonParam.put("currency", "LKR");
            jsonParam.put("date", "19-Nov-2015 01:23:57");
            jsonParam.put("officer", "testOff");
            jsonParam.put("officerId", "007");
            jsonParam.put("officerBranch", "KNG");
            jsonParam.put("syncStatus", "P");

            OutputStream os = conn.getOutputStream();

            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            conn.connect();

            int code = conn.getResponseCode();

            String msg = conn.getResponseMessage();

            if (code == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                return Integer.parseInt(responseBodyStr);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int sendCancelRecordToStaging(String authToken, Context context){

        try {
            URL restUrl = new URL(UPLOAD_CANCELS_URL);

            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();

            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();

            jsonParam.put("accountNo", "0000000077");
            jsonParam.put("name", "testCust");
            jsonParam.put("amount", 0.1);
            jsonParam.put("currency", "LKR");
            jsonParam.put("date", "19-Nov-2015 01:23:57");
            jsonParam.put("officer", "testOff");
            jsonParam.put("officerId", "007");
            jsonParam.put("cancelRemarks", "test Cancellation");
            jsonParam.put("officerBranch", "KNG");

            OutputStream os = conn.getOutputStream();

            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            conn.connect();

            int code = conn.getResponseCode();

            String msg = conn.getResponseMessage();

            if (code == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                return Integer.parseInt(responseBodyStr);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean updateSavingWithStaging(String authToken, Context context, int depositId) {

        try {
            URL restUrl = new URL(UPDATE_DEPOSITS_URL);

            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();

            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", depositId);
            jsonParam.put("syncStatus", "C");

            OutputStream os = conn.getOutputStream();

            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            conn.connect();

            int code = conn.getResponseCode();

            String msg = conn.getResponseMessage();

            if (code == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                if(Integer.parseInt(responseBodyStr) > 0 )
                    return true;

                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static JSONObject getUserDetails(String authToken, Context context, String userName){

        JSONObject jSONResp = null;

        try {
            URL restUrl = new URL(GET_USER_DETAILS_URL + userName);

            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String msg = conn.getResponseMessage();
            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                jSONResp = new JSONObject(responseBodyStr);
            }

            return jSONResp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getMEDetails(String authToken, Context context, String userName){

        JSONObject jSONResp = null;

        try {
            URL restUrl = new URL(GET_ME_DETAILS_URL + userName);

            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String msg = conn.getResponseMessage();
            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                jSONResp = new JSONObject(responseBodyStr);
            }

            return jSONResp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getCustomerInfo(String authToken, Context context, String customerCode){

        JSONObject jSONResp = null;

        try {
            URL restUrl = new URL(GET_CUSTOMER_INFO_URL + customerCode);

            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String msg = conn.getResponseMessage();
            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                jSONResp = new JSONObject(responseBodyStr);
            }

            return jSONResp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getAccountList(String authToken, Context context, String branch){

        JSONObject jSONResp = null;

        try {
            URL restUrl = new URL(GET_BRANCH_ACCOUNT_LIST_URL + branch);

            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String msg = conn.getResponseMessage();
            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                jSONResp = new JSONObject(responseBodyStr);
            }

            return jSONResp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getTestSSL(Context context){

        JSONObject jSONResp = null;

        try {
            URL restUrl = new URL("https://192.100.151.119:8443/RCPT_SERVER/adhocidcrequest/11155");

            HttpsURLConnection conn = (HttpsURLConnection) restUrl.openConnection();

            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    /*HostnameVerifier hv =
                            HttpsURLConnection.getDefaultHostnameVerifier();*/
                    return /*hv.verify("172.16.107.13", session)*/true;
                }
            };

            conn.setHostnameVerifier(hostnameVerifier);
            conn.setSSLSocketFactory(newSslSocketFactory(context).getSocketFactory());

            conn.setRequestMethod("GET");
            conn.connect();

            String msg = conn.getResponseMessage();
            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String responseBodyStr = "";
                String line;
                while ((line = in.readLine()) != null) {
                    responseBodyStr += line;
                }
                in.close();

                jSONResp = new JSONObject(responseBodyStr);
            }

            return jSONResp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static SSLContext newSslSocketFactory(Context context) {
        try {

            TrustManagerFactory originalTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            originalTrustManagerFactory.init((KeyStore) null);

            // Get an instance of the Bouncy Castle KeyStore format
            KeyStore trusted = KeyStore.getInstance(KeyStore.getDefaultType());
            // Get the raw resource, which contains the keystore with
            // your trusted certificates (root and any intermediate certs)

            InputStream in = context.getResources().openRawResource(R.raw.mykeystore);
            try {
                // Initialize the keystore with the provided trusted certificates
                // Also provide the password of the keystore
                trusted.load(in, "lolcMCOM@android".toCharArray());
            } finally {
                in.close();
            }

            originalTrustManagerFactory.init(trusted);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(/*keyManagerFactory.getKeyManagers()*/null, originalTrustManagerFactory.getTrustManagers()/*new TrustManager[]{tm}*/, /*new java.security.SecureRandom()*/null);

            return sc;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private static SSLContext newSslSocketFactory2(Context context) {
        try {

            TrustManagerFactory originalTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            originalTrustManagerFactory.init((KeyStore) null);

            // Get an instance of the Bouncy Castle KeyStore format
            KeyStore trusted = KeyStore.getInstance(KeyStore.getDefaultType());
            // Get the raw resource, which contains the keystore with
            // your trusted certificates (root and any intermediate certs)

            InputStream in = context.getResources().openRawResource(R.raw.clienttruststore);
            try {
                // Initialize the keystore with the provided trusted certificates
                // Also provide the password of the keystore
                trusted.load(in, "wso2carbon".toCharArray());
            } finally {
                in.close();
            }

            originalTrustManagerFactory.init(trusted);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(/*keyManagerFactory.getKeyManagers()*/null, originalTrustManagerFactory.getTrustManagers()/*new TrustManager[]{tm}*/, /*new java.security.SecureRandom()*/null);

            return sc;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}

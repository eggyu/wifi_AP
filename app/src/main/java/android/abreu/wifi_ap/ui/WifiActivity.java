package android.abreu.wifi_ap.ui;

import android.abreu.wifi_ap.R;
import android.abreu.wifi_ap.util.WifiUtils;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WifiActivity extends Activity implements TextWatcher {

    /*************************************************************************
     * 常數數宣告區
     *************************************************************************/

    /*************************************************************************
     * 變數數宣告區
     *************************************************************************/
    private SharedPreferences preferences;
    private final static String savefile = "SAVESSID";
    /****************************
     * View元件變數
     *********************************/
    private EditText ssidNameET, passwordET;
    private Button makeWIFIAP_BT;
    /**************************** Adapter元件變數 ******************************/

    /**************************** Array元件變數 ********************************/

    /**************************** 資料集合變數 *********************************/

    /**************************** 物件變數 *************************************/

    /*************************************************************************
     * Override 函式宣告 (覆寫)
     *************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        /* 初始化View元件,從XML檔中透過ID取得UI物件 */
        findViews();
        /* 初始化資料,包含從其他Activity傳來的Bundle資料 ,Preference資枓 */
        initData();


        /* 設置View元件對應的linstener事件,讓UI可以與用戶產生互動 */
        setLinstener();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onRestart() {


        super.onRestart();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }


    /*************************************************************************
     * 自訂API函式宣告
     *************************************************************************/

    /**
     * 取得View元件記憶體位置
     */
    private void findViews() {
        // TODO Auto-generated method stub

        ssidNameET = (EditText) findViewById(R.id.ssidNameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        makeWIFIAP_BT = (Button) findViewById(R.id.makeWIFIAP_BT);


    }

    /**
     * 初始化物件資料
     */
    private void initData() {
        // TODO Auto-generated method stub

        preferences = getSharedPreferences(savefile, 0);
        ssidNameET.setText(preferences.getString("SSID", ""));
        passwordET.setText(preferences.getString("PW", ""));
        checkEditText();
    }


    /**
     * 於View元件設置監聽Event.
     */
    private void setLinstener() {
        // TODO Auto-generated method stub
        makeWIFIAP_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ssidSize = ssidNameET.getText().toString().getBytes().length;
                if (ssidSize > 0 && ssidSize <= 32) {
                    Toast.makeText(WifiActivity.this, "Create WiFi AP.....", Toast.LENGTH_SHORT).show();
                    ssidNameET.setEnabled(false);
                    passwordET.setEnabled(false);
                    WifiUtils.startWifiAp(ssidNameET.getText().toString(), passwordET.getText().toString(), mHandler);
                } else {
                    Toast.makeText(WifiActivity.this, "SSID length size is need in 1~32 byte", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ssidNameET.addTextChangedListener(this);
        passwordET.addTextChangedListener(this);
    }


    /**************************** 保存、釋放 、重啟 *************************************/


    /*************************************************************************
     * Thread 、 AsycTask類別宣告
     *************************************************************************/

    /*************************************************************************
     * Handler類別宣告
     *************************************************************************/
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            preferences.edit().putString("SSID", ssidNameET.getText().toString()).putString("PW", passwordET.getText().toString()).commit();
            ssidNameET.setEnabled(true);
            passwordET.setEnabled(true);
            Toast.makeText(WifiActivity.this, "Create Ap Success", Toast.LENGTH_SHORT).show();
            finish();
        }
    };
    /*************************************************************************
     * Adapter類別宣告
     *************************************************************************/

    /*************************************************************************
     * 其它內部類別宣告
     *************************************************************************/
    private void checkEditText() {
        int ssidSize = ssidNameET.getText().toString().getBytes().length;
        int pwSize = passwordET.getText().toString().length();
        if (pwSize >= 8 && pwSize < 64 && ssidSize > 0 && ssidSize <= 32) {
            makeWIFIAP_BT.setEnabled(true);
        } else {
            makeWIFIAP_BT.setEnabled(false);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        checkEditText();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
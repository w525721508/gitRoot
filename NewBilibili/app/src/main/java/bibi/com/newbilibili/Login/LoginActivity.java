package bibi.com.newbilibili.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import bibi.com.newbilibili.Login.view.ILoginView;
import bibi.com.newbilibili.R;
import bibi.com.newbilibili.Login.presenter.IPersenter;
import bibi.com.newbilibili.Login.presenter.PersenterImpl;
import bibi.com.newbilibili.main.IndexActivity;

public class LoginActivity extends Activity implements ILoginView {
    EditText etPhone, etPwd;
    Button btnLogin;
    ImageButton ibWeixin;
    Handler handler = new Handler();
    IPersenter iPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPwd = (EditText) findViewById(R.id.etPwd);
        iPersenter = new PersenterImpl(LoginActivity.this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {

            }
            break;
            case R.id.ibWeixin: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        iPersenter.doLogin();
                    }
                }).start();

            }
            break;
        }
    }


    @Override
    public void setViewGray() {
        btnLogin.setTextColor(getResources().getColor(R.color.gray));
    }

    @Override
    public void loginResult(boolean result, String mes) {
        Log.e("tag", "成功");
        startActivity(new Intent(LoginActivity.this, IndexActivity.class));
    }

    @Override
    public void shareResult(boolean result, String mes) {

    }

}

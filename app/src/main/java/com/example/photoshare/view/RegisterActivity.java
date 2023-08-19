package com.example.photoshare.view;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;

import com.example.photoshare.R;
import com.example.photoshare.common.Contact;
import com.example.photoshare.databinding.ActivityRegisterBinding;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.project.base.baseui.BaseActivity;

/**
 * 文件名：RegisterActivity
 * 作  者： Agonystrly
 * 日  期：2023/8/19 4:11 PM
 * 描述：注册界面
 */
public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityRegisterBinding) dataBinding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

        makerEditText();
    }

    private void makerEditText(){
        binding.account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tlAccount.setErrorEnabled(false);
                binding.tlPassword.setErrorEnabled(false);
                binding.tlPasswordAgain.setErrorEnabled(false);
            }
        });
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tlAccount.setErrorEnabled(false);
                binding.tlPassword.setErrorEnabled(false);
                binding.tlPasswordAgain.setErrorEnabled(false);

            }
        });

        binding.passwordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tlAccount.setErrorEnabled(false);
                binding.tlPassword.setErrorEnabled(false);
                binding.tlPasswordAgain.setErrorEnabled(false);

            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = binding.account.getText().toString();
                String password = binding.password.getText().toString();
                String passwordAgagin = binding.passwordAgain.getText().toString();
                if (TextUtils.isEmpty(account)){
                    binding.tlAccount.setError("昵称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    binding.tlPassword.setError("密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(passwordAgagin)){
                    binding.tlPasswordAgain.setError("密码不能为空");
                    return;
                }
                if (!password.equals(passwordAgagin)){
                    binding.tlPassword.setError("密码不一致");
                    binding.tlPasswordAgain.setError("密码不一致");
                    return;
                }
                reister(account,password);
            }
        });
    }

    /**
     * 开始注册
     * @param account
     * @param password
     */
    private void reister(String account, String password) {
        RequestParams params = new RequestParams(Contact.REGISTER);
        params.addHeader("Accept", "application/json, text/plain, */*");
        params.addHeader("Content-Type", "application/json");
        params.addHeader("appId", Contact.appId);
        params.addHeader("appSecret", Contact.appSecret);
        params.addBodyParameter("username", account);
        params.addBodyParameter("password", password);
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("输出数据",result);
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });


    }

}

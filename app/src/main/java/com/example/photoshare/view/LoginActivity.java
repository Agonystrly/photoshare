package com.example.photoshare.view;

import android.content.Intent;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.example.photoshare.R;
import com.example.photoshare.databinding.ActivityLoginBinding;

import cn.project.base.baseui.BaseActivity;

/**
 * 文件名：LoginActivity
 * 作  者： Agonystrly
 * 日  期：2023/8/19 4:10 PM
 * 描述：登陆界面
 */
public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setDataBinding(ViewDataBinding dataBinding) {
        binding = (ActivityLoginBinding) dataBinding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    public void register(View view){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
    public void login(View view){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
}

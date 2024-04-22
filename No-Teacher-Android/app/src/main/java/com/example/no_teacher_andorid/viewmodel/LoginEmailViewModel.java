package com.example.no_teacher_andorid.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.no_teacher_andorid.api.ApiService;
import com.example.no_teacher_andorid.bean.User;
import com.example.no_teacher_andorid.ui.activity.LoginEmailActivity;

/**
 * @Author : Lee
 * @Date : Created in 2024/4/19 21:23
 * @Decription :
 */

/**
 * 双向数据绑定 ： UI控件能显示数据模型的内容，
 *    同时也能在用户界面上的变化直接反映到数据模型中
 *  在Data Binding表达式中使用@={}
 *
 * 单向绑定 只从数据源到UI元素的数据绑定
 */
public class LoginEmailViewModel extends ViewModel {

    //MutableLiveData 支持双向绑定
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData;
    private ApiService apiService;

    public LoginEmailViewModel(LoginEmailActivity loginEmailActivity) {
        userLiveData = new MutableLiveData<>();
    }

    /*，

     */
    /**
     * @param :
     * @return LiveData<String>
     * @author Lee
     * @description 由于LiveData的观察者机制,一旦email属性的值发生变化
     *  所有订阅这个LiveData的组件（在这种情况下是 EditText）都会接收到更新
     *    并将新值反映在UI上。这样的机制确保了UI的数据总是与后台ViewModel保持同步
     * @date 2024/4/19 22:11
     */
    public LiveData<String> getEmail(){
        return email;
    }

    /**
     * @param email:
     * @return void
     * @author Lee
     * @description 更新ViewModel中存储的数据，
     *  通过LiveData或MutableLiveData自动通知到视图
     *  从而实现从ViewModel到View的数据更新
     * @date 2024/4/19 22:09
     */
    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public void register(String email, String password) {
        User user = new User(email, password);
        //网络请求，响应更新userLiveDat

    }

    public void login(String email, String password){
        User user = new User(email, password);
        //网络请求，响应更新userLiveDat
    }
}














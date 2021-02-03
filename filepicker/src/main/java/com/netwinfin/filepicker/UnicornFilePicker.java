package com.netwinfin.filepicker;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.netwinfin.filepicker.models.Config;
import com.netwinfin.filepicker.ui.FilePickerActivity;

import java.lang.ref.WeakReference;


/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */

/* This is the configuration class for File Picker */
public final class UnicornFilePicker {
    
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mContext;

    private UnicornFilePicker(Activity activity){
        this(activity, null);
    }

    private UnicornFilePicker(Fragment fragment){
        this(fragment.getActivity(), fragment);
    }

    public UnicornFilePicker(Activity activity, Fragment fragment) {
        this.mActivity = new WeakReference<>(activity);
        this.mContext = new WeakReference<>(fragment);
    }

    /**
     * Start UnicornFilePicker from an activity
     *
     * @param activity Activity instance
     * @return UnicornFilePicker instance
     */
    public static com.netwinfin.filepicker.UnicornFilePicker from(Activity activity){
        return new com.netwinfin.filepicker.UnicornFilePicker(activity);
    }

    /**
     * Start UnicornFilePicker from a fragment
     *
     * @param fragment Fragment instance
     * @return UnicornFilePicker instance
     */
    public static com.netwinfin.filepicker.UnicornFilePicker from(Fragment fragment){
        return new com.netwinfin.filepicker.UnicornFilePicker(fragment);
    }

    /**
     * Start FilePicker activity and wait for result
     * @param requestCode Integer identity for Activity or Fragment request
     */
    public void forResult(int requestCode){
        Config.getInstance().setReqCode(requestCode);

        Activity activity = getActivity();
        if(activity==null){
            return;
        }

        Intent intent = new Intent(activity, FilePickerActivity.class);

        Fragment fragment = getFragment();
        if(fragment==null){
            activity.startActivityForResult(intent, requestCode);
        }else{
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public com.netwinfin.filepicker.ConfigBuilder addConfigBuilder(){
        return new com.netwinfin.filepicker.ConfigBuilder(this);
    }


    @Nullable
    Activity getActivity(){
        return mActivity.get();
    }

    @Nullable
    Fragment getFragment(){
        return mContext.get();
    }

}

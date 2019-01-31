package cn.hayring.caseanalyst.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.pojo.PojoInstanceCreater;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class ValueSetter extends AppCompatActivity {
    public static final String TYPE = "type";
    public static final String CREATE_OR_NOT = "create_or_not";
    public static final int CASE = 0;
    public static final String NEW_CASE = "new_case";
    private Intent requestInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_setter);
        requestInfo = getIntent();
        switch (requestInfo.getIntExtra(TYPE, -1)) {
            case CASE: {
                //加载页面并设置
                Case newCase = PojoInstanceCreater.getConanCase();
                requestInfo.putExtra(NEW_CASE, newCase);
                setResult(2, requestInfo);
                finish();
            }
            break;
            default:
                throw new IllegalArgumentException("There is no macthing type");
        }


    }

    class FinishCreateCaseListener implements OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }
}



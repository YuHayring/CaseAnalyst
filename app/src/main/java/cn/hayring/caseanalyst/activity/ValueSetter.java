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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    public static final String DATA = "data";
    public static final String TYPE = "type";
    public static final String CREATE_OR_NOT = "create_or_not";
    public static final int CASE = 0;
    public static final String POSITION = "position";
    private Intent requestInfo;
    private LinearLayout rootLayout;
    private ScrollView sonView;


    private EditText editText1;
    private EditText editText2;

    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_setter);
        rootLayout = findViewById(R.id.value_setter_root_layout);

        requestInfo = getIntent();
        switch (requestInfo.getIntExtra(TYPE, -1)) {
            case CASE: {
                //加载页面
                LayoutInflater inflater = getLayoutInflater();
                sonView = (ScrollView) inflater.inflate(R.layout.case_value_setter, null);
                rootLayout.addView(sonView);
                editText1 = findViewById(R.id.case_name_inputer);
                editText2 = findViewById(R.id.case_info_inputer);
                save = findViewById(R.id.case_save_button);
                save.setOnClickListener(new FinishCreateCaseListener());
                if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
                    Case caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
                    editText1.setText(caseInstance.getName());
                    editText2.setText(caseInstance.getInfo());
                }



            }
            break;
            default:
                throw new IllegalArgumentException("There is no macthing type");
        }


    }

    class FinishCreateCaseListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (requestInfo.getBooleanExtra(CREATE_OR_NOT, false)) {
                Case newCase = new Case();
                newCase.setName(editText1.getText().toString());
                newCase.setInfo(editText2.getText().toString());
                requestInfo.putExtra(DATA, newCase);
            } else if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
                Case caseInstance = (Case) requestInfo.getSerializableExtra(DATA);
                caseInstance.setName(editText1.getText().toString());
                caseInstance.setInfo(editText2.getText().toString());
                requestInfo.putExtra(DATA, caseInstance);
            }
            setResult(2, requestInfo);
            finish();
        }
    }
}



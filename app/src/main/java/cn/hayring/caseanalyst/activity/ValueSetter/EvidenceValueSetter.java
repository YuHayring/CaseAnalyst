package cn.hayring.caseanalyst.activity.ValueSetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.activity.ListActivity.RelationshipListActivity;
import cn.hayring.caseanalyst.pojo.Evidence;
import cn.hayring.caseanalyst.pojo.Relationship;
import cn.hayring.caseanalyst.utils.Pointer;

public class EvidenceValueSetter extends ValueSetter {

    //标记头像是否改过
    protected boolean imageChanged;

    /***
     * 图片存储
     */
    Bitmap image;

    /***
     * 头像显示View
     */
    ImageView headImage;


    /***
     * 此ValueSetter管理的证据实例
     */
    Evidence evidenceInstance;

    /***
     * 数量编辑器
     */
    EditText countInputer;


    /***
     * 人-物关系入口
     */
    TextView manThingRelationshipEnter;


    /***
     * 组织-物关系入口
     */
    TextView orgThingRelationshipEnter;

    @Override
    protected void initView() {
        super.initView();
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.evidence_value_setter, null);
        rootLayout.addView(sonView);
        //注册控件,信息显示
        nameInputer = findViewById(R.id.evidence_name_inputer);
        infoInputer = findViewById(R.id.evidence_info_inputer);
        countInputer = findViewById(R.id.evidence_count_inputer);
        saveButton = findViewById(R.id.evidence_save_button);
        headImage = findViewById(R.id.evidence_image);

        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            //evidenceInstance = (Evidence) requestInfo.getSerializableExtra(DATA);
            evidenceInstance = (Evidence) Pointer.getPoint();


            nameInputer.setText(evidenceInstance.getName());
            infoInputer.setText(evidenceInstance.getInfo());
            Integer count = evidenceInstance.getCount();
            if (count != null) {
                countInputer.setText(count.toString());
            }

            //判断是否有头像并加载
            if (evidenceInstance.getImageIndex() != null) {
                try {
                    FileInputStream headIS = openFileInput(evidenceInstance.getImageIndex() + ".jpg");
                    image = BitmapFactory.decodeStream(headIS);
                    headImage.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            evidenceInstance = caseInstance.createEvidence();
        }
        manThingRelationshipEnter = sonView.findViewById(R.id.man_thing_relationship_text_view);
        orgThingRelationshipEnter = sonView.findViewById(R.id.org_thing_relationship_text_view);



        //设置监听器
        saveButton.setOnClickListener(new EvidenceFinishEditListener());
        headImage.setOnClickListener(view -> {
            Intent importIntent = new Intent(Intent.ACTION_GET_CONTENT);
            importIntent.setType("image/*");//选择图片
            importIntent.addCategory(Intent.CATEGORY_OPENABLE);
            setResult(Activity.RESULT_OK, importIntent);
            //importIntent.putExtra("return-data", true);
            startActivityForResult(importIntent, 2); //对应onActivityResult()
        });

        View.OnClickListener relationshipEnterListener = new EditRelationshipListener();
        orgThingRelationshipEnter.setOnClickListener(relationshipEnterListener);
        orgThingRelationshipEnter.setOnClickListener(relationshipEnterListener);
    }


    /***
     * 保存按钮监听器
     */
    class EvidenceFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            /*if (evidenceInstance == null) {
                evidenceInstance = caseInstance.createEvidence();
            }*/

            evidenceInstance.setName(nameInputer.getText().toString());
            evidenceInstance.setInfo(infoInputer.getText().toString());
            String countStr = countInputer.getText().toString();
            if (!"".equals(countStr) && isInteger(countStr)) {
                evidenceInstance.setCount(Integer.valueOf(countStr));
            }
            //requestInfo.putExtra(DATA, evidenceInstance);

            //判断头像是否变化，是则保存并写入
            if (imageChanged) {
                try {
                    FileOutputStream fo;
                    File f;
                    int imageIndex;
                    if (evidenceInstance.getImageIndex() == null) {
                        do {
                            imageIndex = random.nextInt();
                            f = new File(getFilesDir().getPath() + "/" + imageIndex + ".jpg");
                        } while (f.exists());
                        evidenceInstance.setImageIndex(imageIndex);
                    } else {
                        imageIndex = evidenceInstance.getImageIndex();
                    }
                    fo = openFileOutput(imageIndex + ".jpg", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            requestInfo.putExtra(CHANGED, true);
            if (requestInfo.getBooleanExtra(CREATE_OR_NOT, false)) {
                Pointer.setPoint(evidenceInstance);
            }
        }
    }


    /***
     * 关系编辑入口监听器
     */
    class EditRelationshipListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent request = new Intent(EvidenceValueSetter.this, RelationshipListActivity.class);
            if (view.getId() == R.id.org_thing_relationship_text_view) {
                //request.putExtra(ValueSetter.DATA, evidenceInstance.getOrgThingRelationships());
                Pointer.setPoint(evidenceInstance.getOrgThingRelationships());
                request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_EVIDENCE);
            } else if (view.getId() == R.id.man_thing_relationship_text_view) {
                //request.putExtra(ValueSetter.DATA, evidenceInstance.getManThingRelationships());
                Pointer.setPoint(evidenceInstance.getManThingRelationships());
                request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_EVIDENCE);
            } else {
                throw new IllegalArgumentException("Error View Id");
            }
            //request.putExtra(ValueSetter.CONNECTOR, evidenceInstance);
            Pointer.setConnector(evidenceInstance);
            startActivityForResult(request, 1);
        }
    }

    /***
     * 编辑完成调用
     * @author Hayring
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //将返回的头像存进内存
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            /*try {
                Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = getContentResolver().query(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor.getString(actual_image_column_index);
                File file = new File(img_path);
                if (file.exists()) {
                    FileInputStream headIS = new FileInputStream(file);
                    Bitmap headBitmap = BitmapFactory.decodeStream(headIS);
                    headImage.setImageBitmap(headBitmap);
                    imageChanged = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "选择的文件无效，请选择从 文件管理→内部存储路径 选择合适的文件", Toast.LENGTH_LONG).show();
            }*/

            //Bundle extras = data.getExtras();
            try {

                Uri uri = data.getData();
                InputStream is = getContentResolver().openInputStream(uri);
                image = BitmapFactory.decodeStream(is);
                //Bitmap headBitmap = data.getParcelableExtra("data");
                headImage.setImageBitmap(image);

                imageChanged = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        /*//未改变就结束
        if (!itemTransporter.getBooleanExtra(ValueSetter.CHANGED, true)) {
            return;
        }
        ArrayList data = (ArrayList) itemTransporter.getSerializableExtra(ValueSetter.DATA);
        int type = itemTransporter.getIntExtra(ValueSetter.RELATIONSHIP_TYPE, -1);
        if (type == Relationship.ORG_EVIDENCE) {
            evidenceInstance.setOrgThingRelationships(data);
        } else if (type == Relationship.MAN_EVIDENCE) {
            evidenceInstance.setManThingRelationships(data);
        } else {
            throw new IllegalArgumentException("Error relationship type!");
        }
*/

    }

    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        evidenceInstance = null;
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }


}

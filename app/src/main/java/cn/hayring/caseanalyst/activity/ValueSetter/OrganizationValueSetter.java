package cn.hayring.caseanalyst.activity.ValueSetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
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
import cn.hayring.caseanalyst.pojo.Organization;
import cn.hayring.caseanalyst.pojo.Relationship;
import cn.hayring.caseanalyst.utils.Pointer;

public class OrganizationValueSetter extends ValueSetter {

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
     * 此ValueSetter管理的组织实例
     */
    Organization orgInstance;


    /***
     * 组织-组织关系入口
     */
    TextView orgOrgRelationshipEnter;

    /***
     * 人-组织关系入口
     */
    TextView manOrgRelationshipEnter;

    /***
     * 组织-事关系入口
     */
    TextView orgEventRelationshipEnter;

    /***
     * 组织-物关系入口
     */
    TextView orgThingRelationshipEnter;

    /***
     * 加载页面
     */
    @Override
    protected void initView() {
        super.initView();
        //加载layout实例
        LayoutInflater inflater = getLayoutInflater();
        sonView = (ScrollView) inflater.inflate(R.layout.org_value_setter, null);
        rootLayout.addView(sonView);

        //注册控件,信息显示
        nameInputer = findViewById(R.id.org_name_inputer);
        infoInputer = findViewById(R.id.org_info_inputer);
        saveButton = findViewById(R.id.org_save_button);
        headImage = findViewById(R.id.org_image);



        if (!requestInfo.getBooleanExtra(CREATE_OR_NOT, true)) {
            //orgInstance = (Organization) requestInfo.getSerializableExtra(DATA);
            orgInstance = (Organization) Pointer.getPoint();


            nameInputer.setText(orgInstance.getName());
            infoInputer.setText(orgInstance.getInfo());

            //判断是否有头像并加载
            if (orgInstance.getImageIndex() != null) {
                try {
                    FileInputStream headIS = openFileInput(orgInstance.getImageIndex() + ".jpg");
                    image = BitmapFactory.decodeStream(headIS);
                    headImage.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            orgInstance = caseInstance.createOrganization();
        }

        orgEventRelationshipEnter = sonView.findViewById(R.id.org_event_relationship_text_view);
        orgOrgRelationshipEnter = sonView.findViewById(R.id.org_org_relationship_text_view);
        manOrgRelationshipEnter = sonView.findViewById(R.id.man_org_relationship_text_view);
        orgThingRelationshipEnter = sonView.findViewById(R.id.org_thing_relationship_text_view);

        //设置监听器
        saveButton.setOnClickListener(new OrganizationFinishEditListener());
        headImage.setOnClickListener(view -> {
            Intent importIntent = new Intent(Intent.ACTION_GET_CONTENT);
            importIntent.setType("image/*");//选择图片
            importIntent.addCategory(Intent.CATEGORY_OPENABLE);
            setResult(Activity.RESULT_OK, importIntent);
            startActivityForResult(importIntent, 2); //对应onActivityResult()
        });

        View.OnClickListener relationshipEnterListener = new EditRelationshipListener();

        orgEventRelationshipEnter.setOnClickListener(relationshipEnterListener);
        orgThingRelationshipEnter.setOnClickListener(relationshipEnterListener);
        orgOrgRelationshipEnter.setOnClickListener(relationshipEnterListener);
        manOrgRelationshipEnter.setOnClickListener(relationshipEnterListener);

    }

    /***
     * 保存按钮监听器
     */
    class OrganizationFinishEditListener extends FinishEditListener {
        @Override
        void editReaction() {
            /*if (orgInstance == null) {
                orgInstance = caseInstance.createOrganization();
            }*/
            orgInstance.setName(nameInputer.getText().toString());
            orgInstance.setInfo(infoInputer.getText().toString());
            //requestInfo.putExtra(DATA, orgInstance);
            requestInfo.putExtra(CHANGED, true);

            //判断头像是否变化，是则保存并写入
            if (imageChanged) {
                try {
                    FileOutputStream fo;
                    File f;
                    int imageIndex;
                    if (orgInstance.getImageIndex() == null) {
                        do {
                            imageIndex = random.nextInt();
                            f = new File(getFilesDir().getPath() + "/" + imageIndex + ".jpg");
                        } while (f.exists());
                        orgInstance.setImageIndex(imageIndex);
                    } else {
                        imageIndex = orgInstance.getImageIndex();
                    }
                    fo = openFileOutput(imageIndex + ".jpg", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (requestInfo.getBooleanExtra(CREATE_OR_NOT, false)) {
                Pointer.setPoint(orgInstance);
            }
        }
    }


    /***
     * 关系编辑入口监听器
     */
    class EditRelationshipListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent request = new Intent(OrganizationValueSetter.this, RelationshipListActivity.class);
            switch (view.getId()) {
                case R.id.org_event_relationship_text_view: {
                    //request.putExtra(ValueSetter.DATA, orgInstance.getOrgEventRelationships());
                    Pointer.setPoint(orgInstance.getOrgEventRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_EVENT);
                }
                break;
                case R.id.org_org_relationship_text_view: {
                    //request.putExtra(ValueSetter.DATA, orgInstance.getOrgOrgRelationships());
                    Pointer.setPoint(orgInstance.getOrgOrgRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_ORG);
                }
                break;
                case R.id.man_org_relationship_text_view: {
                    //request.putExtra(ValueSetter.DATA, orgInstance.getManOrgRelationships());
                    Pointer.setPoint(orgInstance.getManOrgRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.MAN_ORG);
                }
                break;
                case R.id.org_thing_relationship_text_view: {
                    //request.putExtra(ValueSetter.DATA, orgInstance.getOrgThingRelationships());
                    Pointer.setPoint(orgInstance.getOrgThingRelationships());
                    request.putExtra(ValueSetter.RELATIONSHIP_TYPE, Relationship.ORG_EVIDENCE);
                }
                break;
                default:
                    throw new IllegalArgumentException("Error View Id");
            }
            //request.putExtra(ValueSetter.CONNECTOR, orgInstance);
            Pointer.setConnector(orgInstance);
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

/*        //未改变就结束
        if (!itemTransporter.getBooleanExtra(ValueSetter.CHANGED, true)) {
            return;
        }
        ArrayList data = (ArrayList) itemTransporter.getSerializableExtra(ValueSetter.DATA);
        switch (itemTransporter.getIntExtra(ValueSetter.RELATIONSHIP_TYPE, -1)) {
            case Relationship.ORG_EVENT: {
                orgInstance.setOrgEventRelationships(data);
            }
            break;
            case Relationship.ORG_ORG: {
                orgInstance.setOrgOrgRelationships(data);
            }
            break;
            case Relationship.MAN_ORG: {
                orgInstance.setManOrgRelationships(data);
            }
            break;
            case Relationship.ORG_EVIDENCE: {
                orgInstance.setOrgThingRelationships(data);
            }
            break;
            default:
                throw new IllegalArgumentException("Error relationship type!");
        }*/


    }




    /***
     * 返回键不保存，返回未改动
     */
    @Override
    public void finish() {
        orgInstance = null;
        requestInfo.putExtra(CHANGED, false);
        setResult(2, requestInfo);
        super.finish();
    }


}

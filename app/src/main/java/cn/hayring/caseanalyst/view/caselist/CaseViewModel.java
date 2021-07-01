package cn.hayring.caseanalyst.view.caselist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import java.util.List;

import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.viewmodel.AsyncCallBack;

/**
 * @author hayring
 * @date 6/21/21 7:26 PM
 */
public class CaseViewModel extends ViewModel {

    public CaseViewModel() {
    }

    /**
     * model 层单例
     */
    CaseRepository caseModel = CaseModel.getInstance();

    /**
     * 带有案件列表的 livedata
     */
    MutableLiveData<List<Case>> caseListData = new MutableLiveData<>();

    /**
     * 单个案件 livedata
     */
    MutableLiveData<Case> singleCase = new MutableLiveData<>();

    /**
     * 上一次插入的自增 id
     */
    MutableLiveData<Long> autoincrementId = new MutableLiveData<>();


    /**
     * 获取案件列表
     */
    public void getCaseList() {
//        List<Case> cases = caseModel.getCases();
//        caseListData.postValue(cases);
        caseModel.getCases(getCaseListCallback);
    }

    private final AsyncCallBack<List<Case>> getCaseListCallback = new AsyncCallBack<List<Case>>() {
        @Override
        public void callBack(List<Case> cases) {
            caseListData.postValue(cases);
        }
    };


    /**
     * 删除案件
     *
     * @param id 案件 id
     */
    public void deleteCase(Long id) {
        caseModel.deleteCase(id, deleteCaseListCallback);
    }


    private final AsyncCallBack<Boolean> deleteCaseListCallback = new AsyncCallBack<Boolean>() {
        @Override
        public void callBack(Boolean bool) {
            Log.i("delete", bool.toString());
        }
    };


    /**
     * 查询单个案件
     *
     * @param id 案件 id
     */
    public void getCase(Long id) {
        caseModel.getCase(id, caseAsyncCallBack);
    }

    private final AsyncCallBack<Case> caseAsyncCallBack = new AsyncCallBack<Case>() {
        @Override
        public void callBack(Case caxe) {
            Log.i("getCase", caxe.toString());
        }
    };


    public void updateCase(Case caxe) {
        caseModel.updateCase(caxe, updateCaseListCallback);
    }

    private final AsyncCallBack<Boolean> updateCaseListCallback = new AsyncCallBack<Boolean>() {
        @Override
        public void callBack(Boolean bool) {
            Log.i("update", bool.toString());
        }
    };


    public void addCase() {
        caseModel.addCase(addCaseListCallback);
    }

    private final AsyncCallBack<Long> addCaseListCallback = new AsyncCallBack<Long>() {
        @Override
        public void callBack(Long id) {
            autoincrementId.postValue(id);
        }
    };


    public MutableLiveData<Long> getAutoincrementId() {
        return autoincrementId;
    }

    public LiveData<List<Case>> getCaseListData() {
        return caseListData;
    }

    public MutableLiveData<Case> getSingleCase() {
        return singleCase;
    }
}

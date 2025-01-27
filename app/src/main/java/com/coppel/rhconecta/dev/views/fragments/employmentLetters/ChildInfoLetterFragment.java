package com.coppel.rhconecta.dev.views.fragments.employmentLetters;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.business.interfaces.IChangeVisibilityElements;
import com.coppel.rhconecta.dev.business.interfaces.IServicesContract;
import com.coppel.rhconecta.dev.business.models.CoppelServicesLettersGenerateRequest;
import com.coppel.rhconecta.dev.business.models.LetterChildrenData;
import com.coppel.rhconecta.dev.business.models.LetterChildrenDataVO;
import com.coppel.rhconecta.dev.business.models.LetterConfigResponse;
import com.coppel.rhconecta.dev.business.models.PreviewDataVO;
import com.coppel.rhconecta.dev.business.presenters.CoppelServicesPresenter;
import com.coppel.rhconecta.dev.business.utils.ServicesError;
import com.coppel.rhconecta.dev.business.utils.ServicesResponse;
import com.coppel.rhconecta.dev.views.activities.ConfigLetterActivity;
import com.coppel.rhconecta.dev.views.adapters.ChildInfoLetterRecyclerAdapter;
import com.coppel.rhconecta.dev.views.dialogs.DialogFragmentLoader;
import com.coppel.rhconecta.dev.views.utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.coppel.rhconecta.dev.views.utils.AppConstants.BUNDLE_LETTER;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_NUM_COLABORADOR;
import static com.coppel.rhconecta.dev.views.utils.AppConstants.SHARED_PREFERENCES_TOKEN;

public class ChildInfoLetterFragment extends Fragment implements View.OnClickListener, IServicesContract.View,
        IChangeVisibilityElements {

    public static final String TAG = ChildInfoLetterFragment.class.getSimpleName();
    private ConfigLetterActivity parent;
    private DialogFragmentLoader dialogFragmentLoader;
    private CoppelServicesPresenter coppelServicesPresenter;
    private LetterConfigResponse letterConfigResponse;
    private List<LetterChildrenDataVO> childrenDataVOList;
    private ChildInfoLetterRecyclerAdapter childInfoLetterRecyclerAdapter;
    private long mLastClickTime = 0;

    @BindView(R.id.rcvChilds)
    RecyclerView rcvChilds;
    @BindView(R.id.btnNext)
    Button btnNext;
    private int typeLetter;
    private boolean hasStamp;

    private com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation ILettersNavigation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ILettersNavigation = (com.coppel.rhconecta.dev.business.interfaces.ILettersNavigation) context;
    }

    public static ChildInfoLetterFragment getInstance(int tipoCarta){
        ChildInfoLetterFragment fragment = new ChildInfoLetterFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_LETTER,tipoCarta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_data_letter, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        parent = (ConfigLetterActivity) getActivity();
        //parent.setToolbarTitle(getString(R.string.bonus));
        coppelServicesPresenter = new CoppelServicesPresenter(this, parent);
        rcvChilds.setHasFixedSize(true);
        rcvChilds.setLayoutManager(new LinearLayoutManager(getContext()));
        childrenDataVOList = new ArrayList<>();
        childInfoLetterRecyclerAdapter = new ChildInfoLetterRecyclerAdapter(getContext(), childrenDataVOList,this);
        rcvChilds.setAdapter(childInfoLetterRecyclerAdapter);
        btnNext.setOnClickListener(this);
        btnNext.setVisibility(View.INVISIBLE);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeLetter = getArguments().getInt(BUNDLE_LETTER);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                parent.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnNext:

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1200){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if(childInfoLetterRecyclerAdapter.hasFielsdSelected()){
                    ILettersNavigation.showFragmentAtPosition(0,getData());

                }else {
                    Toast.makeText(getActivity(),"Debes seleccionar al menos un nombre",Toast.LENGTH_SHORT).show();
                }



                break;
        }
    }

    public void setDataChild(List<LetterChildrenData> letterChildrenDataList){

        if(childrenDataVOList != null)
            childrenDataVOList.clear();

        for (LetterChildrenData data : letterChildrenDataList){
            childrenDataVOList.add(new LetterChildrenDataVO(data));
        }

        childInfoLetterRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showResponse(ServicesResponse response) {

    }

    @Override
    public void showError(ServicesError coppelServicesError) {

    }

    @Override
    public void showProgress() {
        //dialogFragmentLoader = new DialogFragmentLoader();
      // dialogFragmentLoader.show(parent.getSupportFragmentManager(), DialogFragmentLoader.TAG);
    }

    @Override
    public void hideProgress() {
       // dialogFragmentLoader.close();
    }

    private void getPreviewLetter(boolean stampLetter){
        this.hasStamp = stampLetter;
        List<LetterChildrenDataVO> childrenDataVOs =childInfoLetterRecyclerAdapter.getFieldsLetter();
        String token = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_TOKEN);
        String numEmployer = AppUtilities.getStringFromSharedPreferences(getActivity(),SHARED_PREFERENCES_NUM_COLABORADOR);
      //  coppelServicesPresenter.requestLettersPreviewView(numEmployer,typeLetter,fieldsLetter,stampLetter,token);
    }

    @Override
    public void changeVisibiliyElement(int visibiliy) {
        btnNext.setVisibility(visibiliy);

    }

    private PreviewDataVO getData(){
        PreviewDataVO previewDataVO = parent.getPreviewDataVO();
        CoppelServicesLettersGenerateRequest.Data dataOptional =  previewDataVO.getDataOptional();
        dataOptional.setChildrenData(childInfoLetterRecyclerAdapter.getChildSelected());

        return previewDataVO;
    }
}
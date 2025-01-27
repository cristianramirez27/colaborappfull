package com.coppel.rhconecta.dev.business.models;

import androidx.annotation.Keep;
import androidx.fragment.app.Fragment;

import com.coppel.rhconecta.dev.business.interfaces.IEnumTab;

import java.util.List;

/**
 * @author flima on 22/05/2017.
 */
@Keep
public class ViewPagerData<T extends IEnumTab> {

    private List<Fragment> fragmentList;
    private T[] tabData;

    public ViewPagerData(List<Fragment> fragmentList, T... tabData) {
        this.fragmentList = fragmentList;
        this.tabData = tabData;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public T[] getTabData() {
        return tabData;
    }
}

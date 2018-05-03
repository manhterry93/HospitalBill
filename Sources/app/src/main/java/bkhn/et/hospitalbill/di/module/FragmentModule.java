package bkhn.et.hospitalbill.di.module;

import bkhn.et.hospitalbill.base.BaseFragment;
import dagger.Module;

/**
 * Created by PL_itto on 5/3/2018.
 */
@Module
public class FragmentModule {
    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        mFragment = fragment;
    }

}

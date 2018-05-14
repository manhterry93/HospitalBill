package bkhn.et.hospitalbill.base;

/**
 * Created by PL_itto on 5/10/2018.
 */

public class BasePresenter<V extends IBaseContract.IBaseView> implements IBaseContract.IBasePresenter<V> {
    protected V mView;

    @Override
    public void onAttach(V view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }
}

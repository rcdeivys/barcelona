package com.BarcelonaSC.BarcelonaApp.commons.mvp;

/**
 * Created by Carlos-pc on 26/09/2017.
 */

public class MVPContract {

    public interface View {

    }

    public interface Model {
    }

    public interface Presenter<T> {
        void onAttach(T view);
        void onDetach();
    }

}

package ge.turtlecat.theorytest.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ge.turtlecat.theorytest.ui.tm.TicketManager;

/**
 * Created by Alex on 11/21/2015.
 */
public abstract class BaseFragment extends Fragment {

    protected ViewGroup mainView;
    protected TicketManager tm;
    private Bundle argsBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tm = TicketManager.getInstance();
        mainView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);

        onCreate();
        return mainView;
    }

    protected View findViewById(int id) {
        return mainView.findViewById(id);
    }

    /**
     * String არგუმენტის დამატება ფრაგმენტის არგუმენტების ბანდლში, არ საჭიროებს ბანდლის ცალკე შექმნას.<br>
     * მაგ: <code>Fragment fragment = new BaseFragment().setStringArgument(key, value)</code>
     *
     * @param key   String
     * @param value String
     * @return აბრუნებს BaseFragment-ის ობიექტს
     */
    public BaseFragment setStringArgument(String key, String value) {
        getArgsBundle().putString(key, value);
        return this;
    }

    /**
     * double არგუმენტის დამატება ფრაგმენტის არგუმენტების ბანდლში, არ საჭიროებს ბანდლის ცალკე შექმნას.<br>
     * მაგ: <code>Fragment fragment = new BaseFragment().setDoubleArgument(key, value)</code>
     *
     * @param key   String
     * @param value double
     * @return აბრუნებს BaseFragment-ის ობიექტს
     */
    public BaseFragment setDoubleArgument(String key, double value) {
        getArgsBundle().putDouble(key, value);
        return this;
    }

    /**
     * int არგუმენტის დამატება ფრაგმენტის არგუმენტების ბანდლში, არ საჭიროებს ბანდლის ცალკე შექმნას.<br>
     * მაგ: <code>Fragment fragment = new BaseFragment().setIntArgument(key, value)</code>
     *
     * @param key   String
     * @param value int
     * @return აბრუნებს BaseFragment-ის ობიექტს
     */
    public BaseFragment setIntArgument(String key, int value) {
        getArgsBundle().putInt(key, value);
        return this;
    }

    /**
     * boolean არგუმენტის დამატება ფრაგმენტის არგუმენტების ბანდლში, არ საჭიროებს ბანდლის ცალკე შექმნას.<br>
     * მაგ: <code>Fragment fragment = new BaseFragment().setBooleanArgument(key, value)</code>
     *
     * @param key   String
     * @param value boolean
     * @return აბრუნებს BaseFragment-ის ობიექტს
     */
    public BaseFragment setBooleanArgument(String key, boolean value) {
        getArgsBundle().putBoolean(key, value);
        return this;
    }

    public BaseFragment setParcelableArgument(String key, Parcelable value) {
        getArgsBundle().putParcelable(key, value);
        return this;
    }

    private Bundle getArgsBundle() {
        if (argsBundle == null) argsBundle = new Bundle();
        return argsBundle;
    }

    public BaseFragment commitBundle() {
        setArguments(getArgsBundle());
        return this;
    }


    protected abstract int getLayoutId();

    protected abstract void onCreate();
}

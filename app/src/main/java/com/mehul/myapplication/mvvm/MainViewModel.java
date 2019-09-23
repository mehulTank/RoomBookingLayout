package com.mehul.myapplication.mvvm;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;

import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Kailash Patel
 */

public class MainViewModel extends Observable {

    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainNavigator storeListNavigator;

    private ObservableField<String> text;

    private String[] roomList;
    private int position;
    private String country;


    public MainViewModel(@NonNull Context context, MainNavigator storeListNavigator) {
        this.context = context;
        this.storeListNavigator = storeListNavigator;
        text = new ObservableField<>();


    }


    public void getStoreList(final String locationId, final String offset) {


        try {
           /* BeautyApplication appController = BeautyApplication.getmInstance();
            UsersService usersService = appController.getUserService();


            Disposable disposable = usersService.doGetLocation(locationId, offset)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<StoreResponseData>() {
                        @Override
                        public void accept(StoreResponseData userResponse) throws Exception {
                            storeListNavigator.storeResponce(userResponse);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            storeListNavigator.handleError(throwable);
                        }
                    });

            compositeDisposable.add(disposable);*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void selectedItem(Spinner spinner, int id) {
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "" + parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }


    @BindingAdapter(value = {"bind:selectedValue", "bind:selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner pAppCompatSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<String>) pAppCompatSpinner.getAdapter()).getPosition(newSelectedValue);
            pAppCompatSpinner.setSelection(pos, true);
        }
    }

    @InverseBindingAdapter(attribute = "bind:selectedValue", event = "bind:selectedValueAttrChanged")
    public static String captureSelectedValue(AppCompatSpinner pAppCompatSpinner) {
        return (String) pAppCompatSpinner.getSelectedItem();
    }


}


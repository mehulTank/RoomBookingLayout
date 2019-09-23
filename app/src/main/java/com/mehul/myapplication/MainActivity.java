package com.mehul.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mehul.myapplication.databinding.ActivityMainBinding;
import com.mehul.myapplication.mvvm.MainNavigator;
import com.mehul.myapplication.mvvm.MainViewModel;

public class MainActivity extends AppCompatActivity implements MainNavigator {


    ActivityMainBinding activityMainBinding;
    MainViewModel mainViewModel;
    MainNavigator mainNavigator;
    private LinearLayout llMainContainer;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initDataBinding();

        initComponent();


    }

    private void initComponent() {


        /*String[] paymentOptions = getResources().getStringArray(R.array.array_room_list);*/

        /*activityMainBinding.setSpinAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, paymentOptions));
        */
        llMainContainer = activityMainBinding.llMainContainer;

        activityMainBinding.spnSelectNumberOfRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                removeView();

                count = Integer.parseInt(String.valueOf(activityMainBinding.spnSelectNumberOfRoom.getItemAtPosition(position)));
                for (int i = 1; i <= count; i++) {
                    addView(i);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activityMainBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("--- value of ", "---" + getEditTextValue(llMainContainer));

                Toast.makeText(MainActivity.this, getEditTextValue(llMainContainer) + "", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initDataBinding() {

        mainNavigator = this;
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this, mainNavigator);
        activityMainBinding.setMainViewModel(mainViewModel);
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    private void removeView() {
        if (llMainContainer != null && llMainContainer.getChildCount() > 0) {
            llMainContainer.removeAllViews();
        }
    }


    public void addView(final int count) {

        final View viewRoom = LayoutInflater.from(this).inflate(R.layout.layout_room_view, llMainContainer, false);
        TextView tvRoomNo = viewRoom.findViewById(R.id.tv_room_no);
        tvRoomNo.setText("Room" + " " + count);

        final LinearLayout llAdultAgeView = viewRoom.findViewById(R.id.ll_Adult_age_view);
        final LinearLayout llChildAgeView = viewRoom.findViewById(R.id.ll_child_age_view);
        final Spinner spAdultSelection = viewRoom.findViewById(R.id.ll_room_sp_adult);

        final Spinner spChildAgeSelection = viewRoom.findViewById(R.id.ll_room_sp_child);


        spChildAgeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                removeChildAgeView(llChildAgeView);

                int adultCount = Integer.parseInt(String.valueOf(spChildAgeSelection.getItemAtPosition(position)));
                for (int i = 1; i <= adultCount; i++) {
                    addChildAgeView(i, llChildAgeView);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spAdultSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                removeAdultAgeView(llAdultAgeView);

                int adultCount = Integer.parseInt(String.valueOf(spAdultSelection.getItemAtPosition(position)));
                for (int i = 1; i <= adultCount; i++) {
                    addAdultAgeView(i, llAdultAgeView);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        llMainContainer.addView(viewRoom);

    }

    private void removeChildAgeView(LinearLayout llChildAgeView) {


        if (llChildAgeView != null && llChildAgeView.getChildCount() > 0) {
            llChildAgeView.removeAllViews();
        }
    }

    private void removeAdultAgeView(LinearLayout llAdultAgeView) {

        if (llAdultAgeView != null && llAdultAgeView.getChildCount() > 0) {
            llAdultAgeView.removeAllViews();
        }
    }

    private void addAdultAgeView(int i, LinearLayout llAdultAgeView) {

        final View viewAdultView = LayoutInflater.from(this).inflate(R.layout.layout_edittext, llAdultAgeView, false);


        llAdultAgeView.addView(viewAdultView);
    }


    private void addChildAgeView(int i, LinearLayout llAdultAgeView) {

        final View viewAdultView = LayoutInflater.from(this).inflate(R.layout.layout_edittext, llAdultAgeView, false);


        llAdultAgeView.addView(viewAdultView);
    }


    private String getEditTextValue(LinearLayout llPhonesContainer) {
        String values = "";
        int childCount = llPhonesContainer.getChildCount();
        if (childCount > 0) {
            for (int c = 0; c < childCount; c++) {
                String roomValue = "";
                final View childView = llPhonesContainer.getChildAt(c);

                LinearLayout llAdultAgeView = (childView.findViewById(R.id.ll_Adult_age_view));
                LinearLayout llChildAgeView = (childView.findViewById(R.id.ll_child_age_view));

                int adultAgeCount = llAdultAgeView.getChildCount();
                int childAgeCount = llChildAgeView.getChildCount();

                final TextView tvRoomNo = (childView.findViewById(R.id.tv_room_no));

                String adultAge = "";


                boolean isAdultAge = true;
                for (int i = 0; i < adultAgeCount; i++) {

                    final View viewAgeAdult = llAdultAgeView.getChildAt(i);

                    final EditText edtAdultAge = (viewAgeAdult.findViewById(R.id.ed_age_user));
                    if (edtAdultAge != null && !edtAdultAge.getText().toString().isEmpty()) {


                        if (Integer.parseInt(edtAdultAge.getText().toString()) < 18) {

                            //roomValue = tvRoomNo.getText().toString() + "Age shoud be Greter 18";

                            isAdultAge = false;

                            break;

                        } else {
                            if (i == (adultAgeCount - 1)) {
                                adultAge = adultAge + edtAdultAge.getText().toString();
                            } else {
                                adultAge = adultAge + edtAdultAge.getText().toString() + ",";
                            }
                        }


                    }

                }
                if (!isAdultAge) {
                    roomValue = tvRoomNo.getText().toString() + "Age shoud be Greter 18";
                } else {
                    roomValue = tvRoomNo.getText().toString() + " No of adult  " + adultAgeCount + " Age Are" + adultAge;
                }


                String childAge = "";

                boolean isChildAge = true;

                for (int i = 0; i < childAgeCount; i++) {

                    final View viewChildAge = llChildAgeView.getChildAt(i);

                    final EditText edtAdultAge = (viewChildAge.findViewById(R.id.ed_age_user));


                    if (edtAdultAge != null && !edtAdultAge.getText().toString().isEmpty()) {

                        if (Integer.parseInt(edtAdultAge.getText().toString()) > 18) {

                            isChildAge = false;

                        } else {
                            if (i == (childAgeCount - 1)) {
                                childAge = childAge + edtAdultAge.getText().toString();
                            } else {
                                childAge = childAge + edtAdultAge.getText().toString() + ",";
                            }
                        }


                    }


                }

                if (!isChildAge) {

                    roomValue = roomValue + "Age shoud be less 18 in child ";

                } else {
                    roomValue = roomValue + " No of child  " + childAgeCount + " Age Are" + childAge;
                }


                values = values + "\n \n" + roomValue;

            }


        }

        return values;
    }
}

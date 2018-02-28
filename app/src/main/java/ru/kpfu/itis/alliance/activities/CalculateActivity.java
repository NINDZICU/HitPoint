package ru.kpfu.itis.alliance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kpfu.itis.alliance.Constants;
import ru.kpfu.itis.alliance.R;
import ru.kpfu.itis.alliance.ResourceCalculator;
import ru.kpfu.itis.alliance.allianceAPI.AllianceAPI;
import ru.kpfu.itis.alliance.allianceAPI.Api;
import ru.kpfu.itis.alliance.models.CalculationResult;
import ru.kpfu.itis.alliance.models.ResponseSuccess;

public class CalculateActivity extends AppCompatActivity {
    public static final int CUSTOMER = 1;
    public static final int DESIGNER = 2;
    public static final int EXECUTOR = 3;
    public static final int PRIVATEPERSON = 4;
    public static final String PHONE_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private final int REQUEST_CODE_WHO_ARE = 10;
    private final int REQUEST_CODE_TYPE_OF_SYSTEM = 20;
    private final int REQUEST_CODE_RESULT = 30;

    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Pattern patternPhone = Pattern.compile(PHONE_PATTERN);

    private Activity context = this;
    private TextView toolbarTitle;
    private EditText nameCompany;
    private TextView whoAre;
    private TextView typeCladding;
    private EditText perimetrWall;
    private EditText buildingHeight;
    private EditText squareWindow;
    private EditText quantityWindow;
    private EditText squareDoor;
    private EditText quantityDoor;
    private EditText etEmail;
    private EditText etNumberOfPhone;
    private Button btnCalculate;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    private ResourceCalculator calculator;

    private int typeCladdingValue = Constants.COMPOSITE;
    private int whoAreYou = CalculateActivity.CUSTOMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_activity);

        nameCompany = findViewById(R.id.et_name);
        nameCompany.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) nameCompany.setHint("");
                else nameCompany.setHint("Название объекта");
            }
        });
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.scrollView2);

        whoAre = findViewById(R.id.tv_who_are);
        typeCladding = findViewById(R.id.type_of_cladding);

        perimetrWall = findViewById(R.id.et_perimetr);
//        perimetrWall.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) perimetrWall.setHint("");
//                else perimetrWall.setHint("-");
//            }
//        });
        buildingHeight = findViewById(R.id.et_building_height);
//        buildingHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) buildingHeight.setHint("");
//                else buildingHeight.setHint("-");
//            }
//        });
        squareWindow = findViewById(R.id.et_square_window);
//        squareWindow.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) squareWindow.setHint("");
//                else squareWindow.setHint("-");
//            }
//        });
        quantityWindow = findViewById(R.id.et_quantity_window);
//        quantityWindow.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) quantityWindow.setHint("");
//                else quantityWindow.setHint("-");
//            }
//        });
        squareDoor = findViewById(R.id.et_square_door);
//        squareDoor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) squareDoor.setHint("");
//                else squareDoor.setHint("-");
//            }
//        });
        quantityDoor = findViewById(R.id.et_quantity_door);
//        quantityDoor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) quantityDoor.setHint("");
//                else quantityDoor.setHint("-");
//            }
//        });
        etNumberOfPhone = findViewById(R.id.et_number_of_phone);
        etNumberOfPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) etNumberOfPhone.setHint("");
                else etNumberOfPhone.setHint("Введите номер");
            }
        });
        etEmail = findViewById(R.id.et_email);
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) etEmail.setHint("");
                else etEmail.setHint("Введите email");
            }
        });
        btnCalculate = findViewById(R.id.btn_calculate_price);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbarTitle = myToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Рассчет");

        whoAre.setText("Заказчик");

//        setTypeCladding();
        typeCladding.setText(getTypeCladding());
        setWhoAre();


        whoAre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WhoAreActivity.class);
                intent.putExtra("who_are", DESIGNER);
                startActivityForResult(intent, REQUEST_CODE_WHO_ARE);
            }
        });

        typeCladding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TypeOfSystemActivity.class);
                intent.putExtra("type", Constants.COMPOSITE);
                startActivityForResult(intent, REQUEST_CODE_TYPE_OF_SYSTEM);
            }
        });


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameCompany.length() == 0 || perimetrWall.length() == 0 || buildingHeight.length() == 0 || squareWindow.length() == 0
                        || quantityWindow.length() == 0 || squareDoor.length() == 0 || quantityDoor.length() == 0) {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show();
                } else if (!validatePhone(etNumberOfPhone.getText().toString())) {
                    Toast.makeText(context, "Введите корректный номер телефона", Toast.LENGTH_LONG).show();
                } else if (!validateEmail(etEmail.getText().toString())) {
                    Toast.makeText(context, "Проверьте правильность email", Toast.LENGTH_SHORT).show();
                } else {
//                    Intent intent = new Intent(context, ResultAcitivity.class);
                    setVisibleProgressBar();
                    calculator = new ResourceCalculator(
                            context,
                            Double.valueOf(perimetrWall.getText().toString()),
                            Double.valueOf(buildingHeight.getText().toString()),
                            Double.valueOf(squareWindow.getText().toString()),
                            Integer.valueOf(quantityWindow.getText().toString()),
                            Double.valueOf(squareDoor.getText().toString()),
                            Integer.valueOf(quantityDoor.getText().toString()),
                            whoAreYou);
                    try {
                        calculator.parseJson();
                        final List<CalculationResult> list = calculator.getCalculationResults();
                        AllianceAPI api = Api.getInstance().getApi();
                        Call<ResponseSuccess> call = api.sendData(etEmail.getText().toString(), etNumberOfPhone.getText().toString(), whoAre.getText().toString(), "android", calculator.getTotalSum(), true);

                        call.enqueue(new Callback<ResponseSuccess>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseSuccess> call, @NonNull Response<ResponseSuccess> response) {

                                Intent intent = new Intent(context, ResultAcitivity.class);
                                intent.putExtra("results", (Serializable) list);
                                intent.putExtra("typeCladding", getString(getTypeCladding()));
                                startActivityForResult(intent, REQUEST_CODE_RESULT);
                                Toast.makeText(CalculateActivity.this, "Запрос отправлен", Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseSuccess> call, @NonNull Throwable t) {
                                setVisibleProgressBar();
                                Toast.makeText(CalculateActivity.this, "Что-то пошло не так, повторите позднее", Toast.LENGTH_SHORT).show();
                            }
                        });
//                        ResultFragment resultFragment = ResultFragment.newInstance(list, typeCladdingValue);
//                        resultFragment.setCalculationResults(list);
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .add(R.id.fragment_result, resultFragment, ResultFragment.class.toString())
//                                .addToBackStack(ResultFragment.class.getName())
//                                .commit();

                    } catch (IOException e) {
                        setVisibleProgressBar();
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePhone(String phone) {
        Matcher matcher = patternPhone.matcher(phone);
        return matcher.matches();
    }

    public int getTypeCladding() {
        switch (typeCladdingValue) {
            case Constants.COMPOSITE:
                return R.string.composit;
            case Constants.KERAMOGRANITE:
                return R.string.keramogranit;
            case Constants.FIBROPLITA:
                return R.string.fibroplita;
            case Constants.METALLOKASSETA:
                return R.string.metallokaseta;
        }
        return R.string.composit;
    }

    public void setWhoAre() {
        switch (whoAreYou) {
            case CUSTOMER:
                whoAre.setText(R.string.customer);
                break;
            case EXECUTOR:
                whoAre.setText(R.string.executor);
                break;
            case PRIVATEPERSON:
                whoAre.setText(R.string.private_person);
                break;
            case DESIGNER:
                whoAre.setText(R.string.designer);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TYPE_OF_SYSTEM:
                    typeCladdingValue = data.getIntExtra("type", Constants.COMPOSITE);
                    typeCladding.setText(getTypeCladding());
                    break;
                case REQUEST_CODE_WHO_ARE:
                    whoAreYou = data.getIntExtra("who_are", CUSTOMER);
                    setWhoAre();
                    break;
                case REQUEST_CODE_RESULT:
                    setVisibleProgressBar();
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_CODE_RESULT:
                    finish();
            }
        }
    }
    private void setVisibleProgressBar(){
        if(progressBar.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }
}

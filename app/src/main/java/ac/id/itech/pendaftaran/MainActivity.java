package ac.id.itech.pendaftaran;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonClear;
    AlertDialogManager alert = new AlertDialogManager();
    private static final String TAG = "Register Application";
    private EditText editTextNIK;
    private EditText editTextNAMA_LGKP;
    private EditText editTextTPT_LHR;
    private EditText editTextTGL_LHR;
    private EditText editTextNO_HP;
    private EditText editTextPEMINATAN_JURUSAN;
    private EditText editTextTHN_LULUS;
    private EditText editTextNILAI_AKHIR;
    private EditText editTextASAL_SEKOLAH;
    private EditText editTextASAL_WILAYAH;
    private EditText editTextEMAIL;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;
    private Button buttonInsert;
    private Button buttonView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isConnected()) {
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();
        context = MainActivity.this;
        pDialog = new ProgressDialog(context);
        editTextNILAI_AKHIR = (EditText) findViewById(R.id.editTextNILAI_AKHIR);
        editTextNAMA_LGKP = (EditText) findViewById(R.id.editTextNAMA_LGKP);
        editTextNIK = (EditText) findViewById(R.id.editTextNIK);
        editTextPEMINATAN_JURUSAN = (EditText) findViewById(R.id.editTextPEMINATAN_JURUSAN);
        editTextASAL_SEKOLAH = (EditText) findViewById(R.id.editTextASAL_SEKOLAH);
        editTextASAL_WILAYAH = (EditText) findViewById(R.id.editTextASAL_WILAYAH);
        editTextTHN_LULUS = (EditText) findViewById(R.id.editTextTHN_LULUS);
        editTextTPT_LHR = (EditText) findViewById(R.id.editTextTPT_LHR);
        editTextTGL_LHR = (EditText) findViewById(R.id.editTextTGL_LHR);
        editTextNO_HP = (EditText) findViewById(R.id.editTextNO_HP);
        editTextEMAIL = (EditText) findViewById(R.id.editTextEMAIL);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonView.setOnClickListener(this);
        buttonClear.setOnClickListener(v -> {
            if (v == buttonClear) {
                editTextNAMA_LGKP.setText("");
                editTextTPT_LHR.setText("");
                editTextNIK.setText("");
                editTextNILAI_AKHIR.setText("");
                editTextPEMINATAN_JURUSAN.setText("");
                editTextASAL_SEKOLAH.setText("");
                editTextASAL_WILAYAH.setText("");
                editTextTHN_LULUS.setText("");
                editTextTGL_LHR.setText("");
                editTextNO_HP.setText("");
                editTextEMAIL.setText("");
            }
        });
        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                insert();
            }
        });
    }
    private void insert() {
        final String nama_lgkp = editTextNAMA_LGKP.getText().toString().trim();
        final String email = editTextEMAIL.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEMAIL.setError("Enter a valid email");
            editTextEMAIL.requestFocus();
            return;
        }
        if (!isValidemail(email)) {
            editTextEMAIL.setError("Email Harus Di Isi.");
        }
        if (!isValidnama_lengkap(nama_lgkp)) {
            editTextNAMA_LGKP.setError("Nama Lengkap Harus Di Isi.");
        }
        final String tpt_lhr = editTextTPT_LHR.getText().toString().trim();
        if (!isValidtempat_lahir(tpt_lhr)) {
            editTextTPT_LHR.setError("Tempat Lahir Harus Di Isi.");
        }
        final String tgl_lhr = editTextTGL_LHR.getText().toString().trim();
        final String no_hp = editTextNO_HP.getText().toString().trim();
        final String nik = editTextNIK.getText().toString().trim();
        final String thn_lulus = editTextTHN_LULUS.getText().toString().trim();
        final String peminatan_jurusan = editTextPEMINATAN_JURUSAN.getText().toString().trim();
        final String nilai_akhir = editTextNILAI_AKHIR.getText().toString().trim();
        final String asal_sekolah = editTextASAL_SEKOLAH.getText().toString().trim();
        final String asal_wilayah = editTextASAL_WILAYAH.getText().toString().trim();

        if (isValidemail(email) ) {
            pDialog.setMessage("Register Process...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AndroidTransaction.URL_INSERT_REGISTRASI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains(AndroidTransaction.KEY_EMAIL)) {
                                alert.showAlertDialog(MainActivity.this, " GAGAL", "Email sudah pernah terdaftar.", false);
                                hideDialog();
                            }
                            else
                            {
                                AndroidTransaction.email=editTextEMAIL.getText().toString().trim();
                                alert.showAlertDialog(MainActivity.this, "SUKSES ", "Periksa email untuk aktivasi", false);
                                hideDialog();
                            }
                        }},
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            alert.showAlertDialog(MainActivity.this, " GAGAL", "Silahkan coba lagi.", false);
                            hideDialog();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> hashMap = new HashMap<>();

                    hashMap.put(AndroidTransaction.KEY_NAMA_LGKP, nama_lgkp);
                    hashMap.put(AndroidTransaction.KEY_NIK, nik);
                    hashMap.put(AndroidTransaction.KEY_NILAI_AKHIR, nilai_akhir);
                    hashMap.put(AndroidTransaction.KEY_PEMINATAN_JURUSAN, peminatan_jurusan);
                    hashMap.put(AndroidTransaction.KEY_THN_LULUS, thn_lulus);
                    hashMap.put(AndroidTransaction.KEY_ASAL_SEKOLAH, asal_sekolah);
                    hashMap.put(AndroidTransaction.KEY_ASAL_WILAYAH, asal_wilayah);
                    hashMap.put(AndroidTransaction.KEY_TPT_LHR, tpt_lhr);
                    hashMap.put(AndroidTransaction.KEY_TGL_LHR, tgl_lhr);
                    hashMap.put(AndroidTransaction.KEY_NO_HP, no_hp);
                    hashMap.put(AndroidTransaction.KEY_EMAIL, email);
                    return hashMap;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }
    private boolean isValidemail(String email) {
        return email != null && email.length() > 1;
    }

    private boolean isValidnama_lengkap(String nama_lengkap) {
        return nama_lengkap != null && nama_lengkap.length() > 1;
    }

    private boolean isValidtempat_lahir(String tempat_lahir) {
        if (tempat_lahir != null && tempat_lahir.length() > 1) {
            return true;
        }
        return false;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void findViewsById() {
        editTextTGL_LHR = findViewById(R.id.editTextTGL_LHR);
        editTextTGL_LHR.setInputType(InputType.TYPE_NULL);
        editTextTGL_LHR.requestFocus();

    }
    private void setDateTimeField() {
        editTextTGL_LHR.setOnClickListener(this);
        editTextTGL_LHR.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextTGL_LHR.setText(dateFormatter.format(newDate.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View view) {
        if(view == editTextTGL_LHR) {
            fromDatePickerDialog.show();
        }
        else if(view == buttonView) {
            startActivity(new Intent(this,GetRegistrasi.class));
        }
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}
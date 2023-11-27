package ac.id.itech.registrasi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AlertDialogManager alert = new AlertDialogManager();
    Button buttonClear;
    private static final String TAG = "Register Application";
    private EditText editTextNAMA_LENGKAP;
    private EditText editTextTEMPAT_LAHIR;
    private EditText  editTextTANGGAL_LAHIR;
    private EditText  editTextNO_HP;
    private EditText editTextEMAIL;
    private EditText editTextNIK;
    private EditText editTextPEMINATAN_JURUSAN;
    private EditText editTextTAHUN_LULUS;
    private EditText editTextASAL_SEKOLAH;
    private EditText editTextASAL_WILAYAH;
    private EditText editTextNILAI_AKHIR;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;
    private Button buttonInsert;
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
        editTextNAMA_LENGKAP = (EditText) findViewById(R.id.editTextNAMA_LENGKAP);
        editTextNIK = (EditText) findViewById(R.id.editTextNIK);
        editTextPEMINATAN_JURUSAN = (EditText) findViewById(R.id.editTextPEMINATAN_JURUSAN);
        editTextASAL_SEKOLAH = (EditText) findViewById(R.id.editTextASAL_SEKOLAH);
        editTextASAL_WILAYAH = (EditText) findViewById(R.id.editTextASAL_WILAYAH);
        editTextTAHUN_LULUS = (EditText) findViewById(R.id.editTextTAHUN_LULUS);
        editTextTEMPAT_LAHIR = (EditText) findViewById(R.id.editTextTEMPAT_LAHIR);
        editTextTANGGAL_LAHIR = (EditText) findViewById(R.id.editTextTANGGAL_LAHIR);
        editTextNO_HP = (EditText) findViewById(R.id.editTextNO_HP);
        editTextEMAIL = (EditText) findViewById(R.id.editTextEMAIL);
        buttonClear  = (Button)findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(v -> {
            if (v==buttonClear) {
                editTextNAMA_LENGKAP.setText("");
                editTextTEMPAT_LAHIR.setText("");
                editTextNIK.setText("");
                editTextNILAI_AKHIR.setText("");
                editTextPEMINATAN_JURUSAN.setText("");
                editTextASAL_SEKOLAH.setText("");
                editTextASAL_WILAYAH.setText("");
                editTextTAHUN_LULUS.setText("");
                editTextTANGGAL_LAHIR.setText("");
                editTextNO_HP.setText("");
                editTextEMAIL.setText("");
            }
        });
        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(v -> insert_registrasi());
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
    private void insert_registrasi() {
        final String nama_lengkap = editTextNAMA_LENGKAP.getText().toString().trim();
        final String email = editTextEMAIL.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEMAIL.setError("Enter a valid email");
            editTextEMAIL.requestFocus();
            return;
        }
        if (!isValidemail(email)) {
            editTextEMAIL.setError("Email Harus Di Isi.");
        }
        if (!isValidnama_lengkap(nama_lengkap)) {
            editTextNAMA_LENGKAP.setError("Nama Lengkap Harus Di Isi.");
        }
        final String tempat_lahir = editTextTEMPAT_LAHIR.getText().toString().trim();
        if (!isValidtempat_lahir(tempat_lahir)) {
            editTextTEMPAT_LAHIR.setError("Tempat Lahir Harus Di Isi.");
        }
        final String tanggal_lahir = editTextTANGGAL_LAHIR.getText().toString().trim();
        final String no_hp = editTextNO_HP.getText().toString().trim();
        final String nik = editTextNIK.getText().toString().trim();
        final String thn_lulus = editTextTAHUN_LULUS.getText().toString().trim();
        final String peminatan_jurusan = editTextPEMINATAN_JURUSAN.getText().toString().trim();
        final String nilai_akhir = editTextNILAI_AKHIR.getText().toString().trim();
        final String asal_sekolah = editTextASAL_SEKOLAH.getText().toString().trim();
        final String asal_wilayah = editTextASAL_WILAYAH.getText().toString().trim();

        if(isValidemail(email) &&isValidnama_lengkap(nama_lengkap) && isValidtempat_lahir(tempat_lahir)) {
            showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, AndroidTransaction.URL_INSERT_REGISTRASI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        if (response.contains(AndroidTransaction.KEY_EMAIL)) {
                            AndroidTransaction.email=editTextEMAIL.getText().toString().trim();
                            Log.e("Sukses", editTextEMAIL.getText().toString().trim());
                            alert.showAlertDialog(MainActivity.this, "GAGAL ", "Email sudah terdaftar, coba email yang lain", false);
                        }
                        else
                        {
                            Log.e("Gagal", editTextEMAIL.getText().toString().trim());
                            alert.showAlertDialog(MainActivity.this, "SUKSES ", "Data berhasil disimpan", false);
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
                    Map<String, String> params = new HashMap<>();
                    Map<String, String> hashMap = new HashMap<>();
                    hashMap.put(AndroidTransaction.KEY_NAMA_LENGKAP, nama_lengkap);
                    hashMap.put(AndroidTransaction.KEY_NIK, nik);
                    hashMap.put(AndroidTransaction.KEY_NILAI_AKHIR, nilai_akhir);
                    hashMap.put(AndroidTransaction.KEY_PEMINATAN_JURUSAN, peminatan_jurusan);
                    hashMap.put(AndroidTransaction.KEY_TAHUN_LULUS, thn_lulus);
                    hashMap.put(AndroidTransaction.KEY_ASAL_SEKOLAH, asal_sekolah);
                    hashMap.put(AndroidTransaction.KEY_ASAL_WILAYAH, asal_wilayah);
                    hashMap.put(AndroidTransaction.KEY_TEMPAT_LAHIR, tempat_lahir);
                    hashMap.put(AndroidTransaction.KEY_TANGGAL_LAHIR, tanggal_lahir);
                    hashMap.put(AndroidTransaction.KEY_NO_HP, no_hp);
                    hashMap.put(AndroidTransaction.KEY_EMAIL, email);
                    return hashMap;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }
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
        editTextTANGGAL_LAHIR = findViewById(R.id.editTextTANGGAL_LAHIR);
        editTextTANGGAL_LAHIR.setInputType(InputType.TYPE_NULL);
        editTextTANGGAL_LAHIR.requestFocus();

    }
    private void setDateTimeField() {
        editTextTANGGAL_LAHIR.setOnClickListener(this);
        editTextTANGGAL_LAHIR.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextTANGGAL_LAHIR.setText(dateFormatter.format(newDate.getTime()));
            }
        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View view) {
        if(view == editTextTANGGAL_LAHIR) {
            fromDatePickerDialog.show();
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
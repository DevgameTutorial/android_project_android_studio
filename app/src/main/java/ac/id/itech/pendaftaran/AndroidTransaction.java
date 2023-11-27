package ac.id.itech.pendaftaran;
public class AndroidTransaction {
//    https://ec2-44-218-142-59.compute-1.amazonaws.com
//    https://44.218.142.59
//    10.0.2.2:3001


    public static final String URL_INSERT_REGISTRASI = "http://10.0.2.2:3001/api/insert_registrasi";
    public static final String URL_GET_ALL_REGISTRASI = "http://10.0.2.2:3001/api/get_registrasi";
    public static final String URL_GET_REGISTRASI_BY_PARAM = "http://10.0.2.2:3001/api/get_registrasi_by_id/";
    public static final String URL_UPDATE_REGISTRASI = "http://10.0.2.2:3001/api/update_registrasi_by_id/";
    public static final String URL_DELETE_REGISTRASI = "http://10.0.2.2:3001/api/delete_registrasi_by_id/";
    public static final String URL_PATH_ASSETS = "http://10.0.2.2:3001/";
    // Tags Format JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_NAMA_LGKP = "nama_lgkp";
    public static final String TAG_TPT_LHR = "tmp_lhr";
    public static final String TAG_TGL_LHR = "tgl_lhr";
    public static final String TAG_NO_HP = "no_hp";
    public static final String TAG_NIK = "nik";
    public static final String TAG_PEMINATAN_JURUSAN = "peminatan_jurusan";
    public static final String TAG_THN_LULUS = "thn_lulus";
    public static final String TAG_ASAL_SEKOLAH = "asal_sekolah";
    public static final String TAG_ASAL_WILAYAH = "asal_wilayah";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_FOTO= "foto";
    public static final String TAG_FLAG = "flag";
    public static final String TAG_ID = "id";

    public static final String PARAM_ID = "id";
    public static final String KEY_NAMA_LGKP = "nama_lgkp";
    public static final String KEY_TPT_LHR = "tmp_lhr";
    public static final String KEY_TGL_LHR = "tgl_lhr";
    public static final String KEY_NO_HP = "no_hp";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NIK = "nik";
    public static final String KEY_PEMINATAN_JURUSAN = "peminatan_jurusan";
    public static final String KEY_THN_LULUS = "thn_lulus";
    public static final String KEY_NILAI_AKHIR = "nilai_akhir";
    public static final String KEY_ASAL_SEKOLAH = "asal_sekolah";
    public static final String KEY_ASAL_WILAYAH = "asal_wilayah";
    public static final String KEY_FOTO = "foto";
    public static final String KEY_FLAG = "flag";

    public static String id;
    public static String email;
    public static String nik;
    public static String nama_lgkp;
    public static String asal_wilayah;
    public static String asal_sekolah;
    public static String nilai_akhir;
    public static String thn_lulus;
    public static String peminatan_jurusan;
    public static String no_hp;
    public static String tgl_lhr;
    public static String tpt_lhr;
    public static String flag;
    public static String foto;
    public static final String EMP_ID = "id";

}
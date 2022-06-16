package profileuser;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.thanguit.toastperfect.ToastPerfect;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroduceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroduceFragment extends DialogFragment {
    UserManager userManager;
    private TextView danhsach, mota, thamgia;
    ImageButton imagebtn;
    RequestQueue queue;
    String Anh = "";
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntroduceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroduceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroduceFragment newInstance(String param1, String param2) {
        IntroduceFragment fragment = new IntroduceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduce,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv);
        //Ánh xạ
        danhsach = view.findViewById(R.id.danhsach);
        mota = view.findViewById(R.id.mota);
        thamgia = view.findViewById(R.id.thamgia);
        queue = Volley.newRequestQueue(getActivity());

        listitem_adapter adapterlist = new listitem_adapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //set adapter cho rcv
        adapterlist.setData(getListBook());
        recyclerView.setAdapter(adapterlist);

        userManager = new UserManager(getActivity());

        HashMap<String,String> user = userManager.userDatail();
        //Lấy dl tk
        final String tK = user.get(userManager.TAIKHOAN);
        danhsach.setText("Danh sách đọc của: "+tK);
        //Get dữ liệu mota
        String url0 = "http://ginnami201.tk/readmota.php";
        String url1 = "http://ginnami201.tk/date.php";
//        String url0 = "http://192.168.43.7/Android/readmota.php";
//        String url1 = "http://192.168.43.7/Android/date.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().isEmpty())
                        {
                            mota.setText("Nhấp vào đây để thêm một mô tả về bản thân...");
                        }
                        else{
                            mota.setText(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tk",tK);
                return params;
            }
        };
        queue.add(stringRequest);

//        getdate ngày tham gia
        String Date = thamgia.getText().toString().trim();
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            thamgia.setText("Đã tham gia "+response);
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tk", tK);
                return params;
            }

        };
        queue.add(stringRequest1);

        //Text mota
        mota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(Gravity.CENTER);
            }

            private void opendialog(int gravity) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                TextInputEditText textgioithieu = dialog.findViewById(R.id.textgioithieu);
                Button huy = dialog.findViewById(R.id.huy);
                Button ok = dialog.findViewById(R.id.ok);

                //Hiển thị dữ liệu từ text view mota lên edittext của dialog
                textgioithieu.setText(mota.getText().toString().trim());

                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //Update mota
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = "http://ginnami201.tk/updatemota.php";
//                            String url = "http://192.168.43.7/Android/updatemota.php";
                            String Mota = textgioithieu.getText().toString().trim();
                            if(Mota.isEmpty()){
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                mota.setText("Nhấp vào đây để thêm một mô tả về bản thân...");
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        dialog.dismiss();
                                                    }
                                                }, 500);   //0,5s
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("mota",Mota);
                                        params.put("tk",tK);
                                        return params;
                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(getActivity());
                                queue.add(stringRequest);
                            } else {
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                mota.setText(Mota);
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        dialog.dismiss();
                                                    }
                                                }, 1000);   //1 seconds
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("mota",Mota);
                                        params.put("tk",tK);
                                        return params;
                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(getActivity());
                                queue.add(stringRequest);
                                //ToastPerfect.makeText(getActivity(), ToastPerfect.SUCCESS, "Thêm mô tả thành công!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                            }
                        }
                    });

                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                //set chiều rộng, cao cho dialog
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                //set background
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Xác định vị trí ở giữa của dialog
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = gravity;
                window.setAttributes(windowAttributes);
                if(Gravity.CENTER == gravity)
                {
                    dialog.setCancelable(true);
                }
                dialog.show();
            }
        });
        return view;
    }

    private List<Book_profile> getListBook() {
        List<Book_profile> list = new ArrayList<>();
        list.add(new Book_profile(R.drawable.lovebook, R.drawable.ic_baseline_remove_red_eye_24,R.drawable.ic_baseline_star_24,R.drawable.ic_baseline_format_list_bulleted_24,"Love Book","13k","1,2k","120","Sách Hay"));
        list.add(new Book_profile(R.drawable.lovebook, R.drawable.ic_baseline_remove_red_eye_24,R.drawable.ic_baseline_star_24,R.drawable.ic_baseline_format_list_bulleted_24,"Love Book","13k","1,2k","120","Sách Hay"));
        list.add(new Book_profile(R.drawable.lovebook, R.drawable.ic_baseline_remove_red_eye_24,R.drawable.ic_baseline_star_24,R.drawable.ic_baseline_format_list_bulleted_24,"Love Book","13k","1,2k","120","Sách Hay"));
        list.add(new Book_profile(R.drawable.lovebook, R.drawable.ic_baseline_remove_red_eye_24,R.drawable.ic_baseline_star_24,R.drawable.ic_baseline_format_list_bulleted_24,"Love Book","13k","1,2k","120","Sách Hay"));
        return list;
    }
}
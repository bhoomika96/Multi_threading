package info.bhoomika.volleymutithread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    public static final String REGISTER_URL1 = "http://localhost/volleysqljson/comments.php";
    public static final String REGISTER_URL2 = "http://localhost/volleysqljson/photos.php";
    public static final String REGISTER_URL3 = "http://localhost/volleysqljson/todos.php";
    public static final String REGISTER_URL4 = "http://localhost/volleysqljson/posts.php";

    public static final String KEY_POSTID = "postid";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BODY = "body";

    public static final String KEY_ALBUMID = "albumid";
    public static final String KEY_IDP = "idp";
    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";
    public static final String KEY_THUMBNAIL = "thumb";

    public static final String KEY_USERID = "userid";
    public static final String KEY_IDT = "idt";
    public static final String KEY_TITLET = "titlet";
    public static final String KEY_COM = "com";

    public static final String KEY_USER = "user";
    public static final String KEY_IDPOST = "idpost";
    public static final String KEY_TITLEP = "titlep";
    public static final String KEY_BODYP = "bodyp";

    private Handler handler;
    TextView tvStatus;
    Button b1,b2,b3,b4;
    TextView tv11,tv12,tv13,tv14,tv21,tv22,tv23,tv24,tv31,tv32,tv33,tv34,tv41,tv42,tv43,tv44;
    int curCount = 0,num=0;
    //ProgressBar progressBar;
    private String[] url = {"https://jsonplaceholder.typicode.com/comments","https://jsonplaceholder.typicode.com/photos","https://jsonplaceholder.typicode.com/todos","https://jsonplaceholder.typicode.com/posts"};
    //private ProgressDialog pDialog;
    private static String TAG = MainActivity.class.getSimpleName();
    private String jsonResponse;

    float totalCount = 100F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.textView);

        tv11 = (TextView) findViewById(R.id.tv11);
        tv12 = (TextView) findViewById(R.id.tv12);
        tv13 = (TextView) findViewById(R.id.tv13);
        tv14 = (TextView) findViewById(R.id.tv14);

        tv21 = (TextView) findViewById(R.id.tv21);
        tv22 = (TextView) findViewById(R.id.tv22);
        tv23 = (TextView) findViewById(R.id.tv23);
        tv24 = (TextView) findViewById(R.id.tv24);

        tv31 = (TextView) findViewById(R.id.tv31);
        tv32 = (TextView) findViewById(R.id.tv32);
        tv33 = (TextView) findViewById(R.id.tv33);
        tv34 = (TextView) findViewById(R.id.tv34);

        tv41 = (TextView) findViewById(R.id.tv41);
        tv42 = (TextView) findViewById(R.id.tv42);
        tv43 = (TextView) findViewById(R.id.tv43);
        tv44 = (TextView) findViewById(R.id.tv44);

        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl=url[0];
                ExecutorService pool = Executors.newSingleThreadExecutor();

                Runnable task = new Runnable() {
                    public void run() {
                        //System.out.println(Thread.currentThread().getName());

                        tv11.setText(callstamp());
                        makeJsonArrayRequest1();

                    }
                };
                pool.execute(task);
                tv12.setText(callstamp());
                pool.shutdown();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl=url[1];
                ExecutorService pool = Executors.newSingleThreadExecutor();

                Runnable task = new Runnable() {
                    public void run() {
                        //System.out.println(Thread.currentThread().getName());

                        tv21.setText(callstamp());
                    }
                };
                pool.execute(task);
                tv22.setText(callstamp());
                pool.shutdown();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl=url[0];
                ExecutorService pool = Executors.newSingleThreadExecutor();

                Runnable task = new Runnable() {
                    public void run() {
                        //System.out.println(Thread.currentThread().getName());

                        tv31.setText(callstamp());
                    }
                };
                pool.execute(task);
                tv32.setText(callstamp());
                pool.shutdown();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl=url[1];
                ExecutorService pool = Executors.newSingleThreadExecutor();

                Runnable task = new Runnable() {
                    public void run() {
                        //System.out.println(Thread.currentThread().getName());

                        tv41.setText(callstamp());
                    }
                };
                pool.execute(task);
                tv42.setText(callstamp());
                pool.shutdown();
            }
        });

        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 4,
                NUMBER_OF_CORES * 4,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );

        for (int i = 0; i < totalCount; i++) {

            String imageUrl=url[i%4];
            if(i<4)
            {
                if(num%4==0)
                    tv11.setText(callstamp());
                else if(num%4==1)
                    tv21.setText(callstamp());
                else if(num%4==2)
                    tv31.setText(callstamp());
                else
                    tv41.setText(callstamp());

            }
            executor.execute(new LongThread(i, imageUrl, new Handler(this)));
            num++;
        }

        tvStatus.setText("Starting Download...");
    }

    @Override
    public boolean handleMessage(Message msg) {
        curCount++;
        float per = (curCount / totalCount) * 100;
      /*  progressBar.setProgress((int) per);
       */ if (per < 100)
            tvStatus.setText("Downloaded [" + curCount + "/" + (int)totalCount + "]");
        else
            tvStatus.setText("All images downloaded.");

        return true;
    }

    public String callstamp()
    {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }
    private void makeJsonArrayRequest1() {

        JsonArrayRequest req = new JsonArrayRequest(url[0],
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                String postid = person.getString("postId");
                                String id = person.getString("id");
                                String name = person.getString("name");
                                String email = person.getString("email");
                                String body = person.getString("body");

                                registercomments(postid,id,name,email,body);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
    private void registercomments(String a,String b,String c,String d,String e)
    {
        final String postid=a;
        final String id=b;
        final String name=c;
        final String email=d;
        final String body=e;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_POSTID,postid);
                params.put(KEY_ID,id);
                params.put(KEY_NAME,name);
                params.put(KEY_EMAIL,email);
                params.put(KEY_BODY,body);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}


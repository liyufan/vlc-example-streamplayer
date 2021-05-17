package com.pedro.vlctestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryActivity extends AppCompatActivity {

    public static final String TAG = "CategoryActivity";
    //    HashMap<String, Integer> map = new HashMap<>() {
//        {
//            put("person", 0);
//
//        }
//    }
    HashMap<String, Integer> mHashMap = new HashMap<>();
    private int[] categories = new int[80];
    private ArrayList<DetObj> mList = new ArrayList<DetObj>();
    private String data[] = {"person", "bicycle", "car", "motorcycle", "airplane", "bus",
            "train", "truck", "boat", "traffic light", "fire hydrant",
            "stop sign", "parking meter", "bench", "bird", "cat", "dog",
            "horse", "sheep", "cow", "elephant", "bear", "zebra", "giraffe",
            "backpack", "umbrella", "handbag", "tie", "suitcase", "frisbee",
            "skis", "snowboard", "sports ball", "kite", "baseball bat",
            "baseball glove", "skateboard", "surfboard", "tennis racket",
            "bottle", "wine glass", "cup", "fork", "knife", "spoon", "bowl",
            "banana", "apple", "sandwich", "orange", "broccoli", "carrot",
            "hot dog", "pizza", "donut", "cake", "chair", "couch",
            "potted plant", "bed", "dining table", "toilet", "tv", "laptop",
            "mouse", "remote", "keyboard", "cell phone", "microwave",
            "oven", "toaster", "sink", "refrigerator", "book", "clock",
            "vase", "scissors", "teddy bear", "hair drier", "toothbrush"};

    private String mrtsp_src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoiy);
        ActivityCollector.addActivity(this);
        initObjCls();
//        if (savedInstanceState!=null) {
//            categories = savedInstanceState.getIntArray("categories");
//           //Log.d(TAG, "receive data from destructed activity successfully");
//        }
        Button transmitBu = (Button) findViewById(R.id.TransmitButton);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DetAdaptor adaptor = new DetAdaptor(mList);
        recyclerView.setAdapter(adaptor);
        Intent intent = getIntent();
        mrtsp_src = intent.getStringExtra("rtsp_src");
        transmitBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                intent.putExtra("rtsp_src", mrtsp_src);
                intent.putExtra("category", mList);
               //Log.d(TAG, "send all information to rtsp activity!");
                startActivity(intent);
            }
        });


       //Log.d(TAG, "receive rtsp sources " + mrtsp_src);
        Toast.makeText(CategoryActivity.this, "Please Select the Categories You Would Like to Detect", Toast.LENGTH_SHORT).show();
    }

    private void initObjCls() {
        DetObj person = new DetObj("person", R.mipmap.person_pic);
        mList.add(person);
        DetObj car = new DetObj("car", R.mipmap.car_pic);
        mList.add(car);
        DetObj bench = new DetObj("bench", R.mipmap.bench_pic);
        mList.add(bench);
        DetObj bird = new DetObj("bird", R.mipmap.bird_pic);
        mList.add(bird);
        DetObj cat = new DetObj("cat", R.mipmap.cat_pic);
        mList.add(cat);
        DetObj dog = new DetObj("dog", R.mipmap.dog_pic);
        mList.add(dog);
        DetObj backpack = new DetObj("backpack", R.mipmap.backpack_pic);
        mList.add(backpack);
        DetObj umbrella = new DetObj("unbrella", R.mipmap.umbrella_pic);
        mList.add(umbrella);
        DetObj handbag = new DetObj("handbag", R.mipmap.handbag_pic);
        mList.add(handbag);
        DetObj suitcase = new DetObj("suitcase", R.mipmap.suitcase_pic);
        mList.add(suitcase);
        DetObj bottle = new DetObj("bottle", R.mipmap.bottle_pic);
        mList.add(bottle);
        DetObj cup = new DetObj("cup", R.mipmap.cup_pic);
        mList.add(cup);
        DetObj fork = new DetObj("fork", R.mipmap.fork_pic);
        mList.add(fork);
        DetObj knife = new DetObj("knife", R.mipmap.knife_pic);
        mList.add(knife);
        DetObj spoon = new DetObj("spoon", R.mipmap.spoon_pic);
        mList.add(spoon);
        DetObj bowl = new DetObj("bowl", R.mipmap.bowl_pic);
        mList.add(bowl);
        DetObj banana = new DetObj("banana", R.mipmap.banana_pic);
        mList.add(banana);
        DetObj apple = new DetObj("apple", R.mipmap.apple_icon);
        mList.add(apple);
        DetObj orange = new DetObj("orange", R.mipmap.orange_pic);
        mList.add(orange);
        DetObj chair = new DetObj("chair", R.mipmap.chair_pic);
        mList.add(chair);
        DetObj tv = new DetObj("tv", R.mipmap.tv_pic);
        mList.add(tv);
        DetObj laptop = new DetObj("laptop", R.mipmap.laptop_pic);
        mList.add(laptop);
        DetObj mouse = new DetObj("mouse", R.mipmap.mouse_pic);
        mList.add(mouse);
        DetObj keyboard = new DetObj("keyboard", R.mipmap.keyboard_pic);
        mList.add(keyboard);
        DetObj cellphone = new DetObj("cellphone", R.mipmap.cellphone_pic);
        mList.add(cellphone);
        DetObj book = new DetObj("book", R.mipmap.book_pic);
        mList.add(book);
        DetObj clock = new DetObj("clock", R.mipmap.clock_pic);
        mList.add(clock);
        DetObj vase = new DetObj("vase", R.mipmap.vase_pic);
        mList.add(vase);
        DetObj scissors = new DetObj("scissors", R.mipmap.scissors_pic);
        mList.add(scissors);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("categories", categories);
       //Log.d(TAG, "put CategoryActivity data in bundle");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
package com.pedro.vlctestapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BboxView extends SurfaceView implements SurfaceHolder.Callback, Runnable, Category {


    private static final String TAG = "BBOX_VIEW";
    private static final int DRAW_INTERVAL = 30;
    public static final int DISPLAY_TIME_ON_SCREEN = 0;
    public static final int RECEIVE_NOTIFICATION = 1;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    int vlcWidth;
    private boolean peopleDetected;
    private int display_style;
    private int mCountTime = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /*BboxView(Context context) {
        super(context);
        mHandler.post(mCounter);
    }*/

    private Runnable mCounter = new Runnable() {
        @Override
        public void run() {
            mCountTime++;
            //tvTime.setText("当前计数值:"+mCount);
            mHandler.postDelayed(this,1000);
        }
    };

//    class BoundingBox {
//        public int x1;
//        public int y1;
//        public int x2;
//        public int y2;
//        public int cls;
//        public BoundingBox(int x1, int y1, int x2, int y2, int cls) {
//            this.x1 = x1;
//            this.y1 = y1;
//            this.x2 = x2;
//            this.y2 = y2;
//            this.cls = cls;
//        }
//    }

//    private ArrayList<BoundingBox> mBBoxList;

//    private ArrayList<OneFrameDetection.BoundingBox> mTestBBoxList;
    int vlcHeight;
    /*private String[] mCategoryTab = {"person", "bicycle", "car", "motorcycle", "airplane", "bus",
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
            "vase", "scissors", "teddy bear", "hair drier", "toothbrush"};*/
    /*private HashMap<String, Boolean> mShowCategory = new HashMap<String, Boolean>() {
        {

            put("person", true);                            //detect
            put("bicycle", true);                   //detect
            put("car", true);                       //detect
            put("motorcycle", true);                //detect
            put("airplane", false);
            put("bus", false);
            put("train", false);
            put("truck", false);
            put("boat", false);
            put("traffic light", false);
            put("fire hydrant", true);              //detect
            put("stop sign", false);
            put("parking meter", false);
            put("bench", true);                     //detect
            put("bird", true);                      //detect
            put("cat", true);                       //detect
            put("dog", true);                       //detect
            put("horse", false);
            put("sheep", false);
            put("cow", false);
            put("elephant", false);
            put("bear", false);
            put("zebra", false);
            put("giraffe", false);
            put("backpack", true);                  //detect
            put("umbrella", true);                  //detect
            put("handbag", true);                   //detect
            put("tie", false);
            put("suitcase", true);                  //detect
            put("frisbee", false);
            put("skis", false);
            put("snowboard", false);
            put("sports ball", false);
            put("kite", false);
            put("baseball bat", false);
            put("baseball glove", false);
            put("skateboard", false);
            put("surfboard", false);
            put("tennis racket", false);
            put("bottle", true);                    //detect
            put("wine glass", false);
            put("cup", true);                       //detect
            put("fork", true);                      //detect
            put("knife", true);                    //detect
            put("spoon", true);                     //detect
            put("bowl", true);                      //detect
            put("banana", true);                    //detect
            put("apple", true);                     //detect
            put("sandwich", false);
            put("orange", true);                    //detect
            put("broccoli", false);
            put("carrot", false);
            put("hot dog", false);
            put("pizza", false);
            put("donut", false);
            put("cake", false);
            put("chair", true);                     //detect
            put("couch", false);
            put("potted plant", false);
            put("bed", false);
            put("dining table", true);
            put("toilet", false);
            put("tv", true);                        //detect
            put("laptop", true);                    //detect
            put("mouse", true);                     //detect
            put("remote", false);
            put("keyboard", true);                  //detect
            put("cell phone", true);                //detect
            put("microwave", false);
            put("oven", false);
            put("toaster", false);
            put("sink", false);
            put("refrigerator", false);
            put("book", true);                      //detect
            put("clock", true);                     //detect
            put("vase", true);                      //detect
            put("scissors", true);                  //detect
            put("teddy bear", false);
            put("hair drier", false);
            put("toothbrush", false);
        }
    };*/
    private Lock mlock;

//    private MyService.NotifyBinder mNotifyBinder;
    private Context mContext;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;

//    private boolean mBindService;
    private Gson mgson;

//    private Intent mServiceIntent;
    private OneFrameDetection mOneFrameDetection;
    private Paint mpaint;
    private boolean mIsDrawing;
    private boolean mDetectionValid = false;
    private JWebSocketClient mWSClient;

//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//           //Log.e(TAG, "Successfully build connection with MainActivity");
//            mNotifyBinder = (MyService.NotifyBinder)iBinder;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//           //Log.e(TAG, "Disconnected with MainActivity");
//        }
//    };


    public BboxView(Context context) {
        super(context);
        this.mContext = context;
        initView();
        mHandler.post(mCounter);
    }

    public BboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
        mHandler.post(mCounter);
    }

    public BboxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        mHandler.post(mCounter);
    }


    private void initView() {
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderMediaOverlay(true);
        mpaint = new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.RED);
        mpaint.setStrokeWidth(5);
        mpaint.setTextSize(50);
        mgson = new Gson();
        mlock = new ReentrantLock();
        DisplayMetrics metrics = new DisplayMetrics();
        metrics = getResources().getDisplayMetrics();


        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;
        setWebSocketUri("ws://192.168.1.106:3000");
//        setWebSocketUri("ws://202.120.37.223:3000");
//        setWebSocketUri("wss://echo.websocket.org");

//        mBBoxList = new ArrayList<>();
//        mTestBBoxList = new ArrayList<>();
//        putBoundingBox();
    }

//    public void bindWithService() {
//        mBindService = true;
//    }
//
//    public void unbindWithService() {
//        mBindService = false;
//    }
//
//    public void setBinder(MyService.NotifyBinder binder) {
//        mNotifyBinder = binder;
//    }

    public void setWebSocketUri(String s) {
        URI uri = URI.create(s);
        mWSClient = new JWebSocketClient(uri) {
            String requestJson = mgson.toJson(new WebSocketRequest("request", "this is a bbox request message"), WebSocketRequest.class);

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
               //Log.e(TAG, "BBox View is successfully build connect as client");
//                WebSocketRequest message = new WebSocketRequest("request", "this is a bbox request message");
                send(requestJson);
               //Log.e(TAG, "Successfully send out the request");
            }

            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                boolean capture = false;
                try {
                    capture = mlock.tryLock(2, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
//                   //Log.e(TAG, "screen pixel is " + SCREEN_WIDTH + " " + SCREEN_HEIGHT);
//                   //Log.e(TAG, "The message we received is " + message);
                    if (capture) {
                        mOneFrameDetection = mgson.fromJson(message, OneFrameDetection.class);
                        mDetectionValid = true;
//                        if (mBindService) {
//                            mNotifyBinder.justPrint();
////                            mNotifyBinder.setTransmit(mOneFrameDetection);
//                        }

//                       //Log.e(TAG, "Message Received Below: \n" + mOneFrameDetection.toString());
                        mlock.unlock();
                    }
//                   //Log.e(TAG, "End of Drawing BBow");
                    send(requestJson);
//                   //Log.e(TAG, "Message send out through on message event");
                }
//               //Log.e(TAG, "Message Received Below: \n" + mOneFrameDetection.toString());

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                super.onClose(code, reason, remote);
               //Log.e(TAG, "The WebSocket connection is over, disconnect code is: " + code +
                        //"\n The last message we received is: " + reason);

            }
        };
    }

//    public void putBoundingBox() {
//        BoundingBox A = new BoundingBox(100, 200, 300, 400, 0);
//        BoundingBox B = new BoundingBox(167, 320, 555, 256, 0);
//        BoundingBox C = new BoundingBox(10, 60, 520, 469, 0);
//        BoundingBox D = new BoundingBox(55, 182, 467, 802, 0);
//        mBBoxList.add(A);
//        mBBoxList.add(B);
//        mBBoxList.add(C);
//        mBBoxList.add(D);
//
//    }

    public void setDetect(List<DetObj> bbox) {
        for (DetObj elem : bbox) {
            String objName = elem.getName();
            mShowCategory.remove(objName);
            mShowCategory.put(objName, elem.getchosen());
        }
    }


//    private void shiftBoundingBox() {
////       //Log.d("shift");
//        for (BoundingBox elem: mBBoxList) {
//           //Log.d("BBOX_VIEW", "x1: "+elem.x1 + " x2: "+elem.x2);
//            elem.x1 += 10;
//            if (elem.x1 > SCREEN_WIDTH) elem.x1 = 0;
//            elem.x2 += 10;
//            if (elem.x2 > SCREEN_WIDTH) elem.x2 = 0;
//
//        }
//    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        Thread ws_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mWSClient.connect();
            }
        });
        ws_thread.start();

        while (mIsDrawing) {
            if (mDetectionValid) {
                mlock.lock();
                try {
                    doDraw();
                } finally {
                    mlock.unlock();
                }
            }
        }
    }

    public void setVLCAttr(int height, int width) {
        vlcHeight = height;
        vlcWidth = width;
    }

    private boolean people_state_changed;
    private boolean peopleDetectedLastFrame;
    public void doDraw() {
        try {
            mCanvas = mHolder.lockCanvas();

            mCanvas.drawColor(Color.TRANSPARENT);
            mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mCanvas.drawPaint(mpaint);
            mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//            int height = mCanvas.getHeight();
            int height = vlcHeight;
            double ratio = height / 720.0;
//            int width = mCanvas.getWidth();
            int width = (int) (1280 * ratio);
            int widthshift = (SCREEN_WIDTH - width) / 2;
            int heightshift = (SCREEN_HEIGHT - height) / 2;
            boolean existPeople = false;
            int elemIndex;
            peopleDetected = false;
            // TODO: start a timer
            for (OneFrameDetection.BoundingBox elem : mOneFrameDetection.bboxes) {
                elemIndex = elem.cls;
                if (elemIndex == 0 && !peopleDetected) {
                    peopleDetected = true;
                }
                String cls = mCategoryTab[elemIndex];
                //Log.i(TAG, "class is: " + cls);
                if (mShowCategory.get(cls)) {
                    int nx1 = (int) (elem.x1 * width) + widthshift;
                    int nx2 = (int) (elem.x2 * width) + widthshift;
                    int ny1 = (int) (elem.y1 * height); //+ heightshift;
                    int ny2 = (int) (elem.y2 * height);// + heightshift;

                    mCanvas.drawRect(new RectF(nx1, ny1, nx2, ny2), mpaint);
                    mCanvas.drawText(cls, nx1, ny1 + 60, mpaint);
                }
            }
            people_state_changed = (peopleDetectedLastFrame != peopleDetected);
            peopleDetectedLastFrame = peopleDetected;
            if (people_state_changed) {
                // TODO: reset timer
                mCountTime = 0;
            }
            Log.d(TAG, "state changed var is " + people_state_changed);
            //mHandler.post(mCounter);
            /*switch (display_style) {
                case DISPLAY_TIME_ON_SCREEN:


            }*/
            if (peopleDetected) {
                mCanvas.drawText("LYF is now at work for " + mCountTime, width - 60, 60, mpaint);
            } else {
                mCanvas.drawText("LYF is absent now " + mCountTime, width - 60, 60, mpaint);
            }
            //Log.i(TAG, "People found? " + peopleDetected);
//            mCanvas.drawRect(new RectF(500, 650, 750, 820), mpaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
            mDetectionValid = false;
        }
    }

    public void doDraw(ArrayList<OneFrameDetection.BoundingBox> bbox_list) {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT);
        for (OneFrameDetection.BoundingBox elem : bbox_list) {
            canvas.drawRect(new RectF(elem.x1, elem.y1, elem.x2, elem.y2), mpaint);
        }
//        canvas.drawRect(new RectF(500, 650, 750, 820), mpaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    public void clearDraw() {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.BLUE);
        mHolder.unlockCanvasAndPost(canvas);
    }

    public boolean getPeopleDetected() {
        return peopleDetected;
    }

    public void setDisplayStyle(int i) {
        display_style = i;
    }

//    private class DrawThread extends Thread {
//        public boolean isRunning = false;
//
//        public DrawThread() {
//            isRunning = true;
//            mpaint = new Paint();
//        }
//
//        public void stopThread() {
//            isRunning = false;
//
//            boolean workIsNotFinish = true;
//
//            while(workIsNotFinish) {
//                try {
//                    this.join();
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }
//                workIsNotFinish = false;
//            }
//        }
//
//        public void run() {
//            long deltaTime = 0;
//            long tickTime = 0;
//            tickTime = System.currentTimeMillis();
//            while(isRunning) {
//                Canvas canvas = null;
//                try{
//                    synchronized (mHolder) {
//                        canvas = mHolder.lockCanvas();
//
//                        canvas.drawRect(new RectF(40, 50, 140, 150), mpaint);
//
//                    }
//                } catch(Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (mHolder != null) {
//                        mHolder.unlockCanvasAndPost(canvas);
//                    }
//                }
//                deltaTime = System.currentTimeMillis() - tickTime;
//                if (deltaTime < DRAW_INTERVAL) {
//                    try {
//                        Thread.sleep(DRAW_INTERVAL - deltaTime);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

//                tickTime = System.currentTimeMillis();
//            }
//        }
//    }


}

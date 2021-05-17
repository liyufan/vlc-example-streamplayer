package com.pedro.vlctestapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

public class MyService extends Service implements Category{
    private static String TAG = "MyService";
    /*private String mCategoryTab[] = {"person", "bicycle", "car", "motorcycle", "airplane", "bus",
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
    private boolean mcategoryReceived = false;

    private boolean statuschange = false;

    private String mnotifyChange;
    private OneFrameDetection mOneFrameDetection;

    private JWebSocketClient mWSClient;

    private HashMap<String, Integer> mCurState;

    private HashMap<String, Integer> mReceiveState;

    private Gson mgson;

    private boolean mDetectionValid = false;

//    private Lock mlock;
    private NotifyBinder mBinder = new NotifyBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
       //Log.e(TAG, "Background Service is successfully activate!\n");
        initService();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mWSClient.connect();
            }
        });
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle("The current status in scenario").build();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       //Log.e(TAG, "Service Successfully destroied");
    }

    private void initService() {
        mgson = new Gson();
        setWebSocketUri("ws://202.120.37.223:3000");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    public void setWebSocketUri(String s) {
        URI uri = URI.create(s);
        mWSClient = new JWebSocketClient(uri) {
            String requestJson = mgson.toJson(new WebSocketRequest("request", "this is a bbox request message"), WebSocketRequest.class);

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
               //Log.e(TAG, "BBox View is successfully build connect as client");
                send(requestJson);
               //Log.e(TAG, "Successfully send out the request");
            }

            @Override
            public void onMessage(String message) {
                super.onMessage(message);
               //Log.e(TAG, "The message we received is " + message);
                mOneFrameDetection = mgson.fromJson(message, OneFrameDetection.class);
                mDetectionValid = true;
                String notificationContent = calcScenario();
                getNotificationManager().notify(1, getNotification("Current Scenario", notificationContent));
                if (statuschange) {
                    getNotificationManager().notify(2, getNotification("Scenario Change", mnotifyChange));
                }
               //Log.e(TAG, "Message Received Below: \n" + mOneFrameDetection.toString());
                send(requestJson);
               //Log.e(TAG, "Message send out through on message event");

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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, String content) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentIntent(pi);
        builder.setContentText(content);

        return builder.build();
    }

    private String calcScenario() {
        if (!mcategoryReceived) {
            return "Haven't receive your attention now, still loading.....";
        }
        String result = "There are ";
        if (mDetectionValid) {
            for (OneFrameDetection.BoundingBox elem : mOneFrameDetection.bboxes) {
                String cls = mCategoryTab[elem.cls];
                if (mShowCategory.get(cls)) {
                    Integer num = mReceiveState.get(cls);
                    if (num == null) {
                        mReceiveState.put(cls, 1);
                    } else {
                        mReceiveState.put(cls, num + 1);
                    }
                }
            }
            for (String key : mReceiveState.keySet()) {
                Integer num = mReceiveState.get(key);
                Integer num1;
                result += num + " " + key + " , ";
                if (!(num1 = mCurState.get(key)).equals(num)) {
                    if (!statuschange) {
                        statuschange = true;
                        mnotifyChange = "";
                        if (num > num1) {
                            mnotifyChange += (num - num1) + " " + key + " get into scenario, ";
                        } else {
                            mnotifyChange += (num1 - num) + " " + key + " disappear from scenario, ";
                        }
                    } else {
                        if (num > num1) {
                            mnotifyChange += (num - num1) + " " + key + " get into scenario, ";
                        } else {
                            mnotifyChange += (num1 - num) + " " + key + " disappear from scenario, ";
                        }
                    }
                }
            }
        } else {
            result = "Detection result is invalid now, loading!";
        }
        return result;
    }

    class NotifyBinder extends Binder {

        public void setDetect(List<DetObj> bbox) {
            for (DetObj elem : bbox) {
                String objName = elem.getName();
                mShowCategory.remove(objName);
                mShowCategory.put(objName, elem.getchosen());
            }
        }

        public void justPrint() {
           //Log.e(TAG, "This is only a simple test");
        }

        public void setTransmit(OneFrameDetection oneframe) {
            mOneFrameDetection = oneframe;
            mDetectionValid = true;
            String notificationContent = calcScenario();
            getNotificationManager().notify(1, getNotification("Current Scenario", notificationContent));
            if (statuschange) {
                getNotificationManager().notify(2, getNotification("Scenario Change", mnotifyChange));
            }
        }
    }
}


package com.pedro.vlctestapp;

import java.util.HashMap;

public interface Category {
    String[] mCategoryTab = {"person", "bicycle", "car", "motorcycle", "airplane", "bus",
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

    HashMap<String, Boolean> mShowCategory = new HashMap<String, Boolean>() {
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
    };
}

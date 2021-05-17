package com.pedro.vlctestapp;

import java.util.ArrayList;
import java.util.List;

public class OneFrameDetection {
    public String frameId;
    public String url;
    public String timestamp;
    public List<BoundingBox> bboxes;

    public OneFrameDetection(String frameId, String url, String timeStamp, List<BoundingBox> bboxes) {
        this.frameId = frameId;
        this.url = url;
        this.timestamp = timeStamp;
        this.bboxes = new ArrayList<>(bboxes);
    }

    public OneFrameDetection(String frameId, String url, String timeStamp) {
        this.frameId = frameId;
        this.url = url;
        this.timestamp = timeStamp;
        this.bboxes = new ArrayList<>();
    }

    @Override
    public String toString() {
        String temp = "OneFrameDetection result: " + frameId + " from url: " + url + ", is arrived at time: " +
                timestamp + " , with bbox list: ";
        if (!bboxes.isEmpty()) {
            for (BoundingBox elem : bboxes) {
                temp = temp + elem.toString() + "  ";
            }
            return temp;
        } else {
            return temp += "None";
        }
    }

    public class BoundingBox {
        public float x1;
        public float y1;
        public float x2;
        public float y2;
        public int cls;

        public BoundingBox() {
            x1 = 0;
            x2 = 0;
            y1 = 0;
            y2 = 0;
            cls = -1;
        }

        public BoundingBox(float x1, float y1, float x2, float y2, int cls) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.cls = cls;
        }

        @Override
        public String toString() {
            return "[ " + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ", " + cls + " ]";
        }
    }
}

class WebSocketRequest {
    public String op;
    public String message;

    public WebSocketRequest(String Option, String Message) {
        op = Option;
        message = Message;
    }

    @Override
    public String toString() {
        return "WebSocket Request option: " + op + "\n Request message: " + message + "\n";
    }
}

package com.zhuorui.securities.market.socket;

import android.annotation.SuppressLint;
import com.zhuorui.securities.market.event.SocketAuthCompleteEvent;
import com.zhuorui.securities.market.event.SocketDisconnectEvent;
import com.zhuorui.securities.market.model.StockTopic;
import com.zhuorui.securities.market.model.StockTopicDataTypeEnum;
import com.zhuorui.securities.market.socket.push.StocksTopicMinuteKlineResponse;
import com.zhuorui.securities.market.socket.push.StocksTopicMarketResponse;
import com.zhuorui.securities.market.socket.push.StocksTopicPriceResponse;
import com.zhuorui.securities.market.socket.push.StocksTopicTransResponse;
import com.zhuorui.securities.market.socket.request.SocketHeader;
import com.zhuorui.securities.market.socket.request.SocketRequest;
import com.zhuorui.securities.market.socket.request.StockKlineGetDaily;
import com.zhuorui.securities.market.socket.request.StockMinuteKline;
import com.zhuorui.securities.market.socket.response.*;
import com.zhuorui.securities.market.util.ByteBufferUtil;
import com.zhuorui.securities.market.util.GZipUtil;
import com.zhuorui.securities.base2app.infra.LogInfra;
import com.zhuorui.securities.base2app.rxbus.RxBus;
import com.zhuorui.securities.base2app.util.DeviceUtil;
import com.zhuorui.securities.base2app.util.JsonUtil;
import com.zhuorui.securities.base2app.util.Md5Util;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SocketClient {

    private final String TAG = "SocketClient";

    private static SocketClient instance;
    private WebSocketClient client;
    private Map<String, SocketRequest> requestMap;

    private final boolean openGzip = true;

    public static SocketClient getInstance() {
        if (instance == null) {
            synchronized (SocketClient.class) {
                if (instance == null) {
                    instance = new SocketClient();
                }
            }
        }
        return instance;
    }

    public void destroy() {
        if (client != null) {
            // 解除所有的订阅
            unBindAllTopic();
            // 断开连接
            client.close();
            client = null;
        }
        if (requestMap != null) {
            requestMap.clear();
            requestMap = null;
        }
    }

    public void connect() {
        try {
            // 先断开上一次的连接
            destroy();
            // 重新创建连接
            requestMap = new HashMap<>();
            client = new WebSocketClient(new URI(SocketApi.SOCKET_URL), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    LogInfra.Log.d(TAG, "握手成功");
                    sendAuth();
                }

                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onMessage(ByteBuffer byteBuffer) {
                    String message;
                    if (openGzip) {
                        byte[] bytes = ByteBufferUtil.byteBuffer2Byte(byteBuffer);
                        message = GZipUtil.uncompressToString(bytes);
                    } else {
                        message = ByteBufferUtil.byteBuffer2String(byteBuffer);
                    }
                    LogInfra.Log.d(TAG, "<-- onMessage " + message);
                    if (message.equals("over")) {
                        client.close();
                    }
                    try {
                        JSONObject jsonObject = JsonUtil.toJSONObject(message);
                        if (jsonObject != null && jsonObject.has("header")) {
                            JSONObject header = jsonObject.getJSONObject("header");
                            String path = header.getString("path");
                            switch (path) {
                                case SocketApi.PUSH_STOCK_INFO:
                                    // 行情
                                    RxBus.getDefault().post(JsonUtil.fromJson(message, StocksTopicMarketResponse.class));
                                    break;
                                case SocketApi.PUSH_STOCK_KLINE:
                                    // K线
                                    JSONObject body = jsonObject.getJSONObject("body");
                                    String klineType = body.getString("klineType");

                                    // TODO 暂时只判断2-1
                                    if (klineType.equals(StockTopicDataTypeEnum.kminute.getValue())) {
                                        RxBus.getDefault().post(JsonUtil.fromJson(message, StocksTopicMinuteKlineResponse.class));
                                    }
                                    break;
                                case SocketApi.PUSH_STOCK_TRANS:
                                    // TODO 盘口
                                    RxBus.getDefault().post(JsonUtil.fromJson(message, StocksTopicTransResponse.class));
                                    break;
                                case SocketApi.PUSH_STOCK_PRICE:
                                    // TODO 股价
                                    RxBus.getDefault().post(JsonUtil.fromJson(message, StocksTopicPriceResponse.class));
                                    break;
                            }
                        } else {
                            SocketResponse response = JsonUtil.fromJson(message, SocketResponse.class);
                            if (response != null && response.isSuccessful()) {
                                switch (Objects.requireNonNull(response.getPath())) {
                                    case SocketApi.AUTH:
                                        // 认证成功
                                        RxBus.getDefault().post(new SocketAuthCompleteEvent());
                                        break;
                                    case SocketApi.TOPIC_UNBIND:
                                        // 传递上层，解绑订阅成功
                                        RxBus.getDefault().post(new StockUnBindTopicResponse(requestMap.remove(response.getRespId())));
                                        break;
                                    case SocketApi.PUSH_STOCK_KLINE_GET_MINUTE:
                                        // 获取分时
                                        requestMap.remove(response.getRespId());
                                        RxBus.getDefault().post(JsonUtil.fromJson(message, GetStocksMinuteKlineResponse.class));
                                        break;
                                    case SocketApi.PUSH_STOCK_KLINE_GET_DAILY:
                                        // 获取日K
                                        requestMap.remove(response.getRespId());
                                        RxBus.getDefault().post(JsonUtil.fromJson(message, StocksDayKlineResponse.class));
                                        break;
                                    case SocketApi.PUSH_STOCK_KLINE_GET_FIVE_DAY:
                                        // 获取五日
                                        requestMap.remove(response.getRespId());
                                        RxBus.getDefault().post(JsonUtil.fromJson(message, StocksFiveDayKlineResponse.class));
                                        break;
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onWebsocketPing(WebSocket conn, Framedata f) {
                    LogInfra.Log.d(TAG, "ping>>" + f);
                    super.onWebsocketPing(conn, f);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    LogInfra.Log.d(TAG, "链接已关闭");
                    // 通知上层连接断开
                    RxBus.getDefault().post(new SocketDisconnectEvent());
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    LogInfra.Log.d(TAG, "发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.setConnectionLostTimeout(30);
        client.connect();
        client.getDraft();
        LogInfra.Log.d(TAG, "正在连接...");
    }

    private void sendRequest(SocketRequest request) {
        if (request == null || client == null || client.getReadyState() != WebSocket.READYSTATE.OPEN) {
            return;
        }
        String json = JsonUtil.toJson(request);
        LogInfra.Log.d(TAG, "--> sendRequest " + json);
        if (openGzip) {
            byte[] zipBytes = GZipUtil.compress(json);
            client.send(ByteBuffer.wrap(zipBytes));
        } else {
            client.send(ByteBufferUtil.string2ByteBuffer(json));
        }
    }

    public void bindTopic(StockTopic... topics) {
        if (topics == null || topics.length == 0) {
            return;
        }
        sendRequest(createTopicMessage(SocketApi.TOPIC_BIND, topics));
    }

    public void unBindTopic(StockTopic... topics) {
        SocketRequest request = createTopicMessage(SocketApi.TOPIC_UNBIND, topics);
        sendRequest(request);
        requestMap.put(Objects.requireNonNull(request.getHeader()).getReqId(), request);
    }

    private void unBindAllTopic() {
        SocketRequest request = createTopicMessage(SocketApi.TOPIC_UNBIND_ALL);
        sendRequest(request);
    }

    private void sendAuth() {
        String devId = DeviceUtil.getDeviceUuid();

        SocketRequest request = new SocketRequest();
        SocketHeader socketHeader = getRequestHeader(SocketApi.AUTH);
        socketHeader.setDevId(devId);
        request.setHeader(socketHeader);

        Map<String, Object> body = new HashMap<>();
        body.put("devId", devId);
        long timestamp = System.currentTimeMillis();
        body.put("timestamp", System.currentTimeMillis());
        String str = devId + timestamp + SocketApi.SOCKET_AUTH_SIGNATURE;
        String token = Md5Util.getMd5Str(str);
        body.put("token", token);

        request.setBody(body);
        sendRequest(request);
    }

    @SuppressLint("DefaultLocale")
    private SocketRequest createTopicMessage(String path, StockTopic... topics) {
        SocketRequest socketRequest = new SocketRequest();
        SocketHeader socketHeader = getRequestHeader(path);
        socketRequest.setHeader(socketHeader);

        if (topics != null) {
            StockSubTopic subTopic = new StockSubTopic();
            subTopic.setTopics(topics);
            socketRequest.setBody(subTopic);
        }

        return socketRequest;
    }

    @SuppressLint("DefaultLocale")
    public String requestGetDailyKline(StockKlineGetDaily stockKlineGetDaily) {
        SocketRequest request = new SocketRequest();
        SocketHeader socketHeader = getRequestHeader(SocketApi.PUSH_STOCK_KLINE_GET_DAILY);
        request.setHeader(socketHeader);
        request.setBody(stockKlineGetDaily);

        sendRequest(request);
        requestMap.put(Objects.requireNonNull(request.getHeader()).getReqId(), request);
        return socketHeader.getReqId();
    }

    @SuppressLint("DefaultLocale")
    public String requestGetFiveDayKline(StockKlineGetDaily stockKlineGetDaily) {
        SocketRequest request = new SocketRequest();
        SocketHeader socketHeader = getRequestHeader(SocketApi.PUSH_STOCK_KLINE_GET_FIVE_DAY);
        request.setHeader(socketHeader);
        request.setBody(stockKlineGetDaily);

        sendRequest(request);
        requestMap.put(Objects.requireNonNull(request.getHeader()).getReqId(), request);
        return socketHeader.getReqId();
    }

    @SuppressLint("DefaultLocale")
    public String requestGetMinuteKline(StockMinuteKline stockMinuteKline) {
        SocketRequest request = new SocketRequest();
        SocketHeader socketHeader = getRequestHeader(SocketApi.PUSH_STOCK_KLINE_GET_MINUTE);
        request.setHeader(socketHeader);
        request.setBody(stockMinuteKline);

        sendRequest(request);
        requestMap.put(Objects.requireNonNull(request.getHeader()).getReqId(), request);
        return socketHeader.getReqId();
    }

    private SocketHeader getRequestHeader(String path) {
        SocketHeader socketHeader = new SocketHeader();
        socketHeader.setLanguage("ZN");
        socketHeader.setReqId(UUID.randomUUID().toString());
        socketHeader.setVersion("1.0.0");
        socketHeader.setPath(path);
        return socketHeader;
    }
}
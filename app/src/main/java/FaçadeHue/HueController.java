package FaçadeHue;


import android.util.Log;

import com.javierizquierdovera.ConnectAndroidAPPtoSwitchPhlipsHue.MainActivity;
import com.philips.lighting.hue.demo.ConnectAndroidAPPtoSwitchPhlipsHue.R;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedEvent;
import com.philips.lighting.hue.sdk.wrapper.connection.ConnectionEvent;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryCallback;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryResult;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeBuilder;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeState;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipResponse;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridge;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridges;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * Created by Lifka on 04/12/2017.
 */

public class HueController {

    private static HueController instance;

    private Bridge current_bridge = null;
    private static BridgeDiscovery bridge_discovery = null;

    public static final int MAX_HUE = 65535;
    private HueController(){}

    public static HueController getInstance(){
        if (instance == null){
            instance = new HueController();
        }

        return instance;
    }


    public void startBridgeDiscovery(BridgeDiscoveryCallback bridgeDiscoveryCallback) {
        FaçadeHue.BridgeConnection.getInstance().startBridgeDiscovery(bridgeDiscoveryCallback);
    }

    public void connectToBridge(String bridgeIp, BridgeConnectionCallback bridgeConnectionCallback, BridgeStateUpdatedCallback bridgeStateUpdatedCallback) {
        FaçadeHue.BridgeConnection.getInstance().connectToBridge(bridgeIp, bridgeConnectionCallback, bridgeStateUpdatedCallback);
    }


    private void disconnectFromBridge() {
        FaçadeHue.BridgeConnection.getInstance().disconnectFromBridge();
    }


    private void stopBridgeDiscovery() {
        FaçadeHue.BridgeConnection.getInstance().stopBridgeDiscovery();
    }


    public void randomColor(){
        ColorController.getInstance().randomColor( FaçadeHue.BridgeConnection.getInstance().getBridgeState());
    }

}

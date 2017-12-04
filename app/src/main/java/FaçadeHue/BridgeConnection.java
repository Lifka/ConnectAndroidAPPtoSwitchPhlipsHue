package FaçadeHue;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryCallback;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeBuilder;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeState;

/**
 * Created by Lifka on 04/12/2017.
 */

public class BridgeConnection {
    private static BridgeConnection instance;

    private Bridge current_bridge = null;
    private static BridgeDiscovery bridge_discovery = null;

    private BridgeConnection(){}

    public static BridgeConnection getInstance(){
        if (instance == null){
            instance = new BridgeConnection();
        }

        return instance;
    }

    public static BridgeDiscovery getBridgeDiscovery(){
        return bridge_discovery;
    }

    /**
     * Start the bridge discovery search
     * Read the documentation on meethue for an explanation of the bridge discovery options
     */
    public void startBridgeDiscovery(BridgeDiscoveryCallback bridgeDiscoveryCallback) {
        disconnectFromBridge();

        bridge_discovery = new BridgeDiscovery();
        bridge_discovery.search(BridgeDiscovery.BridgeDiscoveryOption.UPNP, bridgeDiscoveryCallback);
    }

    /**
     * Use the BridgeBuilder to create a bridge instance and connect to it
     */
    public void connectToBridge(String bridgeIp, BridgeConnectionCallback bridgeConnectionCallback, BridgeStateUpdatedCallback bridgeStateUpdatedCallback) {
        stopBridgeDiscovery();
        disconnectFromBridge();

        current_bridge = new BridgeBuilder("app name", "device name")
                .setIpAddress(bridgeIp)
                .setConnectionType(BridgeConnectionType.LOCAL)
                .setBridgeConnectionCallback(bridgeConnectionCallback)
                .addBridgeStateUpdatedCallback(bridgeStateUpdatedCallback)
                .build();

        current_bridge.connect();
    }

    /**
     * Disconnect a bridge
     * The hue SDK supports multiple bridge connections at the same time,
     * but for the purposes of this demo we only connect to one bridge at a time.
     */
    public void disconnectFromBridge() {
        if (current_bridge != null) {
            current_bridge.disconnect();
            current_bridge = null;
        }
    }

    /**
     * Stops the bridge discovery if it is still running
     */
    public void stopBridgeDiscovery() {
        if (bridge_discovery != null) {
            bridge_discovery.stop();
            bridge_discovery = null;
        }
    }
    
    public BridgeState getBridgeState(){
        return current_bridge.getBridgeState();
    }
}

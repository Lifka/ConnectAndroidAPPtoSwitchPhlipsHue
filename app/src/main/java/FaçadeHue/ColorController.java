package FaçadeHue;

import android.util.Log;

import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeResponseCallback;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeState;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ClipResponse;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

import java.util.List;
import java.util.Random;

import static FaçadeHue.HueController.MAX_HUE;

/**
 * Created by Lifka on 04/12/2017.
 */

public class ColorController {

    private static ColorController instance;

    private ColorController(){}

    public static ColorController getInstance(){
        if (instance == null){
            instance = new ColorController();
        }

        return instance;
    }


    public void randomColor(BridgeState bridgeState) {

        List<LightPoint> lights = bridgeState.getLights();

        Random rand = new Random();

        for (final LightPoint light : lights) {

            setColor(light, rand.nextInt(MAX_HUE));

        }
    }


    public void setColor(LightPoint light, int color) {

        final LightState lightState = new LightState();
        lightState.setHue(color);

        final String id = light.getIdentifier();

        light.updateState(lightState, BridgeConnectionType.LOCAL, new BridgeResponseCallback() {
                @Override
                public void handleCallback(Bridge bridge, ReturnCode returnCode, List<ClipResponse> list, List<HueError> errorList) {
                    if (returnCode == ReturnCode.SUCCESS) {
                        Log.i("[---DEBUG---]: ", this.getClass().getSimpleName() + " -> Changed hue of light " + id + " to " + lightState.getHue());
                    } else {
                        Log.e("[---DEBUG---]: ", this.getClass().getSimpleName() + " -> Error changing hue of light " + id);
                        for (HueError error : errorList) {
                            Log.e("[---DEBUG---]: ", this.getClass().getSimpleName() + " -> " + error.toString());
                        }
                    }
                }
        });
    }
}

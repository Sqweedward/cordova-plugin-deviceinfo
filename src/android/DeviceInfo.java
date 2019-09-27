package com.eximbank.cordova.plugin.DeviceInfo;

// The native TimeZone and Settings API
import java.util.TimeZone;
import android.provider.Settings;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfo extends CordovaPlugin {
    public static final String TAG = "Device";

    public static String platform; // Device OS
    public static String uuid; // Device UUID

    private static final String ANDROID_PLATFORM = "Android";
    private static final String AMAZON_PLATFORM = "amazon-fireos";
    private static final String AMAZON_DEVICE = "Amazon";

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        // Verify that the user sent a 'show' action
        if (!action.equals("getDeviceInfo")) {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
        }
        String operation;
        try {
            JSONObject options = args.getJSONObject(0);
            operation = options.getString("operation");
        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }
        if ("getDeviceInfo".equals(action)) {
            try {
                JSONObject r = new JSONObject();
                r.put("uuid", this.getUuid());
                r.put("version", this.getOSVersion());
                r.put("platform", this.getPlatform());
                r.put("model", this.getModel());
                r.put("manufacturer", this.getManufacturer());
                r.put("isVirtual", this.isVirtual());
                r.put("serial", this.getSerialNumber());
                callbackContext.success("Data: " + r);
            } catch (JSONException e) {
                callbackContext.error("Error getting data: " + e.getMessage());
                return false;
            }
        } else

        {
            return false;
        }
        // Send a positive result to the callbackContext
        // PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
        // callbackContext.sendPluginResult(pluginResult);
        return true;
    }
    // --------------------------------------------------------------------------
    // LOCAL METHODS
    // --------------------------------------------------------------------------

    /**
     * Get the OS name.
     *
     * @return
     */
    public String getPlatform() {
        String platform;
        if (isAmazonDevice()) {
            platform = AMAZON_PLATFORM;
        } else {
            platform = ANDROID_PLATFORM;
        }
        return platform;
    }

    /**
     * Get the device's Universally Unique Identifier (UUID).
     *
     * @return
     */
    public String getUuid() {
        String uuid = Settings.Secure.getString(this.cordova.getActivity().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        return uuid;
    }

    public String getModel() {
        String model = android.os.Build.MODEL;
        return model;
    }

    public String getProductName() {
        String productname = android.os.Build.PRODUCT;
        return productname;
    }

    public String getManufacturer() {
        String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer;
    }

    public String getSerialNumber() {
        String serial = android.os.Build.getSerial();
        return serial;
    }

    /**
     * Get the OS version.
     *
     * @return
     */
    public String getOSVersion() {
        String osversion = android.os.Build.VERSION.RELEASE;
        return osversion;
    }

    public String getSDKVersion() {
        @SuppressWarnings("deprecation")
        String sdkversion = android.os.Build.VERSION.SDK;
        return sdkversion;
    }

    public String getTimeZoneID() {
        TimeZone tz = TimeZone.getDefault();
        return (tz.getID());
    }

    /**
     * Function to check if the device is manufactured by Amazon
     *
     * @return
     */
    public boolean isAmazonDevice() {
        if (android.os.Build.MANUFACTURER.equals(AMAZON_DEVICE)) {
            return true;
        }
        return false;
    }

    public boolean isVirtual() {
        return android.os.Build.FINGERPRINT.contains("generic") || android.os.Build.PRODUCT.contains("sdk");
    }
}
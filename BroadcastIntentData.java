package com.mooshim.mooshimeter.common;

import android.content.Intent;

import com.mooshim.mooshimeter.interfaces.MooshimeterControlInterface;

/**
 * Created by Workroom on 7/6/2016.   added for broadcast intent
 */
public class BroadcastIntentData {

    static boolean CH1isSaved = false;
    static MeterReading valCH1;

    public static void broadcastMeterReading(MooshimeterControlInterface.Channel c, MeterReading val) {
        /* broadcast the intent containing both both CH1 & CH2.  Any and all receivers on the android device
           will receive the intent if the receiver has an action filter of   com.mooshim.mooshimeter.SAMPLE_INTENT
           Change MeterReading val so broadcast receivers can more easily use the data in key:value pair format.
        */

        if (c == MooshimeterControlInterface.Channel.CH1) {
            valCH1 = val;      // save the val of CH1 until CH2 val is also received
            CH1isSaved = true;
        }
        if ((c == MooshimeterControlInterface.Channel.CH2) && (CH1isSaved)) {  // we got data for both channels, so broadcast {
            Intent intent = new Intent();
            intent.putExtra("units1", valCH1.units);  // key, value pair
            intent.putExtra("value1", valCH1.value);
            intent.putExtra("units2", val.units);     // key, value pair
            intent.putExtra("value2", val.value);
            intent.setAction("com.mooshim.mooshimeter.SAMPLE_INTENT");
            try {
                Util.getRootContext().sendBroadcast(intent);  // context must come from an activity or MyApplication
            } catch (Exception e) {
                // placeholder
            }
            CH1isSaved = false;     // reset the flag
        }
    }
}
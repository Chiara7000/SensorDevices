package ie.corktrainingcentre.sensordevices;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {

    String[] deviceNames;
    String[] fileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        deviceNames = res.getStringArray(R.array.device_names);
        fileNames = res.getStringArray(R.array.file_names);

        LinearLayout sensorLayout = (LinearLayout) findViewById(R.id.sensorDevices);
        for(int index = 0; index < deviceNames.length; index++) {
            sensorLayout.addView( createSensorDeviceItem( index ) );
        }

    }

    private GridLayout createSensorDeviceItem(int index) {
        // the getSystemService() method will get a handle to a system-level service by name
        // the name of the system level service we want is Context.LAYOUT_INFLATER_SERVICE
        // this system service is a LayoutInflater for inflating layout resources in this context
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View deviceGridLayout = inflater.inflate(R.layout.device_grid, null);
        GridLayout deviceGridLayout = (GridLayout)inflater.inflate(R.layout.device_grid, null);

        ImageView deviceImage = (ImageView)deviceGridLayout.findViewById(R.id.device_image);
        TextView deviceName = (TextView)deviceGridLayout.findViewById(R.id.device_name);
        TextView deviceText = (TextView)deviceGridLayout.findViewById(R.id.device_text);

        deviceImage.setImageResource( getSensorImageId(index) );
        deviceName.setText( deviceNames[index] );
        deviceText.setText( getSensorText(index) );

        return deviceGridLayout;
    }

    private int getSensorImageId(int index) {
        String fileName = fileNames[index];
        String resourceType = "drawable";
        String packageName = getApplicationContext().getPackageName();
        Resources res = this.getResources();
        int identifier = res.getIdentifier( fileName, resourceType, packageName );
        return identifier;
    }

    private String getSensorText(int index) {
        String sensorText = null;
        String fileName = fileNames[index];
        String resourceType = "raw";
        String packageName = getApplicationContext().getPackageName();
        Resources res = this.getResources();
        int identifier = res.getIdentifier( fileName, resourceType, packageName );
        InputStream file = null;
        try {
            file = res.openRawResource(identifier);
            byte[] buffer = new byte[file.available()];
            file.read(buffer, 0, buffer.length);
            sensorText = new String(buffer, "UTF-8");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sensorText;
    }

}
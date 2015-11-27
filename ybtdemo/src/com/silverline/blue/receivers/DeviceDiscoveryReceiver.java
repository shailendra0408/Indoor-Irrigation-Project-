package com.silverline.blue.receivers;

import java.util.Iterator;
import java.util.Set;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.silverline.blue.activities.BlueDevicesActivity;
import com.silverline.blue.activities.R;
import com.silverline.blue.application.SilverlineBlueApplication;
import com.silverline.blue.utils.BlueConstants;
import com.silverline.blue.utils.BlueUtils;

public class DeviceDiscoveryReceiver extends BroadcastReceiver {
	private static final String TAG = DeviceDiscoveryReceiver.class
			.getSimpleName();
	private static final int NOTIFICATION_ID = 11;
	private Context mContext = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		if (intent.getAction().equalsIgnoreCase(BluetoothDevice.ACTION_FOUND)) {
			// Device found
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			String devName = device.getName();
			BlueUtils.LogV(TAG, "New device found :: " + devName);
			BlueConstants.sBlueDevices.add(device);
		} else if (intent.getAction().equalsIgnoreCase(
				BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
			BlueUtils.LogV(TAG, "Device search strated.");
			if (BlueConstants.sBlueDevices != null)
				BlueConstants.sBlueDevices.clear();
		} else if (intent.getAction().equalsIgnoreCase(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
			BlueUtils.LogV(TAG, "Device search finished.");
			// Create notification or fetch the details
			int blueDevCount = BlueConstants.sBlueDevices.size();
			if (blueDevCount > BlueConstants.INT_ZERO) {
				// Set paired device if any
				BlueUtils.LogI(TAG,
						"Device search finished count greater than zero.");
				Set<BluetoothDevice> mBlueDevices = BluetoothAdapter
						.getDefaultAdapter().getBondedDevices();
				Iterator<BluetoothDevice> deviceIterator = mBlueDevices
						.iterator();
				while (deviceIterator.hasNext()) {
					BlueUtils.LogV(TAG, "Device search finished  Iterator.");
					BluetoothDevice bluetoothDevice = (BluetoothDevice) deviceIterator
							.next();
					String deviceName = bluetoothDevice.getName();
					if (deviceName.equalsIgnoreCase(BlueConstants.DEVICE_NAME)) {
						// create socket and pass
						BlueUtils.LogD(TAG, "Receiver Paired device :: "
								+ deviceName);
						BlueConstants.sArduinoBlueDevice = bluetoothDevice;

						// NO need to create notification .Launch activity
						// directly
						Intent actIntent = new Intent(mContext,
								BlueDevicesActivity.class);
						actIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(actIntent);
						// createNotification(blueDevCount + "found");
						break;
					}
				}
			} else
				BlueUtils
						.showToast("Device discovery finished. No device found.");
		}
	}

	/**
	 * This method creates notification that a new device is found
	 */
	@SuppressWarnings("unused")
	private void createNotification(String iMessage) {
		NotificationManager mNotificationManager = (NotificationManager) SilverlineBlueApplication
				.getsContext().getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(mContext, BlueDevicesActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 10,
				intent, PendingIntent.FLAG_ONE_SHOT);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				mContext)
				.setSmallIcon(R.drawable.device_access_bluetooth)
				.setContentTitle("New Device")
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(iMessage))
				.setContentText(iMessage);
		mBuilder.setAutoCancel(true);
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}

package com.silverline.blue.application;

import android.app.Application;
import android.content.Context;

public class SilverlineBlueApplication extends Application {
	private static Context sContext = null;

	public static Context getsContext() {
		return sContext;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = this;
	}
}

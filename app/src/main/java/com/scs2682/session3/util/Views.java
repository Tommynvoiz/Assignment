package com.scs2682.session3.util;
import android.app.Activity;

public class Views {


	public static boolean isActivityNull(Activity activity) {
		return activity == null || activity.isFinishing() || activity.isDestroyed();
	}
}
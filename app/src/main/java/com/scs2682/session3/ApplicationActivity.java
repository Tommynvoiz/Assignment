package com.scs2682.session3;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.scs2682.session3.ui.Form;
import com.scs2682.session3.ui.Greeting;
import com.scs2682.session3.util.Views;

public class ApplicationActivity extends Activity {
	public static final String NAME = ApplicationActivity.class.getSimpleName();

	public static final String FIRST_NAME_KEY = "firstName";
	public static final String LAST_NAME_KEY = "lastName";

	/**
	 * @param firstName first name
	 * @param lastName last name
	 */
	public void onFormEnterClick(String firstName, String lastName) {
		if (Views.isActivityNull(this)) {
			return;
		}

		FragmentManager fragmentManager = getFragmentManager();
		Fragment oldGreeting = fragmentManager.findFragmentByTag(Greeting.NAME);
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		if (oldGreeting != null) {

			fragmentTransaction.remove(oldGreeting);
		}

		fragmentTransaction
			.add(R.id.rootView, Greeting.newInstance(firstName, lastName), Greeting.NAME)
			.commit();

		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}


	public void onGreetingReset() {
		if (Views.isActivityNull(this)) {
			return;
		}

		FragmentManager fragmentManager = getFragmentManager();
		((Form) fragmentManager.findFragmentByTag(Form.NAME)).reset();
		Fragment greeting = fragmentManager.findFragmentByTag(Greeting.NAME);

		if (greeting != null) {
			fragmentManager.beginTransaction()
				.remove(greeting)
				.commit();
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.w(NAME, NAME + ".onCreate()");


		setContentView(R.layout.applicationactivity);


		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.add(R.id.rootView, new Form(), Form.NAME)
				.commit();
		}
	}
}
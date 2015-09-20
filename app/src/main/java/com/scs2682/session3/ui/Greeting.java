package com.scs2682.session3.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.scs2682.session3.ApplicationActivity;
import com.scs2682.session3.R;

public class Greeting extends Fragment implements View.OnClickListener {
	public static final String NAME = Greeting.class.getSimpleName();

	public static Greeting newInstance(String firstName, String lastName) {
		firstName = firstName != null ? firstName : "";
		lastName = lastName != null ? lastName : "";

		final Bundle bundle = new Bundle();
		bundle.putString(ApplicationActivity.FIRST_NAME_KEY, firstName);
		bundle.putString(ApplicationActivity.LAST_NAME_KEY, lastName);

		final Greeting fragment = new Greeting();
		fragment.setArguments(bundle);
		return fragment;
	}

	private String firstName;
	private String lastName;
	private TextView textView;
	private View button;

	@Override
	public void onClick(View view) {
		if (button.equals(view)) {
			firstName = null;
			lastName = null;
			textView.setText(null);
			view.setVisibility(View.GONE);
			((ApplicationActivity) getActivity()).onGreetingReset();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.w(NAME, NAME + ".onCreateView()");

		return inflater.inflate(R.layout.greeting, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Log.w(NAME, NAME + ".onViewCreated()");

		textView = (TextView) view.findViewById(R.id.textView);
		button = view.findViewById(R.id.button);
		button.setOnClickListener(this);

		Bundle arguments = getArguments();
		if (savedInstanceState != null && savedInstanceState.containsKey(ApplicationActivity.FIRST_NAME_KEY)) {

			firstName = savedInstanceState.getString(ApplicationActivity.FIRST_NAME_KEY);
			lastName = savedInstanceState.getString(ApplicationActivity.LAST_NAME_KEY);
		}
		else if (arguments != null && arguments.containsKey(ApplicationActivity.FIRST_NAME_KEY)) {

			firstName = arguments.getString(ApplicationActivity.FIRST_NAME_KEY);
			lastName = arguments.getString(ApplicationActivity.LAST_NAME_KEY);
		}

		textView.setText(String.format(getString(R.string.greeting_text), firstName, lastName));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.w(NAME, NAME + ".onSaveInstanceState()");

		outState.putString(ApplicationActivity.FIRST_NAME_KEY, firstName);
		outState.putString(ApplicationActivity.LAST_NAME_KEY, lastName);
	}
}

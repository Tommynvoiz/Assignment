package com.scs2682.session3.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.scs2682.session3.ApplicationActivity;
import com.scs2682.session3.R;


public class Form extends Fragment implements View.OnClickListener {
	public static final String NAME = Form.class.getSimpleName();

	private AlertDialog dialog;

	private EditText firstName;
	private EditText lastName;
	private Button button;

	public void reset() {
		firstName.setText("");
		firstName.setCursorVisible(true);
		lastName.setText("");
		lastName.setCursorVisible(true);
		lastName.requestFocus();
	}

	@Override
	public void onClick(View view) {
		if (button.equals(view)) {
			String first = firstName.getText().toString().trim();
			String last = lastName.getText().toString().trim();

			if (!TextUtils.isEmpty(first) && !TextUtils.isEmpty(last)) {

				firstName.setCursorVisible(false);
				lastName.setCursorVisible(false);

				((ApplicationActivity) getActivity()).onFormEnterClick(first, last);
			}
			else {
				if (TextUtils.isEmpty(first)) {
					firstName.requestFocus();
				}
				else {
					lastName.requestFocus();
				}

				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}

				final String close = getString(R.string.form_error_close);
				final String title = getString(R.string.form_error_title);
				final String description = getString(R.string.form_error_description);


				dialog = new AlertDialog.Builder(getActivity())
					.setTitle(title)
					.setMessage(description)
					.setCancelable(true)
					.setNegativeButton(close, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int which) {
							if (dialog != null && dialog.isShowing()) {
								dialog.cancel();
								dialog = null;
							}
						}
					})
					.create();
				dialog.show();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.w(NAME, NAME + ".onCreateView()");
		return inflater.inflate(R.layout.form, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Log.w(NAME, NAME + ".onActivityCreated()");


		firstName = (EditText) view.findViewById(R.id.firstName);
		lastName = (EditText) view.findViewById(R.id.lastName);
		button = (Button) view.findViewById(R.id.button);
		button.setOnClickListener(this);

		if (savedInstanceState != null) {
			firstName.setText(savedInstanceState.getString(ApplicationActivity.FIRST_NAME_KEY));
			lastName.setText(savedInstanceState.getString(ApplicationActivity.LAST_NAME_KEY));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.w(NAME, NAME + ".onSaveInstanceState()");

		final String first = firstName.getText() != null ? firstName.getText().toString() : null;
		final String last = lastName.getText() != null ? lastName.getText().toString() : null;
		outState.putString(ApplicationActivity.FIRST_NAME_KEY, first);
		outState.putString(ApplicationActivity.LAST_NAME_KEY, last);

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.w(NAME, NAME + ".onDestroyView()");

		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		dialog = null;
	}
}

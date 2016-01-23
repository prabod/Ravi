package com.emergelk.ravindrab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPhoneView;
    private EditText mNameView;
    private EditText mIndexT;
    private EditText mIndexR;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        mPhoneView = (EditText) findViewById(R.id.phone);
        mNameView = (EditText) findViewById(R.id.name);
        mIndexR = (EditText) findViewById(R.id.username_R);
        mIndexT = (EditText) findViewById(R.id.username_T);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPhoneView.setError(null);
        mNameView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String indexT = mIndexT.getText().toString();
        String indexR = mIndexR.getText().toString();
        String name = mNameView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(phone) && !isPasswordValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_password));
            focusView = mPhoneView;
            cancel = true;
        } else if (!TextUtils.isEmpty(indexR) && !isIndexValid(indexR)) {
            mIndexR.setError(getString(R.string.error_invalid_index));
            focusView = mIndexR;
            cancel = true;
        } else if (!TextUtils.isEmpty(indexT) && !isIndexValid(indexT)) {
            mIndexT.setError(getString(R.string.error_invalid_index));
            focusView = mIndexT;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, phone, name, indexR, indexT);
            mAuthTask.execute((Void) null);
        }
    }


    private boolean isPasswordValid(String password) {
        return password.length() == 10;
    }

    private boolean isIndexValid(String index) {
        String pattern = "^[a-zA-Z]{2}[r,tR,T]*[\\d]{5}$";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(index);
        return matcher.find();
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPhone;
        private final String mName;
        private final String mIndexR;
        private final String mIndexT;

        UserLoginTask(String email, String password, String name, String indexR, String indexT) {
            mEmail = email;
            mPhone = password;
            mName = name;
            mIndexR = indexR;
            mIndexT = indexT;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            ParseUser user = new ParseUser();
            user.setUsername(mPhone);
            user.setPassword(mPhone);
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        try {
                            ParseUser.logIn(mPhone, mPhone);
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                            installation.put("username", currentUser.getUsername());
                            installation.put("name", mName);
                            installation.put("email", mEmail);
                            installation.put("indexR", mIndexR.toUpperCase());
                            installation.put("indexT", mIndexT.toUpperCase());
                            installation.save();
                            installation.pin();
                            Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(goToMain);
                            //finish();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();

                        Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMain);
                        //finish();
                    }


                }
            });
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPhoneView.setError(getString(R.string.error_incorrect_password));
                mPhoneView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


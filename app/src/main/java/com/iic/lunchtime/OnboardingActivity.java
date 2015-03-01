package com.iic.lunchtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class OnboardingActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_onboarding);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_onboarding, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

    @InjectView(R.id.onboarding_login_btn)
    LoginButton facebookLoginBtn;

    private UiLifecycleHelper facebookLifecycleHelper;

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      //PackageInfo info = null;
      //try {
      //  info = getActivity().getPackageManager().getPackageInfo("com.iic.lunchtime",
      //      PackageManager.GET_SIGNATURES);
      //  for (Signature signature : info.signatures) {
      //    MessageDigest md = MessageDigest.getInstance("SHA");
      //    md.update(signature.toByteArray());
      //    Log.d(LOG_TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
      //  }
      //} catch (PackageManager.NameNotFoundException|NoSuchAlgorithmException e) {
      //  e.printStackTrace();
      //}

      facebookLifecycleHelper = new UiLifecycleHelper(getActivity(), new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
          onSessionStateChanged(session, sessionState, e);
        }
      });
      facebookLifecycleHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_onboarding, container, false);
      ButterKnife.inject(this, rootView);

      facebookLoginBtn.setReadPermissions("public_profile");
      facebookLoginBtn.setFragment(this);

      return rootView;
    }

    private void onSessionStateChanged(Session session, SessionState sessionState, Exception e) {
      if (sessionState.isOpened()) {
        Log.d(LOG_TAG, "Logged in to facebook");
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
      } else if (sessionState.isClosed()) {
        Log.d(LOG_TAG, "Logged out from facebook");
      }
    }

    @Override
    public void onResume() {
      super.onResume();

      Session session = Session.getActiveSession();
      if (session != null && (session.isOpened() || session.isClosed())) {
        onSessionStateChanged(session, session.getState(), null);
      }

      facebookLifecycleHelper.onResume();
    }

    @Override
    public void onPause() {
      super.onPause();
      facebookLifecycleHelper.onPause();
    }

    @Override
    public void onDestroy() {
      super.onDestroy();
      facebookLifecycleHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      facebookLifecycleHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      facebookLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }
  }
}

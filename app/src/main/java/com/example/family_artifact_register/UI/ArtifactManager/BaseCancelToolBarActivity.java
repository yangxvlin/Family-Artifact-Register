package com.example.family_artifact_register.UI.ArtifactManager;

import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.BaseActionBarActivity;


/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 14:05:49
 * @description activity with predefined app bar with one cancel label at top left corner of the
 * page and direct back to ArtifactManageActivity
 */
public abstract class BaseCancelToolBarActivity extends BaseActionBarActivity {

    protected abstract int getLayoutResource();

    @Override
    public void setTitle(CharSequence title) {
        // https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        getMyActionBar().setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        // https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        getMyActionBar().setTitle(titleId);
    }

    /**
     * set left top cancel button and its listener
     */
    public void setCancelButton() {
        // set the button for the action bar
        getMyActionBar().setHomeAsUpIndicator(R.drawable.common_close_button);
    }

    /**
     * abstract method to end the activity
     */
    public abstract void baseFinish();

    protected abstract int getToolBarId();
}

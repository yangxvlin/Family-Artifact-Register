package com.unimelb.family_artifact_register.UI.Upload;

<<<<<<< HEAD
/**
 * @author Haichao Song 854035,
 * @time 2019-9-20 23:25:09
 * @description view holder for post activity
 * Deprecate and integrate with new artifact activity view holder now.
 */
=======
>>>>>>> 12a0d8a99d0a528d8d45489bdec557da828300a3
@Deprecated
public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}

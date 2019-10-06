package com.example.family_artifact_register.UI.ArtifactManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.ImagesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_URL_LIST;
import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_TYPE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

/**
 * 1. display two media source: Image and Video both passed in through
 * List<String> by Key: MEDIA_URL_LIST and
 * media type is passed through int by key: MEDIA_TYPE. value detail see
 * {@link com.example.family_artifact_register.UI.Util.MediaProcessHelper}
 *
 * 2. set media layout param by call setSingleMediaHeight() and setSingleMediaWidth()
 */
public class ViewMediaFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = ViewMediaFragment.class.getSimpleName();

    /**
     * default media frame's height layout parameter value.
     */
    public static final int DEFAULT_FRAME_HEIGHT = 400;

    public static final int DEFAULT_IMAGE_WIDTH = 100;

    public static final int DEFAULT_IMAGE_HEIGHT = 100;

    public static final int DEFAULT_VIDEO_WIDTH = 300;

    public static final int DEFAULT_VIDEO_HEIGHT = ViewMediaFragment.DEFAULT_FRAME_HEIGHT;

    private int mediaType;

    private List<Uri> mediaList;

    /**
     * recycler view adapter for display images
     */
    private ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;

    /**
     * recycler view for display images
     */
    private RecyclerView imageRecyclerView;

    private int frameHeight = DEFAULT_FRAME_HEIGHT;

    private int singleMediaHeight;

    private int singleMediaWidth;

    public ViewMediaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "me fragment created");
        return inflater.inflate(R.layout.fragment_view_media, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FrameLayout frameLayout = view.findViewById(R.id.fragment_view_media_container);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            // get media type and media list
            mediaType = bundle.getInt(MEDIA_TYPE);
            initializeMediaParam();

            // convert single media's String Url to Uri
            List<String> mediaUrlsList = (List<String>) bundle.getSerializable(MEDIA_URL_LIST);
            mediaList = new ArrayList<>();
            for (String mediaUrl: mediaUrlsList) {
                mediaList.add(Uri.parse(mediaUrl));
            }
        } else {
            Log.e(TAG, "error can't get data from parent fragment !!!");
        }

        if (mediaType == TYPE_IMAGE) {
            // set frame layout param
            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    this.getFrameHeight()
            );
            layoutParam.gravity = Gravity.CENTER;

            // set recycler view images
            RecyclerView.LayoutParams recyclerViewParam = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );
            imageRecyclerView = new RecyclerView(getContext());
            imageRecyclerView.setLayoutParams(recyclerViewParam);

            // images horizontally
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            );
            imageRecyclerView.setLayoutManager(layoutManager);

            // image adapter
            imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter(
                    this.getSingleMediaHeight(),
                    this.getSingleMediaWidth()
            );
            for (Uri image: mediaList) {
                imagesRecyclerViewAdapter.addData(image);
            }

            imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);

            frameLayout.setLayoutParams(layoutParam);
            frameLayout.addView(imageRecyclerView);
        } else if (mediaType == TYPE_VIDEO) {
            // set frame layout param
            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    this.getFrameHeight()
            );
            layoutParam.gravity = Gravity.CENTER;

            // set media
            VideoView mediaView = new VideoView(getContext());
            mediaView.setLayoutParams(new FrameLayout.LayoutParams(
                    this.getSingleMediaWidth(),
                    this.getSingleMediaHeight())
            );
            mediaView.setVideoURI(mediaList.get(0));
            mediaView.setMediaController(new MediaController(getContext()));
            mediaView.start();
            mediaView.requestFocus();
            mediaView.setOnCompletionListener(mp -> {
                Log.d(TAG, "Video play finish.");
            });
            mediaView.setOnErrorListener((mp, what, extra) -> {
                Log.d(TAG, "Video play error!!!");
                return false;
            });

            frameLayout.setLayoutParams(layoutParam);
            frameLayout.addView(mediaView);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }
    }

    /**
     * @return created view media fragment
     */
    public static ViewMediaFragment newInstance() { return new ViewMediaFragment(); }

    private void initializeMediaParam() {
        if (mediaType == TYPE_IMAGE) {
            this.setSingleMediaHeight(DEFAULT_IMAGE_HEIGHT);
            this.setSingleMediaWidth(DEFAULT_IMAGE_WIDTH);
        } else if (mediaType == TYPE_VIDEO) {
            this.setSingleMediaHeight(DEFAULT_VIDEO_HEIGHT);
            this.setSingleMediaWidth(DEFAULT_VIDEO_WIDTH);
        } else {
            Log.e(getFragmentTag(), "unknown media type !!!");
        }
    }

    // ********************************** getter & setter *****************************************
    @Deprecated
    public void setFrameHeight(int height) {
        this.frameHeight = height;
        this.singleMediaHeight = this.frameHeight;
    }

    public int getFrameHeight() { return this.frameHeight; }

    public int getSingleMediaHeight() { return singleMediaHeight; }

    public void setSingleMediaHeight(int singleMediaHeight) {
        this.singleMediaHeight = singleMediaHeight;
        this.frameHeight = this.singleMediaHeight;
    }

    public int getSingleMediaWidth() { return singleMediaWidth; }

    public void setSingleMediaWidth(int singleMediaWidth) { this.singleMediaWidth = singleMediaWidth; }
}

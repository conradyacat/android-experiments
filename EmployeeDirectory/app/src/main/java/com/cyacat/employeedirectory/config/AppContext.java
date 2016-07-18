package com.cyacat.employeedirectory.config;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by Conrad Yacat on 7/18/2016.
 */
public final class AppContext {

    private static boolean _isInitialized;
    private static File _photoDirectory;
    private static boolean _isCameraAvailable;

    public static File get_photoDirectory() {
        checkIfInitialized();
        return _photoDirectory;
    }

    public static boolean is_isCameraAvailable() {
        checkIfInitialized();
        return _isCameraAvailable;
    }

    public static void init(Activity activity) {
        Log.i(AppContext.class.getName(), "Initializing application context");
        initDirectoryForPictures();
        checkCameraRequirements(activity);
        _isInitialized = true;
    }

    private static void checkIfInitialized() {
        if (!_isInitialized)
            throw new RuntimeException("Application context is not yet initialized");
    }

    private static void initDirectoryForPictures() {
        _photoDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "EmpDir");
        if (!_photoDirectory.exists()) {
            Log.v(AppContext.class.getName(), "Creating directory for pictures");
            _photoDirectory.mkdirs();
        }
    }

    private static void checkCameraRequirements(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> availableActivities = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        _isCameraAvailable = availableActivities != null && availableActivities.size() > 0;
    }
}

package com.omkarmoghe.pokemap.helpers;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;

import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.RevMobTestingMode;
import com.revmob.ads.banner.RevMobBanner;
import com.revmob.ads.interstitial.RevMobFullscreen;

/**
 * Created by ventaapps on 7/14/16.
 */
public class RevMobHelperClass {

    private static RevMobHelperClass revMobHelperObj;

    public RevMobHelperClass() {
    }

    public static RevMobHelperClass getSharedInstance() {
        if (revMobHelperObj==null)
            revMobHelperObj = new RevMobHelperClass();
        return revMobHelperObj;
    }

    private RevMob revmob;
    private RevMobFullscreen fullscreen;
    private boolean fullscreenIsLoaded;

    public void startFSAds(final Activity activity) {
        fullscreenIsLoaded = false;
        if (revmob==null) {
            revmob = RevMob.startWithListener(activity, new RevMobAdsListener() {
                @Override
                public void onRevMobSessionStarted() {
                    revmob.setUserAgeRangeMin(10);
                    revmob.setUserAgeRangeMax(80);
                    revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
                    loadFullscreen(activity);
                }

                @Override
                public void onRevMobSessionNotStarted(String message) {
                    Log.d("", "RevMob session failed to start.");
                }
            }, "579a0829b28952630b184aca");
        }
        else {
            loadFullscreen(activity);
        }
    }

    public void startBannerAds(final Activity activity, final LinearLayout layout) {
        fullscreenIsLoaded = false;
        if (revmob==null) {
            revmob = RevMob.startWithListener(activity, new RevMobAdsListener() {
                @Override
                public void onRevMobSessionStarted() {
                    revmob.setUserAgeRangeMin(10);
                    revmob.setUserAgeRangeMax(80);
                    revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
                    showBanner(activity, layout);
                }

                @Override
                public void onRevMobSessionNotStarted(String message) {
                    Log.d("", "RevMob session failed to start.");
                }
            }, "579a0829b28952630b184aca");
        }
        else {
            showBanner(activity, layout);
        }
    }

    public void startAds(final Activity activity, final LinearLayout layout) {
        fullscreenIsLoaded = false;
        if (revmob==null) {
            revmob = RevMob.startWithListener(activity, new RevMobAdsListener() {
                @Override
                public void onRevMobSessionStarted() {
                    revmob.setUserAgeRangeMin(10);
                    revmob.setUserAgeRangeMax(80);
                    revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
                    loadFullscreen(activity);
                    showBanner(activity, layout);
                }

                @Override
                public void onRevMobSessionNotStarted(String message) {
                    Log.d("", "RevMob session failed to start. "+ message);
                }
            }, "579a0829b28952630b184aca");
        }
        else {
            loadFullscreen(activity);
            showBanner(activity, layout);
        }
    }

    public void showBanner(final Activity activity, final LinearLayout layout) {

        final RevMobBanner banner = revmob.createBanner(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layout.removeAllViews();
                layout.addView(banner);

            }
        });


    }

    public void hideBanner(LinearLayout layout) {

        // Remove the banner
        layout.removeAllViews();
    }

    // Helper Methods
    //////////////////////////////////////////////////////
    public void loadFullscreen(Activity activity) {

        //load it with RevMob listeners to control the events fired
        fullscreen = revmob.createFullscreen(activity, new RevMobAdsListener() {
            @Override
            public void onRevMobAdReceived() {
                Log.i("RevMob", "Fullscreen loaded.");
                fullscreenIsLoaded = true;
                gameOverScreen();
            }

            @Override
            public void onRevMobAdNotReceived(String message) {
                Log.i("RevMob", "Fullscreen not received.");
            }

            @Override
            public void onRevMobAdDismissed() {
                Log.i("RevMob", "Fullscreen dismissed.");
            }

            @Override
            public void onRevMobAdClicked() {
                Log.i("RevMob", "Fullscreen clicked.");
            }

            @Override
            public void onRevMobAdDisplayed() {
                Log.i("RevMob", "Fullscreen displayed.");
            }
        });
    }
    public void gameOverScreen() {
        if(fullscreenIsLoaded) {
            fullscreen.show(); // call it wherever you want to show the fullscreen ad
        } else {
            Log.i("RevMob", "Ad not loaded yet.");
        }
    }
}

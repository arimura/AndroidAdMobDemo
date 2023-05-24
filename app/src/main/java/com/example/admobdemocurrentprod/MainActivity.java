package com.example.admobdemocurrentprod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private RewardedAd rewardedAd;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                        Log.d("AdListener", "Ad was clicked.");
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the user is about to return
                        // to the app after tapping on an ad.
                        Log.d("AdListener", "Ad was closed.");
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Code to be executed when an ad request fails.
                        Log.d("AdListener", "Ad failed to load with error: " + adError.getMessage());
                    }

                    @Override
                    public void onAdImpression() {
                        // Code to be executed when an impression is recorded
                        // for an ad.
                        Log.d("AdListener", "Ad impression recorded.");
                    }

                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                        Log.d("AdListener", "Ad finished loading.");
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                        Log.d("AdListener", "Ad opened.");
                    }
                });
            }
        });
        Button button = findViewById(R.id.rewarded_video);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(MainActivity.this, "ca-app-pub-3265304037942335/2459999348",
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error.
                                Log.d("MainActivity", loadAdError.toString());
                                rewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd ad) {
                                rewardedAd = ad;
                                Log.d("MainActivity", "Ad was loaded.");
                                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdClicked() {
                                        // Called when a click is recorded for an ad.
                                        Log.d(TAG, "Ad was clicked.");
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when ad is dismissed.
                                        // Set the ad reference to null so you don't show the ad a second time.
                                        Log.d(TAG, "Ad dismissed fullscreen content.");
                                        rewardedAd = null;
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when ad fails to show.
                                        Log.e(TAG, "Ad failed to show fullscreen content.");
                                        rewardedAd = null;
                                    }

                                    @Override
                                    public void onAdImpression() {
                                        // Called when an impression is recorded for an ad.
                                        Log.d(TAG, "Ad recorded an impression.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when ad is shown.
                                        Log.d(TAG, "Ad showed fullscreen content.");
                                    }
                                });

                                rewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                        // Handle the reward.
                                        Log.d(TAG, "The user earned the reward.");
                                        int rewardAmount = rewardItem.getAmount();
                                        String rewardType = rewardItem.getType();
                                    }
                                });

                            }
                        });
            }
        });
    }


}
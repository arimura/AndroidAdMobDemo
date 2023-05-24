package com.example.admobdemocurrentprod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

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
    }
}
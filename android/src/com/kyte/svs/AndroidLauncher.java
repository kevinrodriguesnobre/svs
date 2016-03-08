package com.kyte.svs;

import android.os.Bundle;

import java.sql.*;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new START(), config);
    }
}

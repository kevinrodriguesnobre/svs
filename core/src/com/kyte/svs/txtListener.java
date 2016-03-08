package com.kyte.svs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by 4krodrig on 08.03.2016.
 */
public class txtListener implements Input.TextInputListener {
    Label _usernameLabel;

    public txtListener(Label txtField){
        _usernameLabel = txtField;

    }

    @Override
    public void input(String text) {
        _usernameLabel.setText(text);
        Game._username = text;
    }

    @Override
    public void canceled() {

    }
}

package com.kyte.svs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.kyte.svs.Objects.GameStats;

public class HUD {

    private Stage _stage, _pauseStage, _deathStage;
    private Label _label,_pauseLabel, _deathLabel;
    private Joysticks _joysticks;
    private HpBar _HpBar;
    public float _width;
    public float _height;
    private Image _weaponSwitchImage;
    private Texture[] _weaponTextureArray;
    private GameStats _gameStats;
    private TextField _textField;


    public HUD(GameStats gameStats)
    {
        _stage = new Stage();
        _pauseStage = new Stage();
        _pauseStage = new Stage();
        _deathStage = new Stage();
        _width = Gdx.graphics.getWidth();
        _height = Gdx.graphics.getHeight();
        _joysticks = new Joysticks(_stage,_width,_height);
        _HpBar = new HpBar(_stage,_width,_height);
        _gameStats = gameStats;

        _weaponTextureArray = new Texture[2];
        _weaponTextureArray[0] = new Texture("Weapons/weapon_pistol.png");
        _weaponTextureArray[1] = new Texture("Weapons/weapon_alien.png");

        _weaponSwitchImage = new Image(_weaponTextureArray[0]);
        _weaponSwitchImage.setBounds(_width - (_width / 7), _height - (_height / 7), _width / 7, _height / 7);

        _stage.addActor(_weaponSwitchImage);

        addLabel();
        addPauseTitel();
        addDeathScreenElements();
        Gdx.input.setInputProcessor(_stage);
    }

    public void renderHUD()
    {
        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
    }

    public Joysticks getJoysticks()
    {
        return _joysticks;
    }

    public HpBar getHpBar(){
        return _HpBar;
    }

    public Stage getStage()
    {
        return _stage;
    }

    private void addLabel(){
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        _label = new Label("Round 1", skin);
        _label.setPosition(Gdx.graphics.getWidth() / 6.5f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10);
        _label.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _label.setFontScale(_width / 400);
        _stage.addActor(_label);
    }

    public void updateLabel(int round){
        _label.setText("Round " + round);
    }

    public void drawWeaponSwitchMenu(Player player)
    {
        switch(player.getWeapon().getCurrentWeaponID())
        {
            case 0:
                _weaponSwitchImage.setDrawable(new SpriteDrawable(new Sprite(_weaponTextureArray[0])));
                break;
            case 1:
                _weaponSwitchImage.setDrawable(new SpriteDrawable(new Sprite(_weaponTextureArray[1])));
                break;
        }
    }

    public void renderPauseMenu()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _pauseLabel.setText("Pausiert\n\n" + "Bisher getoetete Gegner: " + _gameStats.getKillCounter() + "\nBisher verschossene Projektile: " + _gameStats.getRoundsShot());

        _stage.act(Gdx.graphics.getDeltaTime());
        _pauseStage.draw();
    }

    private void addPauseTitel()
    {
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        _pauseLabel = new Label("Pausiert\n\n" + "Bisher getoetete Gegner: " + _gameStats.getKillCounter() +"\nBisher verschossene Projektile: " + _gameStats.getRoundsShot(), skin);
        _pauseLabel.setAlignment(Align.center);
        _pauseLabel.setPosition((Gdx.graphics.getWidth() - _pauseLabel.getWidth()) / 2, (Gdx.graphics.getHeight() - _pauseLabel.getHeight()) / 2);
        _pauseLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _pauseLabel .setFontScale(_width / 500);
        _pauseStage.addActor(_pauseLabel);
    }

    private void addDeathScreenElements()
    {
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        _deathLabel = new Label("Du bist tot",skin);
        _deathLabel.setAlignment(Align.center);
        _deathLabel.setPosition((Gdx.graphics.getWidth() - _deathLabel.getWidth()) / 2, (Gdx.graphics.getHeight() - _deathLabel.getHeight()) / 2);
        _deathLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _deathLabel .setFontScale(_width / 500);

        _textField = new TextField("",skin);
        _textField.setText("Bitte Namen eingeben");
        _textField.setPosition((Gdx.graphics.getWidth() - _textField.getWidth()) / 2, (Gdx.graphics.getHeight() - _textField.getHeight()) / 2);
        _textField.setAlignment(Align.center);
        _deathLabel .setFontScale(_width / 500);

        _textField.setTextFieldListener(new TextField.TextFieldListener() {
            public void keyTyped(TextField textField, char key) {
                if (key == '\n') textField.getOnscreenKeyboard().show(false);
            }
        });

        _deathStage.addActor(_deathLabel);
        _deathStage.addActor(_textField);
    }

    public void renderDeathScreen()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _deathStage.act(Gdx.graphics.getDeltaTime());
        _deathStage.draw();
    }
}

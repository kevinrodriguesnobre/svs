package com.kyte.svs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.kyte.svs.Objects.GameStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class HUD {

    boolean _txtFieldRendered = false;
    private Stage _stage, _pauseStage, _deathStage;
    private Label _label, _pauseLabel, _deathLabel;
    private Joysticks _joysticks;
    private HpBar _HpBar;
    public float _width;
    public float _height;
    private Image _weaponSwitchImage, _menuButtonImage, _restartButtonImage;
    private Texture[] _weaponTextureArray;
    private GameStats _gameStats;
    public static  Label _usernameLabel;


    public HUD(GameStats gameStats) {
        _stage = new Stage();
        _pauseStage = new Stage();
        _pauseStage = new Stage();
        _deathStage = new Stage();
        _width = Gdx.graphics.getWidth();
        _height = Gdx.graphics.getHeight();
        _joysticks = new Joysticks(_stage, _width, _height);
        _HpBar = new HpBar(_stage, _width, _height);
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

    public void renderHUD() {
        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
    }

    public Joysticks getJoysticks() {
        return _joysticks;
    }

    public HpBar getHpBar() {
        return _HpBar;
    }

    public Stage getStage() {
        return _stage;
    }

    private void addLabel() {
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        _label = new Label("Round 1", skin);
        _label.setPosition(Gdx.graphics.getWidth() / 6.5f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10);
        _label.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _label.setFontScale(_width / 400);
        _stage.addActor(_label);
    }

    public void updateLabel(int round) {
        _label.setText("Round " + round);
    }

    public void drawWeaponSwitchMenu(Player player) {
        switch (player.getWeapon().getCurrentWeaponID()) {
            case 0:
                _weaponSwitchImage.setDrawable(new SpriteDrawable(new Sprite(_weaponTextureArray[0])));
                break;
            case 1:
                _weaponSwitchImage.setDrawable(new SpriteDrawable(new Sprite(_weaponTextureArray[1])));
                break;
        }
    }

    public void renderPauseMenu() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _pauseLabel.setText("Pausiert\n\n" + "Bisher getoetete Gegner: " + _gameStats.getKillCounter() + "\nBisher verschossene Projektile: " + _gameStats.getRoundsShot());

        _stage.act(Gdx.graphics.getDeltaTime());
        _pauseStage.draw();
    }

    private void addPauseTitel() {
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        _pauseLabel = new Label("Pausiert\n\n" + "Bisher getoetete Gegner: " + _gameStats.getKillCounter() + "\nBisher verschossene Projektile: " + _gameStats.getRoundsShot(), skin);
        _pauseLabel.setAlignment(Align.center);
        _pauseLabel.setPosition((Gdx.graphics.getWidth() - _pauseLabel.getWidth()) / 2, (Gdx.graphics.getHeight() - _pauseLabel.getHeight()) / 2);
        _pauseLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _pauseLabel.setFontScale(_width / 500);
        _pauseStage.addActor(_pauseLabel);
    }

    private void addDeathScreenElements() {
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        _deathLabel = new Label("Du bist tot", skin);
        _deathLabel.setAlignment(Align.center);
        _deathLabel.setPosition((Gdx.graphics.getWidth() - _deathLabel.getWidth()) / 2, (Gdx.graphics.getHeight() - _deathLabel.getHeight()) / 1.5f);
        _deathLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _deathLabel.setFontScale(_width / 500);

        _usernameLabel = new Label("", skin);
        _usernameLabel.setText("Bitte Namen eingeben");
        _usernameLabel.setPosition((Gdx.graphics.getWidth() - _usernameLabel.getWidth()) / 2, (Gdx.graphics.getHeight() - _usernameLabel.getHeight()) / 2);
        _usernameLabel.setFontScale(_width / 500);
        _usernameLabel.setAlignment(Align.center);

        float imageSize = _width / 5;

        _menuButtonImage =  new Image(new Texture(Gdx.files.internal("data/homebutton.png")));
        _menuButtonImage.setBounds(0.2f * imageSize, imageSize * 0.5f, imageSize, imageSize / 2.15f);
        _restartButtonImage = new Image(new Texture(Gdx.files.internal("data/restartbutton.png")));
        _restartButtonImage.setBounds(_width - 1.2f * imageSize, imageSize * 0.5f, imageSize, imageSize / 2.15f);

        _deathStage.addActor(_deathLabel);
        _deathStage.addActor(_usernameLabel);
        _deathStage.addActor(_menuButtonImage);
        _deathStage.addActor(_restartButtonImage);
    }

    public void renderDeathScreen() {
        if (!_txtFieldRendered) {
            _txtFieldRendered = true;
            Gdx.input.getTextInput(new txtListener(_usernameLabel), "Username", Game._username, "Hier ihren Namen eingeben");
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _deathStage.act(Gdx.graphics.getDeltaTime());
        _deathStage.draw();
    }

    public void updateInputField(String str) {
        _usernameLabel.setText(str);
    }

    public Rectangle getMenuButtonRectangle()
    {
        return new Rectangle(_menuButtonImage.getX(),_menuButtonImage.getY(),_menuButtonImage.getWidth(),_menuButtonImage.getHeight());
    }

    public Rectangle getRestartButtonRectangle()
    {
        return new Rectangle(_restartButtonImage.getX(),_restartButtonImage.getY(),_restartButtonImage.getWidth(),_restartButtonImage.getHeight());
    }

    public static void insertIntoDb(String name, int points){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String url = "jdbc:mysql://vsisls4.informatik.uni-hamburg.de/mc05?useUnicode=true&characterEncoding=UTF8";
        String username = "mc05";
        String password = "0ox2DkqP";
        Connection con;
        try {
            con = DriverManager.getConnection(url, username, password);
            String sql =
                    "INSERT INTO user (points, username)" +
                    "VALUES" + "('" + points + "', '" + name + "')";
            System.out.println(sql);
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            /*while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int points = rs.getInt("point");
                System.out.println(id + " - " + name + " - " + points);
            }*/
            con.close();
        } catch (Exception e) {
            System.out.println("ALARM! Error: " + e.getMessage());
        }
    }
}

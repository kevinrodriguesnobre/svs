package com.kyte.svs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class HUD {

    private Stage _stage;
    private Label _label;
    private Joysticks _joysticks;
    private HpBar _HpBar;
    public float _width;
    public float _height;
    private Image _weaponSwitchImage;
    private Texture[] _weaponTextureArray;

    public HUD()
    {
        _stage = new Stage();
        _width = Gdx.graphics.getWidth();
        _height = Gdx.graphics.getHeight();
        _joysticks = new Joysticks(_stage,_width,_height);
        _HpBar = new HpBar(_stage,_width,_height);

        _weaponTextureArray = new Texture[2];
        _weaponTextureArray[0] = new Texture("Weapons/weapon_pistol.png");
        _weaponTextureArray[1] = new Texture("Weapons/weapon_alien.png");

        _weaponSwitchImage = new Image(_weaponTextureArray[0]);
        _weaponSwitchImage.setBounds(_width - (_width / 7), _height - (_height / 7), _width / 7, _height / 7);

        _stage.addActor(_weaponSwitchImage);

        addLabel();
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
}

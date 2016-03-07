package com.kyte.svs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class HUD {

    private Stage _stage;
    private Joysticks _joysticks;
    public float _width;
    public float _height;
    private Button _weaponSwitchButton;
    private Skin _weaponSwitchSkin;
    private String[] _playerTextureNames;
    private Image _weaponSwitchImage;
    private Texture[] _weaponTextureArray;

    public HUD()
    {
        _stage = new Stage();
        _width = Gdx.graphics.getWidth();
        _height = Gdx.graphics.getHeight();
        _joysticks = new Joysticks(_stage,_width,_height);

        _weaponSwitchSkin = new Skin();
        // alle verfügbaren Waffen, die beim Waffenwechsel angezeigt werden sollen
        _weaponSwitchSkin.add("Pistol", new Texture(Gdx.files.internal("Weapons/weapon_pistol.png")));
        _weaponSwitchSkin.add("Alien", new Texture(Gdx.files.internal("Weapons/weapon_alien.png")));

        _playerTextureNames = new String[2];
        _playerTextureNames[0] = "Pistol";
        _playerTextureNames[1] = "Alien";

        _weaponTextureArray = new Texture[2];
        _weaponTextureArray[0] = new Texture("Weapons/weapon_pistol.png");
        _weaponTextureArray[1] = new Texture("Weapons/weapon_alien.png");

        //_weaponSwitchButton =  new Button(_weaponSwitchSkin.getDrawable("Pistol"));
        //_weaponSwitchButton.setBounds(_width - 300, _height - 200, 400, 200);

        _weaponSwitchImage = new Image(_weaponTextureArray[0]);
        _weaponSwitchImage.setBounds(_width - 400, _height - 200, 400, 200);

        _stage.addActor(_weaponSwitchImage);

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

    public Stage getStage()
    {
        return _stage;
    }


    public void drawWeaponSwitchMenu(Player player)
    {
        //_weaponSwitchButton = new Button(_weaponSwitchSkin.getDrawable(_playerTextureNames[player.getWeapon().getCurrentWeaponID()]));
        //_weaponSwitchButton.setBounds(_width - 300, _height - 200, 400, 200);
        //System.out.println("Die Textur sollte nun geändert sein :-) COOL!!!!!!!!!" + "\n" + _playerTextureNames[player.getWeapon().getCurrentWeaponID()]);

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

    public Button getWeaponSwitchButton()
    {
        return _weaponSwitchButton;
    }
}

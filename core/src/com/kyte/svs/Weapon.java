package com.kyte.svs;

import com.kyte.svs.Objects.AlienBullet;
import com.kyte.svs.Objects.PistolBullet;
import com.kyte.svs.Objects.Projectile;

import java.util.Objects;

/**
 * Hält die Texturen für die verschiedenen Waffen des Spielers
 */
public class Weapon
{
    int _currentWeapon;

    public Projectile[] _waffentitel;
    public String[] _texturePaths,_weaponSwitchTexturePaths;
    public Weapon()
    {
        initialisiereWaffentitel();
        initialisiereWeaponTexturePaths();
        initialisiereWeaponSwitchTexturePaths();
        _currentWeapon = 0;
    }

    private void initialisiereWaffentitel()
    {
        _waffentitel = new Projectile[2];
        _waffentitel[0] = new PistolBullet(0,0,0,null); // Normale Pistole
        _waffentitel[1] = new AlienBullet(0,0,0,null);  // Alienlaserwaffe
    }

    private void initialisiereWeaponTexturePaths()
    {
        _texturePaths = new String[2];
        _texturePaths[0] = "Player/Player.mit.Pistole.png";
        _texturePaths[1] = "Player/Player.mit.Alienwaffe.png";

    }

    private void initialisiereWeaponSwitchTexturePaths()
    {
        _weaponSwitchTexturePaths = new String[2];
        _weaponSwitchTexturePaths[0] = "Weapons/weapon_alien.png";
        _weaponSwitchTexturePaths[1] = "Weapons/weapon_pistol.png";
    }

    public Projectile getCurrentWeapon(){
        return _waffentitel[_currentWeapon];
    }

    public int getCurrentWeaponID(){
        return _currentWeapon;
    }

    public void setWeapon(int weaponID) {
        _currentWeapon = weaponID;
    }

    public String getCurrentWeaponPlayerTextureString()
    {
        return _texturePaths[_currentWeapon];
    }

    public String getCurrentWeaponWeaponSwitchTextureString()
    {
        return _weaponSwitchTexturePaths[_currentWeapon];
    }
}

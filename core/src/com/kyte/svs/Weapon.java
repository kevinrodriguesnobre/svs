package com.kyte.svs;

import com.kyte.svs.Objects.AlienBullet;
import com.kyte.svs.Objects.PistolBullet;
import com.kyte.svs.Objects.Projectile;
import com.kyte.svs.Objects.ShotgunBullet;

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
        _waffentitel = new Projectile[Game.WEAPON_AMOUNT];
        _waffentitel[0] = new PistolBullet(0,0,0,null); // Normale Pistole
        _waffentitel[1] = new AlienBullet(0,0,0,null);  // Alienlaserwaffe
        _waffentitel[2] = new ShotgunBullet(0,0,0,null);  // Schrotflinte
    }

    private void initialisiereWeaponTexturePaths()
    {
        _texturePaths = new String[Game.WEAPON_AMOUNT];
        _texturePaths[0] = "Player/Player.mit.Pistole.png";
        _texturePaths[1] = "Player/Player.mit.Alienwaffe.png";
        _texturePaths[2] = "Player/Player.mit.Schrotflinte.png";

    }

    private void initialisiereWeaponSwitchTexturePaths()
    {
        _weaponSwitchTexturePaths = new String[Game.WEAPON_AMOUNT];
        _weaponSwitchTexturePaths[0] = "Weapons/weapon_alien.png";
        _weaponSwitchTexturePaths[1] = "Weapons/weapon_pistol.png";
        _weaponSwitchTexturePaths[2] = "Weapons/weapon_schrotflinte.png";
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
}

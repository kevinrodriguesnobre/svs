package com.kyte.svs;

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
    public Weapon()
    {
        initialisiereWaffentitel();
        _currentWeapon = 0;
    }

    private void initialisiereWaffentitel()
    {
        _waffentitel = new Projectile[1];
        _waffentitel[0] = new PistolBullet(0,0,0,null);
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
}

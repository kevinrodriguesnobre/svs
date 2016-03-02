package com.kyte.svs.Objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Oberklasse für sämtliche Projektile
 */
public class Projectile extends TextureMapObject
{

    int _shootingFrequenz;
    Vector2 _directionVector;
    float _speed;

    /**
     * Initialisiert Projektile
     * @param textureRegion     Die Textur, die das Projektil haben soll
     * @param shootingFrequenz  Die Frequenz in der geschossen werden darf
     * @param speed             Die Geschwindigkeit, mit der  das Projektil geschossen wird
     * @param directionVector   Der Richtungsvektor, in der die Kugel fliegt
     */
    public Projectile(TextureRegion textureRegion, int shootingFrequenz, float speed, Vector2 directionVector)
    {
        super(textureRegion);
        _directionVector = directionVector;
        _shootingFrequenz = shootingFrequenz;
        _speed = speed;
    }

    public void update(float delta)
    {

        setX(delta * _speed * _directionVector.x + this.getX());
        setY(delta * _speed * _directionVector.y + this.getY());
    }

    public int getShootingFrequenz() {
        return _shootingFrequenz;
    }
}

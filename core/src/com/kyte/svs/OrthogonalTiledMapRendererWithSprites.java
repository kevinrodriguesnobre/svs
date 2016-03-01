package com.kyte.svs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

    public OrthogonalTiledMapRendererWithSprites(TiledMap map)
    {
        super(map);
    }

    @Override
    public void renderObject(MapObject object) {
        if(object instanceof TextureMapObject) {
            TextureMapObject textureObj = (TextureMapObject) object;
            //this.getBatch().draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY());
            this.getBatch().draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY(), textureObj.getOriginX(), textureObj.getOriginY(),
                    32f, 32f, textureObj.getScaleX(), textureObj.getScaleY(), textureObj.getRotation());
        }
    }
}

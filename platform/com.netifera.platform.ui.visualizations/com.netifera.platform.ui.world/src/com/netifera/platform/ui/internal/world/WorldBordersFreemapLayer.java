/*
Copyright (C) 2001, 2008 United States Government
as represented by the Administrator of the
National Aeronautics and Space Administration.
All Rights Reserved.
*/
package com.netifera.platform.ui.internal.world;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.avlist.AVListImpl;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.BasicTiledImageLayer;
import gov.nasa.worldwind.util.LevelSet;
import gov.nasa.worldwind.util.Tile;
import gov.nasa.worldwind.util.TileUrlBuilder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Patrick Murris
 * @version $Id$
 */
public class WorldBordersFreemapLayer extends BasicTiledImageLayer
{
    public WorldBordersFreemapLayer()
    {
        super(makeLevels(new URLBuilder()));
        this.setUseTransparentTextures(true);
    }

    private static LevelSet makeLevels(URLBuilder urlBuilder)
    {
        AVList params = new AVListImpl();

        params.setValue(AVKey.TILE_WIDTH, 512);
        params.setValue(AVKey.TILE_HEIGHT, 512);
        params.setValue(AVKey.DATA_CACHE_NAME, "Earth/WorldBorders_Freemap");
        params.setValue(AVKey.SERVICE, "http://world.freemap.in/cgi-bin/mapserv");
        params.setValue(AVKey.DATASET_NAME, "factbook");
        params.setValue(AVKey.FORMAT_SUFFIX, ".dds");
        params.setValue(AVKey.NUM_LEVELS, 12);
        params.setValue(AVKey.NUM_EMPTY_LEVELS, 0);
        params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, new LatLon(Angle.fromDegrees(36d), Angle.fromDegrees(36d)));
        params.setValue(AVKey.SECTOR, Sector.FULL_SPHERE);
        params.setValue(AVKey.TILE_URL_BUILDER, urlBuilder);

        return new LevelSet(params);
    }

    private static class URLBuilder implements TileUrlBuilder
    {
        public URL getURL(Tile tile, String imageFormat) throws MalformedURLException
        {
            StringBuffer sb = new StringBuffer(tile.getLevel().getService());
            if (sb.lastIndexOf("?") != sb.length() - 1)
                sb.append("?");
            sb.append("map=/www/freemap.in/world/map/factbooktrans.map");
            sb.append("&request=GetMap");
            sb.append("&layers=");
            sb.append(tile.getLevel().getDataset());
            sb.append("&srs=EPSG:4326");
            sb.append("&width=");
            sb.append(tile.getLevel().getTileWidth());
            sb.append("&height=");
            sb.append(tile.getLevel().getTileHeight());

            Sector s = tile.getSector();
            sb.append("&bbox=");
            sb.append(s.getMinLongitude().getDegrees());
            sb.append(",");
            sb.append(s.getMinLatitude().getDegrees());
            sb.append(",");
            sb.append(s.getMaxLongitude().getDegrees());
            sb.append(",");
            sb.append(s.getMaxLatitude().getDegrees());

            sb.append("&format=image/png");
            sb.append("&version=1.1.1");
            sb.append("&transparent=true");

            return new java.net.URL(sb.toString());
        }
    }

    @Override
    public String toString()
    {
        //return Logging.getMessage("layers.Earth.WorldBorders.Name");
        return "World Borders";
    }
}

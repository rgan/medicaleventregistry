package org.healthapps.map {

import com.google.maps.LatLng;
import com.google.maps.LatLngBounds;
import com.google.maps.Map;
import com.google.maps.MapType;
import com.google.maps.controls.PositionControl;
import com.google.maps.controls.ZoomControl;
import com.google.maps.overlays.Marker;
import com.google.maps.overlays.Polygon;
import com.google.maps.overlays.PolygonOptions;
import com.google.maps.styles.FillStyle;
import com.google.maps.styles.StrokeStyle;

import mx.controls.TextInput;
import mx.events.ItemClickEvent;

public class MapHelper {

    private var mapMode:int = 0;
    private var INIT_ZOOM_LEVEL:int = 1;
    private var DETAIL_ZOOM_LEVEL:int = 6;
    private var map:Map;

    public function MapHelper(map:Map) {
        this.map = map;
        this.map.setCenter(new LatLng(0, 0), INIT_ZOOM_LEVEL, MapType.NORMAL_MAP_TYPE);
        this.map.addControl(new ZoomControl());
        this.map.addControl(new PositionControl());
    }

    public function clearMap():void {
        if (map) {
            map.clearOverlays();
        }
    }

    public function displayOnMap(vertices:Array):void {
        var zoomLevel:int = DETAIL_ZOOM_LEVEL;
        var center:LatLng;
        if (vertices.length == 1) {
            center = new LatLng(vertices[0].lat, vertices[0].lon)
            var marker:Marker = new Marker(center);
            map.addOverlay(marker);
        } else {
            center = new LatLngBounds(new LatLng(vertices[1].lat, vertices[1].lon),
                    new LatLng(vertices[0].lat, vertices[0].lon)).getCenter();
            var polygon:Polygon = new Polygon([
                new LatLng(vertices[0].lat, vertices[0].lon),
                new LatLng(vertices[0].lat, vertices[1].lon),
                new LatLng(vertices[1].lat, vertices[1].lon),
                new LatLng(vertices[1].lat, vertices[0].lon),
                new LatLng(vertices[0].lat, vertices[0].lon)
            ],
                    new PolygonOptions({
                        strokeStyle: new StrokeStyle({
                            color: 0xff0000,
                            thickness: 10,
                            alpha: 0.7}),
                        fillStyle: new FillStyle({
                            color: 0xff0000,
                            alpha: 0.7})
                    }));
            map.addOverlay(polygon);
        }
        if (zoomLevel > DETAIL_ZOOM_LEVEL) {
            zoomLevel = DETAIL_ZOOM_LEVEL;
        }
        map.setCenter(center, zoomLevel);
    }

    public function populateFieldsAndDrawMarker(latLng:LatLng, latField:TextInput, lonField:TextInput):void {
        if (mapMode == 1) {
            if (latField != null) {
                latField.text = latLng.lat().toString();
                lonField.text = latLng.lng().toString();
            }
            var marker:Marker = new Marker(latLng);
            map.addOverlay(marker);
        }
    }

    public function fullExtent():void {
        if (map) {
            this.map.setCenter(new LatLng(0, 0), INIT_ZOOM_LEVEL, MapType.NORMAL_MAP_TYPE);
        }
    }

    public function handleMapButtonBarClick(event:ItemClickEvent):void {
        mapMode = 0;
        if (event.index == 0) {
            fullExtent();
        }
        if (event.index == 1) {
            clearMap();
        }
        if (event.index == 2) {
            mapMode = 1;
        }
    }

}
}

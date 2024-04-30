/**
 * MozAtes Map Core js
 * @param elementId
 * @param center_lng
 * @param center_lat
 * @param loadedGeoCodeCallback
 * @param useGeoLocation
 * @param isInitDrawCenterMarker
 * @returns {MozatesPoliceMap}
 * @constructor
 */
const MozAtesPoliceMap = function({elementId, center_lng, center_lat,loadedGeoCodeCallback, useGeoLocation = false ,isInitDrawCenterMarker}){
    const _core = this;
    const _pbkey = "pk.eyJ1IjoiZGVzaW1pbjIiLCJhIjoiY2xvbzMwN2t3Mm52dzJrcXR6em5lZ3hmMyJ9.pu7IdtCJVHme2QXzu4sT7w";
    let _center = center_lng && center_lat ? [center_lng, center_lat] : [32.609310,-25.907068];
    let _map = null;
    let _userLng = null;
    let _userLat = null;
    let _geoLocationTrigger = useGeoLocation;
    let _geoLocate = null;
    let _centerMarker = null;
    let _loadingCoverRoot = null;

    function handlePermission() {
        navigator.permissions.query({name:'geolocation'}).then(function(result) {
            if (result.state === 'granted') {
                getGelocation();
            } else if (result.state === 'prompt') {
                report(result.state);
                getGelocation();
            } else if (result.state === 'denied') {
                report(result.state);
            }
            result.addEventListener('change', function() {
                report(result.state);
            });
        });
    }
    function report(state) {
        console.log('Permission ' + state);
    }
	// handlePermission();
    // Geolocation API에 액세스할 수 있는지를 확인
    function getGelocation(){
        if (navigator.geolocation) {
            //위치 정보를 얻기
            navigator.geolocation.getCurrentPosition (function(pos) {
                if(typeof pos == "undefined") {
                    alert("Not support geolocation.");
                    return
                }
                _userLat = pos.coords.latitude;
                _userLng = pos.coords.longitude;
                _center = [pos.coords.longitude,pos.coords.latitude];
            });
        } else {
//			alert("Not support geolocation.")
        }
    }
    function mapLoadingCover(){
        if(_loadingCoverRoot) _loadingCoverRoot.remove();
        let msg = "Your location is being tracked.";
        const element = document.getElementById(elementId);
        element.style.position = "relative";
        const cover = `<div style="position:absolute;z-index:2;left:0;top:0;bottom:0;right:0;background:rgba(0,0,0,0.5)">
            <p style="position:absolute;left:0;right:0;text-align:center;top:50%;transform: translateY(-50%);color:white;">${msg}</p>
        </div>`;
        _loadingCoverRoot = document.createElement('div');
        _loadingCoverRoot.innerHTML = cover;
        element.appendChild(_loadingCoverRoot);
    }
    // if(_geoLocationTrigger) getGelocation();

    _core.addGeolocate = function(){
        let geolocate = new mapboxgl.GeolocateControl({
            positionOptions: {
                enableHighAccuracy: true
            },
            trackUserLocation: true,
            showUserHeading: false
        })
        /*geolocate._updateCamera = (e) => {
            _map.flyTo({
                center : [e.coords.longitude, e.coords.latitude],
                duration : 1500,
                essential : true
            });
        }*/
        _map.addControl(geolocate, "bottom-right");
        geolocate.on('geolocate', (event) => {
            _loadingCoverRoot.remove();
            _userLng = event.coords.longitude;
            _userLat = event.coords.latitude;
            const loadGeocodeData = _core.getGeoCodeReverse(_userLng, _userLat);
            console.log("geolocation tracking");
        });

        geolocate.on('trackuserlocationstart', (event) => {
            console.log('A trackuserlocationstart event has occurred.');
            _centerMarker?.remove();
            _centerMarker = null;
        });

        geolocate.on('trackuserlocationend', (event) => {
            console.log('A trackuserlocationend event has occurred.');
            if(!_centerMarker){
                _core.drawCenterMaker();
            }
        });
        return geolocate;
    }

    _core.init = function(){
        const element = document.getElementById(elementId);
        if(!element) {
            console.error("Not found element.");
            return;
        }
        mapboxgl.accessToken = _pbkey;
        window.mbox = _map = new mapboxgl.Map({
            container: elementId, // container ID
            style: 'mapbox://styles/mapbox/streets-v12', // style URL
            center: _center, // starting position [lng, lat]
            zoom: 13, // starting zoom
        });
        if(_geoLocationTrigger) _geoLocate = _core.addGeolocate();
        _map.on('load', function() {
            // user location tracking
            if(_geoLocationTrigger) {
                mapLoadingCover();
                _geoLocate?.trigger();
            }
            if(isInitDrawCenterMarker) {
                _core.drawMarker([center_lng, center_lat]);
            }
        });
        _map.on('movestart', function(e) {
            _centerMarker?.setLngLat(_map.getCenter());
        });

        _map.on('move', function(e) {
            _centerMarker?.setLngLat(_map.getCenter());
        });

        _map.on('moveend', async function(e) {
            const center = _map.getCenter();
            _centerMarker?.setLngLat(center);
            await _core.getGeoCodeReverse(center.lng,center.lat);
            _userLng = center.lng;
            _userLat = center.lat;
        });
    }
    _core.drawCenterMaker = function(){
        const centerLngLat = _map.getCenter();
        _centerMarker = _core.drawMarker(centerLngLat);
    };
    _core.drawMarker = function(lngLat){
        return new mapboxgl.Marker({ color: 'red'})
            .setLngLat(lngLat)
            .addTo(_map);
    }
    _core.getLng = function(){
        return _userLng;
    }

    _core.getLat = function(){
        return _userLat;
    }
    _core.getGeolocationControl = function(){
        return _geoLocate;
    }
    _core.getGeoCodeReverse = function(lng,lat){
        if(!lng || !lat) return;
        /*const loading = MozAtesPoliceApp.loading("Loading street name by your location.").start();*/
        return new Promise(function(resolve, reject){
            fetch(`https://api.mapbox.com/v4/mapbox.mapbox-streets-v8/tilequery/${lng},${lat}.json?radius=25&limit=5&dedupe&geometry=linestring&layers=road&access_token=${_pbkey}`,{
                "Content-Type": "application/json",
            })
                .then((response) => response.json())
                .then((data) => {
                    if(typeof loadedGeoCodeCallback === "function") loadedGeoCodeCallback(_core, data);
                    resolve(data);
            }).catch((e) => {
                console.log("Geo Code API Error");
                reject(e);
            })
                /*.finally(() => loading?.end())*/
        });
    }
    _core.init();


    /*Util*/
    _core.util = {
        refineStreetName : function(features) {
            let streetList = [];
            const refineList = features.filter((obj) => obj.properties?.name);

            streetList = refineList.reduce((prev, cur) => {
                const name = cur.properties?.name;
                const ref = cur.properties?.ref;
                const streetName = name+(ref ? " "+ref : "");
                if(prev.indexOf(streetName) === -1) prev.push(streetName);
                return prev;
            }, []);
            return streetList;
        }
    }
    return _core;
};
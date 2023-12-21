<template>
  <div style="position: relative; height: 100%">
    <div id="map-container"></div>
  </div>
</template>
<script>
import AMapLoader from "@amap/amap-jsapi-loader";
export default {
  name: "amap",
  props: {
    locationInfo: {
      type: Object,
      default: "",
    },
  },
  data() {
    return {
      map: null,
      marker: null,
      geocoder: null,
      geobtn: null,
      locationMaker: null,
      showMarkInfo: false,

    };
  },
  watch: {
    locationInfo: {
      handler(val) {
        let e = new AMap.LngLat(val.lng, val.lat);
        this.marker = new AMap.Marker({
          position: e,
        });      
        this.map.add(this.marker);
      },
      deep: true,
    },
  },
  created() {},
  mounted() {
    this.init();
  },
  methods: {
    init() {
      AMapLoader.load({
        key: "3780324eff112196945498dbf596caf0", // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
        AMapUI: {
          version: "1.1",
          plugins: ["overlay/SimpleMarker"],
        },
      })
        .then((AMap) => {
          let _this = this;
          if (!!_this.locationInfo) {
            this.map = new AMap.Map("map-container", {
              zoom: 10,
              center: new AMap.LngLat(_this.locationInfo.lng, _this.locationInfo.lat),
            });
          } else {
            this.map = new AMap.Map("map-container", {
              zoom: 15,
            });
          }
       
          //比例尺
          AMap.plugin("AMap.Scale", function () {
            var scale = new AMap.Scale();
            _this.map.addControl(scale);
          });

          if (!!_this.locationInfo) {
            var lnglat = new AMap.LngLat(_this.locationInfo.lng, _this.locationInfo.lat);
            _this.marker = new AMap.Marker({
              position: lnglat,
            });
            _this.map.add(_this.marker);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>
<style>
#map-container {
  height: 100%;
  width: 100%;
}

.map-serach-controll {
  position: absolute;
  top: 30px;
  left: 30px;
  display: flex;
}

.auto-item {
  padding: 8px 18px;
  font-size: 15px;
  color: #191f25;
  line-height: 18px;
  border-bottom: 1px solid rgba(17, 31, 44, 0.08);
  min-width: 268px;
  max-width: 300px;
  box-sizing: border-box;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  z-index: 3000;
}

.auto-item-span {
  display: block;
  font-size: 13px;
  padding-left: 0;
  color: rgba(25, 31, 37, 0.56);
  line-height: 16px;
}

.map-mark-info-title {
  margin-bottom: 5px;
  width: 100%;
  font-size: 15px;
}

.map-mark-info-content {
  font-size: 13px;
  color: rgba(25, 31, 37, 0.56);
  line-height: 16px;
  width: 100%;
}

.map-mark-info-container {
  padding: 8px 18px;
  position: absolute;
  top: 75px;
  left: 30px;
  width: 220px;
  background-color: #fff;
}

.amap-sug-result {
  z-index: 3001;
  border: 0px !important;
}

.amap-geolocation {
  position: absolute;
  bottom: 20px;
  right: 10px;
  background-color: #fff;
  height: 32px;
  width: 32px;
  border-radius: 50%;
  box-shadow: 0 0 5px silver;
  cursor: pointer;
  background-image: url(https://a.amap.com/jsapi/static/image/plugin/locate.png);
  background-size: 24px;
  background-repeat: no-repeat;
  background-position: 50%;
}
.custom-location img {
  height: 24px;
  width: 24px;
  border-radius: 50%;
  animation: change 2s linear infinite;
  /* background-image: url('/image/mapicon/location.png'); */
  background-repeat: no-repeat;
  border: none;
  background-position: 50%;
}
.amap-geolocation img {
  height: 24px;
  width: 24px;
  background-color: #fff;
  margin: 4px;
  border-radius: 50%;
  animation: rotate 2s linear infinite;
}
@keyframes change {
  0% {
    transform: scale(1);
  }
  25% {
    transform: scale(1.1);
  }
  50% {
    transform: scale(1.3);
  }
  75% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes rotate {
  0% {
    transform: rotate(0deg);
  }
  25% {
    transform: rotate(90deg);
  }
  50% {
    transform: rotate(180deg);
  }
  75% {
    transform: rotate(270deg);
  }
  100% {
    transform: rotate(1turn);
  }
}
</style>

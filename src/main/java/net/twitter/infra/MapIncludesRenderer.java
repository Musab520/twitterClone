package net.twitter.infra;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.vue.VueRenderer;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MapIncludesRenderer extends VueRenderer {

    private static final String MAP_INCLUDES;
    private static final String COMPONENT_SETUP;

    static {
        String[] includes = new String[]{
                "<link rel=\"stylesheet\" href=\"//unpkg.com/leaflet/dist/leaflet.css\" />",
                "<script src=\"//cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js\"></script>",
                "<script src=\"//unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>",
                "<script src=\"//cdnjs.cloudflare.com/ajax/libs/leaflet.draw/1.0.4/leaflet.draw.js\"></script>",
                "<script src=\"//unpkg.com/vue2-leaflet@2.6.0/dist/vue2-leaflet.min.js\"></script>",
                "<link rel=\"stylesheet\" href=\"//cdnjs.cloudflare.com/ajax/libs/leaflet.draw/1.0.4/leaflet.draw-src.css\"/>",
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.css\" />",
                "<script src=\"https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js\"></script>"
        };
        MAP_INCLUDES = String.join("\n", includes).concat("\n@componentRegistration");

    }

    static {
        String[] mapComponents = {
                "Vue.component('l-map', window.Vue2Leaflet.LMap);",
                "Vue.component('l-tile-layer', window.Vue2Leaflet.LTileLayer);",
                "Vue.component('l-control-layers',window.Vue2Leaflet.LControlLayers);",
                "Vue.component('l-marker', window.Vue2Leaflet.LMarker);",
                "Vue.component('l-polygon', window.Vue2Leaflet.LPolygon);",
                "Vue.component('l-popup', window.Vue2Leaflet.LPopup);",
                "Vue.component('l-control', window.Vue2Leaflet.LControl);"
        };
        COMPONENT_SETUP = "<body>\n<script>".concat(String.join("\n", mapComponents)).concat("</script>\n");
    }

    @Override
    public String preRender(String layout, Context ctx) {
        return layout.replace("@componentRegistration", MAP_INCLUDES)
                .replace("<body>", COMPONENT_SETUP);
    }
}

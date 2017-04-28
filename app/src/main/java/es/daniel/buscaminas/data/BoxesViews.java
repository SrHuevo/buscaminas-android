package es.daniel.buscaminas.data;

import java.util.HashMap;
import java.util.Map;

public class BoxesViews {
    private final Map<Integer, Map<Integer, BoxView>> boxViewsByCoor;
    private final int width;
    private final int height;


    public BoxesViews(int width, int height) {
        this.width = width;
        this.height = height;
        boxViewsByCoor = new HashMap<>(width);
    }

    public void add(int x, int y, BoxView boxView) {
        if(!boxViewsByCoor.containsKey(x)) {
            boxViewsByCoor.put(x, new HashMap<Integer, BoxView>(height));
        }
        boxViewsByCoor.get(x).put(y, boxView);
    }

    public BoxView get(int x, int y) {
        return boxViewsByCoor.get(x).get(y);
    }
}

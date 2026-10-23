package pt.rotarapida.waze;

import org.junit.Test;
import static org.junit.Assert.*;

public class WidgetLayoutModeTest {
    @Test public void tilesStaySquareAndFitWithinTheWidget() {
        int side = WidgetLayoutMode.squareTileSideDp(420, 140);
        assertEquals(124, side);
        assertEquals(384, WidgetLayoutMode.actionRowWidthDp(side));
        assertEquals(97, WidgetLayoutMode.squareTileSideDp(320, 200));
        assertEquals(80, WidgetLayoutMode.squareTileSideDp(320, 96));
        assertEquals(60, WidgetLayoutMode.squareTileSideDp(208, 76));
    }

    @Test public void invalidOrTooSmallDimensionsDoNotProduceNegativeSizes() {
        assertEquals(0, WidgetLayoutMode.squareTileSideDp(0, 0));
        assertEquals(0, WidgetLayoutMode.squareTileSideDp(20, 20));
        assertEquals(0, WidgetLayoutMode.actionRowWidthDp(0));
    }
}

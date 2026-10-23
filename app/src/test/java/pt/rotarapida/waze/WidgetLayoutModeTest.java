package pt.rotarapida.waze;

import org.junit.Test;
import static org.junit.Assert.*;

public class WidgetLayoutModeTest {
    @Test public void choosesWideLayoutOnlyWhenThereIsRoom() {
        assertTrue(WidgetLayoutMode.useWideLayout(420, 110));
        assertFalse(WidgetLayoutMode.useWideLayout(359, 110));
        assertFalse(WidgetLayoutMode.useWideLayout(420, 99));
        assertFalse(WidgetLayoutMode.useWideLayout(0, 0));
    }
}

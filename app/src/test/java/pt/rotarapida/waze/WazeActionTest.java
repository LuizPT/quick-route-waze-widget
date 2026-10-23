package pt.rotarapida.waze;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

public class WazeActionTest {
    @Test public void homeAndWorkUseTheOfficialFavoriteDeepLinks() {
        assertEquals("https://waze.com/ul?favorite=home&navigate=yes", WazeAction.HOME.deepLink());
        assertEquals("https://waze.com/ul?favorite=work&navigate=yes", WazeAction.WORK.deepLink());
    }

    @Test public void appActionsDoNotInventDeepLinks() {
        assertNull(WazeAction.OPEN_WAZE.deepLink());
    }

    @Test public void everyActionHasAUniqueKeyAndPendingIntentCode() {
        Set<String> keys = new HashSet<>();
        Set<Integer> codes = new HashSet<>();
        for (WazeAction action : WazeAction.values()) {
            assertTrue(keys.add(action.key()));
            assertTrue(codes.add(action.requestCode()));
        }
        assertEquals(3, keys.size());
    }

    @Test public void unknownWidgetActionFallsBackToOpenWaze() {
        assertSame(WazeAction.OPEN_WAZE, WazeAction.fromKey("unknown"));
        assertSame(WazeAction.OPEN_WAZE, WazeAction.fromKey(null));
        assertSame(WazeAction.OPEN_WAZE, WazeAction.fromKey("favorites"));
    }
}

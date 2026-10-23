package pt.rotarapida.waze;

/** Size decision shared by widget rendering and its JVM tests. */
public final class WidgetLayoutMode {
    private WidgetLayoutMode() { }

    public static boolean useWideLayout(int minWidthDp, int minHeightDp) {
        return minWidthDp >= 360 && minHeightDp >= 100;
    }
}

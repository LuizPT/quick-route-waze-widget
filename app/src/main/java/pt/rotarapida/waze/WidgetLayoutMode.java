package pt.rotarapida.waze;

/** Square-tile sizing shared by widget rendering and its JVM tests. */
public final class WidgetLayoutMode {
    private WidgetLayoutMode() { }

    public static int squareTileSideDp(int widgetWidthDp, int widgetHeightDp) {
        if (widgetWidthDp <= 0 || widgetHeightDp <= 0) return 0;
        int availableWidth = widgetWidthDp - 16 - 12;
        int availableHeight = widgetHeightDp - 16;
        return Math.max(0, Math.min(availableWidth / 3, availableHeight));
    }

    public static int actionRowWidthDp(int tileSideDp) {
        return tileSideDp <= 0 ? 0 : tileSideDp * 3 + 12;
    }
}

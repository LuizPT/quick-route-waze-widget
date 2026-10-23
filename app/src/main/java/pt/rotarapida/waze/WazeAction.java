package pt.rotarapida.waze;

/** The three navigation actions exposed by the app and its home-screen widget. */
public enum WazeAction {
    HOME(101, "home"),
    WORK(102, "work"),
    OPEN_WAZE(104, "open");

    private final int requestCode;
    private final String key;

    WazeAction(int requestCode, String key) {
        this.requestCode = requestCode;
        this.key = key;
    }

    public int requestCode() {
        return requestCode;
    }

    public String key() {
        return key;
    }

    public String deepLink() {
        switch (this) {
            case HOME:
                return "https://waze.com/ul?favorite=home&navigate=yes";
            case WORK:
                return "https://waze.com/ul?favorite=work&navigate=yes";
            default:
                return null;
        }
    }

    public static WazeAction fromKey(String key) {
        if (key != null) {
            for (WazeAction action : values()) {
                if (action.key.equals(key)) return action;
            }
        }
        return OPEN_WAZE;
    }
}

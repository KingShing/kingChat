package sit.kingshing.common.airpanel;

public interface AirPanel {

    interface Sub {
        void openPanel();

        void closePanel();

        boolean isOpen();
    }

    interface Boss extends Sub {
        void setup(PanelListener panelListener);

        void setOnStateChangedListener(AirPanel.OnStateChangedListener listener);
    }

    interface PanelListener {
        void requestHideSoftKeyboard();
    }

    interface OnStateChangedListener {
        void onPanelStateChanged(boolean isOpen);

        void onSoftKeyboardStateChanged(boolean isOpen);
    }
}

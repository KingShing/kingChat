package sit.kingshing.common.airpanel;

import android.app.Activity;



interface Contract extends AirPanel.Boss {
    interface Panel extends AirPanel.Boss {
        void adjustPanelHeight(int heightMeasureSpec);
    }

    interface Helper extends Contract.Panel, AirPanel.PanelListener {
        int calculateHeightMeasureSpec(int heightMeasureSpec);

        void setup(Activity activity);
    }
}

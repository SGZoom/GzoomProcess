package process.gzoom.com.appapi;

import android.app.Activity;
import android.view.View;

/**
 * Created by xujiayunew on 2017/10/5.
 *
 * 默认的activity的查找者
 */

public class ActivityViewFinder implements ViewFinder {
    @Override
    public View findView(Object object, int id) {
        return ((Activity) object).findViewById(id);
    }
}


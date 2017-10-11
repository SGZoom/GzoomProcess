package process.gzoom.com.appapi;

import android.view.View;

/**
 * Created by xujiayunew on 2017/10/5.
 *
 * 定义一个被绑定者查找view的接口
 */

public interface ViewFinder {
    View findView(Object object, int id);

}

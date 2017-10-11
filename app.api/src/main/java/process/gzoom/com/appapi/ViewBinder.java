package process.gzoom.com.appapi;

/**
 * Created by xujiayunew on 2017/10/5.
 *
 * UI绑定接口
 */

public interface ViewBinder<T> {
    void bindView( T host, Object object, ViewFinder finder);

    void unBindView(T host);
}

package mn.example.galttereg.client.manager;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import mn.example.galttereg.client.listener.DBSDataProviderListener;
import mn.example.galttereg.client.request.Request;
import mn.example.galttereg.client.response.Response;
import mn.example.galttereg.client.util.TestBehavior;
import mn.example.galttereg.client.util.TestBehaviorHelper;


public class CustomAssetsFileDBSDataProvider extends AssetsFileDBSDataProvider {

    private static final long DEFAULT_DELAY = 300;
    protected Handler handler = new Handler();
    protected TestBehaviorHelper testBehaviorHelper = getNewTestBehaviorHelper();
    private List<TestBehavior> testBehaviors;
    private Context context;

    public CustomAssetsFileDBSDataProvider(Context context) {
        super(context);
        this.context = context;
    }

    protected long getDelay() {
        return DEFAULT_DELAY;
    }

    @Override
    public void execute(final Request request, final DBSDataProviderListener listener) {
        executeWithDelay(request, listener, getDelay());
    }

    public void executeWithDelay(final Request request, final DBSDataProviderListener listener, final long delay) {
        Runnable delayedRunnable = new Runnable() {
            @Override
            public void run() {
                handleTestBehavior(request, new TestBehaviorHelper.NormalFlowHandler() {
                    @Override
                    public void continueNormalFlow(Request request, DBSDataProviderListener listener) {
                        CustomAssetsFileDBSDataProvider.super.execute(request, listener);
                    }
                }, listener);
            }
        };
        handler.postDelayed(delayedRunnable, delay);
    }

    protected void handleTestBehavior(final Request request, final TestBehaviorHelper.NormalFlowHandler normalFlowHandler, final DBSDataProviderListener listener) {
        if (testBehaviors == null) {
            testBehaviors = testBehaviorHelper.getTestBehaviors(context);
        }
        testBehaviorHelper.triggerTestBehavior(request, testBehaviors, new TestBehaviorHelper.TestBehaviorListener() {
            @Override
            public void callOnSuccessWith(final Response response)  {
                listener.onSuccess(response);
            }

            @Override
            public void callOnErrorWith(final Response errorResponse) {
                listener.onError(errorResponse);
            }

            @Override
            public void continueNormalFlow() {
                normalFlowHandler.continueNormalFlow(request, listener);
            }
        });
    }


    protected TestBehaviorHelper getNewTestBehaviorHelper() {
        return new TestBehaviorHelper();
    }

}

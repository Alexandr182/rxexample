package sample.rxexample.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.rxexample.utils.RxExampleAplication;

/**
 * Created by profiralexandr on 17/08/16.
 */

public class ApiHelper {

    private static ApiHelper sInstance;
    private RxExampleAPI mApi;

    public static RxExampleAPI getApi() {
        if (sInstance == null) {
            sInstance = new ApiHelper();
        }
        return sInstance.mApi;
    }

    private ApiHelper() {
        mApi = createApi();
    }

    private RxExampleAPI createApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.nactem.ac.uk/")
                .client(createClient())
                .addConverterFactory(createGsonFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(RxExampleAPI.class);
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder okHttpBuilder = new okhttp3.OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(interceptor);

        File cacheDir = new File(RxExampleAplication.getInstance().getCacheDir().getAbsolutePath(), RxExampleAplication.getInstance().getPackageName());
        okHttpBuilder.cache(new okhttp3.Cache(cacheDir, 10 * 1024 * 1024));
        return okHttpBuilder.build();
    }

    private GsonConverterFactory createGsonFactory() {
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        return GsonConverterFactory.create(gson);
    }
}

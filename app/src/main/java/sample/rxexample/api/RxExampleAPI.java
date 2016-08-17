package sample.rxexample.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sample.rxexample.model.response.FindAcronymResponse;

/**
 * Created by profiralexandr on 17/08/16.
 */

public interface RxExampleAPI {

    @GET("/software/acromine/dictionary.py")
    Call<List<FindAcronymResponse>> findAcronyms(@Query("sf") String acronym);

    @GET("/software/acromine/dictionary.py")
    Observable<List<FindAcronymResponse>> observeAcronyms(@Query("sf") String acronym);
}

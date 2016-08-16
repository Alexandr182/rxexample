package sample.rxexample.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sample.rxexample.model.response.FindAcronymResponse;

/**
 * Created by profiralexandr on 17/08/16.
 */

public interface RxExampleAPI {

    @GET("/software/acromine/dictionary.py")
    Call<FindAcronymResponse> findAcronyms(@Query("sf") String acronym);
}

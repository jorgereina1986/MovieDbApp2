package com.jorgereina.moviedbapp2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jorgereina.moviedbapp2.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-jorgereina on 5/11/16.
 */
public class ResultsActivity extends AppCompatActivity{

    private static final String BASE_URL = "https://api.themoviedb.org/3/search/movie?query=";
    private static final String API_KEY = "&api_key=fe321b50d58f46c550723750263ad677";
    private String searchQuery;
    private ListView lvMovies;
    private MovieAdapter adapter;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent getIntent = getIntent();
        searchQuery = getIntent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        lvMovies = (ListView)findViewById(R.id.list_view);

        //creating URL
        URL = BASE_URL+searchQuery+API_KEY;

        // start
        new MovieTask().execute(URL);
    }

    public class MovieTask extends AsyncTask<String,Void, List<Movie> > {


        @Override
        protected List<Movie> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                List<Movie> movieModelList = new ArrayList<>();

                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Movie movie = new Movie();
                    movie.setTitle(finalObject.getString("title"));
                    movie.setOverview(finalObject.getString("overview"));
                    movie.setPoster(finalObject.getString("poster_path"));
                    movie.setBackdrop(finalObject.getString("backdrop_path"));
                    movie.setReleaseDate(finalObject.getString("release_date"));


                    // adding the final object in the list
                    movieModelList.add(movie);
                }
                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }

        @Override
        protected void onPostExecute(final List<Movie> result) {
            super.onPostExecute(result);
            if(result != null) {
                adapter = new MovieAdapter(getApplicationContext(), result);
                lvMovies.setAdapter(adapter);

                lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Movie movieModel = result.get(position);
                        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                        intent.putExtra("title", movieModel.getTitle());
                        intent.putExtra("overview", movieModel.getOverview());
                        intent.putExtra("backdrop", movieModel.getBackdrop());
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "You clicked on "+ movieModel.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

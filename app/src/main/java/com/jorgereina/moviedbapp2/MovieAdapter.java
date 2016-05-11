package com.jorgereina.moviedbapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jorgereina.moviedbapp2.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by c4q-jorgereina on 5/11/16.
 */
public class MovieAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> results;

    public MovieAdapter(Context context, List<Movie> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie result = results.get(position);
        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder.title = (TextView) convertView.findViewById(R.id.title_tv);
            holder.overview = (TextView) convertView.findViewById(R.id.overview_tv);
            holder.imageHolder = (ImageView) convertView.findViewById(R.id.poster_iv);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        //setting track name to row view
        holder.title.setText(result.getTitle());
        holder.overview.setText(result.getOverview());

        //setting image to row view
        //image url http://image.tmdb.org/t/p/w185/kBf3g9crrADGMc2AMAMlLBgSm2h.jpg
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+result.getPoster()).placeholder(R.mipmap.ic_launcher).into(holder.imageHolder);

        return convertView;
    }

    static class ViewHolder{
        TextView title;
        TextView overview;
        ImageView imageHolder;
    }
}
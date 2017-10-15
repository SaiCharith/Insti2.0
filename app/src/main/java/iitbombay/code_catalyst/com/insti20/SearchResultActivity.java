package iitbombay.code_catalyst.com.insti20;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultActivity extends AppCompatActivity {

    String[] searchables={"country\nmywish","state","capital","small"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle bundle=getIntent().getExtras();
        String s=bundle.getString("searchword");
        Toast.makeText(SearchResultActivity.this,s,Toast.LENGTH_SHORT).show();

        ListView listView = (ListView) findViewById(R.id.search_results);
        ArrayList<String > Searchables =  new ArrayList<>();
        Searchables.addAll(Arrays.asList(searchables));

        adapter=new ArrayAdapter<String>(
                SearchResultActivity.this,
                android.R.layout.simple_list_item_1,
                Searchables);
        adapter.getFilter().filter(s);
        listView.setAdapter(adapter);

    }
}

package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by charith on 1/10/17.
 */

public class Tab1info extends Fragment {

    private int hostel_no;

    void instantiate(int i){
        hostel_no=i;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1info, container, false);
        ImageView img = container.findViewById(R.id.image1);
        return rootView;
    }
}

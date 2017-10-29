package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
/**
 * Created by charith on 1/10/17.
 */

/**
 *  This is 1st Fragment of the Hostel-activity. This fragment displays necessary information about Mess Secy's
 *  and Hall Managers/Wardens in Relative Layout.
 *  In the parent class while initiating this Fragment the function called instantiate must be called, which
 *  instantiates the hostel_no which is required for getting information of secy and warden for respective hostels.
 */
public class Tab1info extends Fragment {

    /**
     * to hold the name of secy.
     */
    public TextView secyname;

    /**
     * to hold the conatc number of secy.
     */
    public TextView secynumber;

    /**
     * to hold the e-mail id pf secy.
     */
    public TextView secymail;

    /**
     * to hold the name of hall manager/warden.
     */
    public TextView managername;

    /**
     * to hold the contact number of hall manager/warden.
     */
    public TextView managernumber;

    /**
     * to hold the email-id of hall manager/warden.
     */
    public TextView managermail;


    /**
     * to hold the iamge of secy.
     */
    public ImageView imagesecy;

    /**
     * to hold the iamge of hall manager/warden.
     */
    public ImageView imagemanager;

    /**
     * to hold the value of current hostel number.
     */
    int hostel_no;

    /**
     * This method is called when the this Fragment gets created.
     * All the Textviews and Imageviews are set to their respective values by extracting details from a
     * string-array and image-array which is defined in resources
     * @param inflater to fill the fragment with tab1info.xml
     * @param container to get the view
     * @param savedInstanceState not useful
     * @return rootView
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1info, container, false);

        Bundle bundle=getActivity().getIntent().getExtras();
        hostel_no=bundle.getInt("index");

        secyname = rootView.findViewById(R.id.SecyName);
        secynumber = rootView.findViewById(R.id.SecyNumber);
        secymail = rootView.findViewById(R.id.SecyMail);
        imagesecy = rootView.findViewById(R.id.imageS);

        managername = rootView.findViewById(R.id.ManagerName);
        managernumber = rootView.findViewById(R.id.ManagerNumber);
        managermail = rootView.findViewById(R.id.ManagerMail);
        imagemanager = rootView.findViewById(R.id.imageH);

        String secy = getResources().getStringArray(R.array.Secy)[hostel_no];//Array of all secy's
        String [] secyinfo = secy.split(",");//splitting the array at ","
        String Sname = secyinfo[0];
        String Snumber = secyinfo[1];
        String Smail = secyinfo[2];
//
        secyname.setText(Sname);
        secynumber.setText(Snumber);
        secymail.setText(Smail);
        imagesecy.setImageResource((getResources().obtainTypedArray(R.array.ImageS)).getResourceId(hostel_no,0));
//
        String manager = getResources().getStringArray(R.array.Manager)[hostel_no];
        String [] managerinfo = manager.split(",");
        String Mname = managerinfo[0];
        String Mnumber = managerinfo[1];
        String Mmail = managerinfo[2];

        managername.setText(Mname);
        managernumber.setText(Mnumber);
        managermail.setText(Mmail);
        imagemanager.setImageResource((getResources().obtainTypedArray(R.array.ImageH)).getResourceId(hostel_no,0));

        return rootView;
    }
}

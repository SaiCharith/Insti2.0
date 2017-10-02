package iitbombay.code_catalyst.com.insti20;

/**
 * Created by charith on 2/10/17.
 */


import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.support.v4.view.ActionProvider;
import android.widget.Toast;

/**
 * Created by charith on 28/9/17.
 */

public class SortAction extends ActionProvider implements MenuItem.OnMenuItemClickListener{

    private Context mContext;

    public SortAction(Context context){
        super(context);
        mContext=context;
    }
    @Override
    public View onCreateActionView() {
        return null;
    }
    public boolean hasSubMenu(){
        return true;
    }
    public void onPrepareSubMenu(SubMenu submenu){
        submenu.clear();
        submenu.add("sort by hostel").setOnMenuItemClickListener(this);
        MenuItem item1= submenu.getItem(0);
        // item1.setIcon(android.R.drawable.arrow_up_float);
        submenu.add("sort by rating").setOnMenuItemClickListener(this);
        MenuItem item2= submenu.getItem(1);
        //item2.setIcon(android.R.drawable.arrow_down_float);
        submenu.add("sort by likes").setOnMenuItemClickListener(this);
        MenuItem item3= submenu.getItem(2);
        submenu.add("sort by dislikes").setOnMenuItemClickListener(this);
        MenuItem item4= submenu.getItem(3);

    }
    public  boolean onMenuItemClick(MenuItem item){
        if(item.getTitle().equals("sort by hostel")){
            After_login.sortlist(1);
//            Toast.makeText(getContext(),MainActivity.l.get(2).Hostel_name,Toast.LENGTH_SHORT).show();
        }
        else if(item.getTitle().equals("sort by rating")) After_login.sortlist(2);
        else if(item.getTitle().equals("sort by likes")) After_login.sortlist(3);
        else if(item.getTitle().equals("sort by dislikes")) After_login.sortlist(4);
        return true;
    }
}

package iitbombay.code_catalyst.com.insti20;



import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.support.v4.view.ActionProvider;
import android.widget.Toast;

/**
 * This class provides Sort functionality to menu.sort
 * @author Code-Catalyst
 */

public class SortAction extends ActionProvider implements MenuItem.OnMenuItemClickListener{

    /**
     * Sets the context
     */
    private Context mContext;

    /**
     * Constrctor to update the context
     * @param context
     */
    public SortAction(Context context){
        super(context);
        mContext=context;
    }

    /**
     *
     * @return null
     */
    @Override
    public View onCreateActionView() {
        return null;
    }

    /**
     *
     * @return true (indicates presence of sub-menu)
     */
    public boolean hasSubMenu(){
        return true;
    }

    /**
     * Creating sub-menu with
     * <ul>
     *     <li>sort by hostel</li>
     *     <li>sort by rating</li>
     *     <li>sort by likes</li>
     *     <li>sort by dislikes</li>
     * </ul>
     * @param submenu gets submenu
     */
    public void onPrepareSubMenu(SubMenu submenu){
        submenu.clear();
        submenu.add("sort by hostel").setOnMenuItemClickListener(this);
        submenu.add("sort by rating").setOnMenuItemClickListener(this);
        submenu.add("sort by likes").setOnMenuItemClickListener(this);
        submenu.add("sort by dislikes").setOnMenuItemClickListener(this);


    }

    /**
     * Adding functionality to sub-menu
     * differentiating different sub-menus using their titles.
     * @param item brings current Menuitem
     * @return true (informing that function click is successful).
     */
    public  boolean onMenuItemClick(MenuItem item){
        if(item.getTitle().equals("sort by hostel")) After_login.sortlist(1);
        else if(item.getTitle().equals("sort by rating")) After_login.sortlist(2);
        else if(item.getTitle().equals("sort by likes")) After_login.sortlist(3);
        else if(item.getTitle().equals("sort by dislikes")) After_login.sortlist(4);
        return true;
    }
}

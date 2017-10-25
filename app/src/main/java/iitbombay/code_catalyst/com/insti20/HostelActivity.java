package iitbombay.code_catalyst.com.insti20;


import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

/**
 * @author Code-Catalyst
 * This Activity is a swipe-page activity.
 * There are 4 fragments displayed under this activity namely
 * <ul>
 *     <li>tab1info</li>
 *     <li>tab2rate</li>
 *     <li>tab3feedback</li>
 *     <li>tab4menu</li>
 * </ul>
 *
 * SectoinsPagerAdapter displays view of a pirtuclar fragment depending on current position of user
 * A general class that is Instantiated Once The user opens a hostel from {@link After_login} and displays fragmented view of the Hostel Information
 *
 */
public class HostelActivity extends Activity {

    private int hostel_no;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * Method caled when the activity is created.
     * Here mSectionsPagerAdapter, mViewPager, TabLayout are initialized.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);


        Bundle bundle=getIntent().getExtras();
        hostel_no=bundle.getInt("index");


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * here we are inflating the view with menu_hostel.xml
     * @param menu to display contents of menu in menubar
     * @return (boolean) true (informing that menu is present)
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hostel, menu);
        return true;
    }


    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     * @param item
     * @return (boolean)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem is called to instantiate the fragment for the given page.
         * Each fragment is alse instantiated with hostel_no.
         * @param position represents the position of user in one of the 4 tabs
         * @return  Fragment (Fragment at ith position)
         */

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Tab1info tab1 = new Tab1info();
                    tab1.instantiate(hostel_no);
                    return tab1;
                case 1:
                    Tab2Rate tab2 = new Tab2Rate();
                    tab2.instantiate(hostel_no);
                    return tab2;
                case 2:
                    Tab3feedback tab3 = new Tab3feedback();
                    tab3.instantiate(hostel_no);
                    return tab3;
                case 3:
                    Tab4menu tab4 = new Tab4menu();
                    tab4.instantiate(hostel_no);
                    return tab4;
                default:
                    return null;
            }
        }

        /**
         * gets the number of pages to be displayed under this activity.
         * @return 4 (as the requirement is only 4 fragments)
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         *
         * @param position position of tab
         * @return CharSequence which represents the name of the tab.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "INFO";
                case 1:
                    return "RATINGS";
                case 2:
                    return "FEEDBACK";
                case 3:
                    return "MENU";
            }
            return null;
        }
    }
}

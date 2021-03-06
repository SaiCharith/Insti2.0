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
 *
 * This Activity is a swipe-page activity.
 * There are 3 fragments displayed under this activity namely
 * <ul>
 *     <li>likes</li>
 *     <li>dislikes</li>
 *     <li>ratings</li>
 * </ul>
 *
 * SectoinsPagerAdapter is used here which displays view of a pirtuclar fragment depending on current position of user
 *
 * @author Code-Catalyst
 */
public class TabbedActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);
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
     * here menu is being inflated with menu_hostel.xml
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
                    LikeGraph tab1 = new LikeGraph();
                    return tab1;
                case 1:
                    DislikeGraph tab2 = new DislikeGraph();
                    return tab2;
                case 2:
                    RatingGraph tab3 = new RatingGraph();
                    return tab3;

                default:
                    return null;
            }
        }

        /**
         * gets the number of pages to be displayed under this activity.
         * @return 3 (as the requirement is only 3 fragments)
         */
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
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
                    return "Likes";
                case 1:
                    return "Dislikes";
                case 2:
                    return "Ratings";
            }
            return null;
        }
    }
}

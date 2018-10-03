package hawkeye.ena.ba.hawkeye.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hawkeye.ena.ba.hawkeye.R;
import hawkeye.ena.ba.hawkeye.gui.CustomAdapterTime;
import hawkeye.ena.ba.hawkeye.model.Line;

public class AllSchedulesActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    static Line line;
    @SuppressLint("StaticFieldLeak")
    static Context appContext = MainActivity.appContext;
    static int color = 0;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedules);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton logo = (ImageButton) toolbar.findViewById(R.id.imageButton);

        logo.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        line = MainActivity.dataContext.getLineById(id);
        color = intent.getIntExtra("Color",0);
        toolbar1.setTitle(line.getFullLineName());

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_all_schedules, container, false);

            TextView textFrom = (TextView) rootView.findViewById(R.id.textFrom);
            TextView textTo = (TextView) rootView.findViewById(R.id.textTo);

            textFrom.setText(line.getLineFrom());
            textFrom.setTextColor(color);
            textTo.setText(line.getLineTo());
            textTo.setTextColor(color);

            ListView listFrom = (ListView) rootView.findViewById(R.id.listFrom);
            ArrayList<String> timesFrom = new ArrayList<>();
            ArrayList<String> timesTo = new ArrayList<>();

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    timesFrom = line.getTimesFromRD();
                    timesTo = line.getTimesToRD();
                    break;
                case 2:
                    timesFrom = line.getTimesFromSUB();
                    timesTo =line.getTimesToSUB();
                    break;
                case 3:
                    timesFrom = line.getTimesFromNEDIPR();
                    timesTo = line.getTimesToNEDIPR();
                    break;
            }
            CustomAdapterTime customAdapterTimesFrom = new CustomAdapterTime((Activity) appContext, timesFrom);
            listFrom.setAdapter(customAdapterTimesFrom);

            ListView listTo = (ListView) rootView.findViewById(R.id.listTo);
            CustomAdapterTime customAdapterTimesTo = new CustomAdapterTime((Activity) appContext, timesTo);
            listTo.setAdapter(customAdapterTimesTo);

            return rootView;
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return appContext.getString(R.string.radni_dani);
                case 1:
                    return appContext.getString(R.string.subota);
                case 2:
                    return appContext.getString(R.string.nedjelja_i_praznici);
            }
            return null;
        }
    }
}

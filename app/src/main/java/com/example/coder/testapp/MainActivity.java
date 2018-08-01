package com.example.coder.testapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coder.testapp.fragments.FirstFragment;
import com.example.coder.testapp.fragments.FourthFragment;
import com.example.coder.testapp.fragments.SecondFragment;
import com.example.coder.testapp.fragments.ThirdFragment;
import com.example.coder.testapp.model.FragmentTag;
import com.example.coder.testapp.operations.NetworkOperations;
import com.example.coder.testapp.utils.FragmentKeys;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {

    //Tags
    private static final String TAG = "MainActivity";

    //fragments
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;
    private ThirdFragment mThirdFragment;
    private FourthFragment mFourthFragment;

    //vars
    private NetworkOperations operations;
    private List<com.example.coder.testapp.model.Menu> mMenuItemList = new ArrayList<>();
    private ArrayList<String> mFragmentsTags = new ArrayList<>();
    private ArrayList<FragmentTag> mFragments = new ArrayList<>();
    private int mExitCount = 0;

    //widgets
    private NavigationView mNavigationView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        operations = new NetworkOperations(MainActivity.this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        new DownloadTask().execute();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case FragmentKeys.FRAGMENT_ONE:
                item.setCheckable(true);
                Log.d(TAG, "onNavigationItemSelected: Item one tapped...");
                inflateItemOneFragment();
                break;

            case FragmentKeys.FRAGMENT_TWO:
                item.setCheckable(true);
                Log.d(TAG, "onNavigationItemSelected: Item two tapped...");
                inflateItemTwoFragment();
                break;

            case FragmentKeys.FRAGMENT_THREE:
                item.setCheckable(true);
                Log.d(TAG, "onNavigationItemSelected: Item three tapped...");
                inflateItemThreeFragment();
                break;

            case FragmentKeys.FRAGMENT_FOUR:
                item.setCheckable(true);
                Log.d(TAG, "onNavigationItemSelected: Item four tapped...");
                inflateItemFourFragment();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //method for adding menu items
    public void addNewItem(int groupId,int id, int orderId ,String itemName) {
        menu = mNavigationView.getMenu();
        System.out.println(menu);
        menu.add(groupId , id, orderId, itemName);;
        System.out.println("Item " + itemName + " added");
    }


    //dynamic filling of the navDrawer
    @Override
    public void initDrawer(List<com.example.coder.testapp.model.Menu> menus) {
        System.out.println(menus);
        Log.d(TAG, "initDrawer: Initiating");
        if (menus != null) {
            System.out.println(menus.size());
            addNewItem(R.id.one,FragmentKeys.FRAGMENT_ONE,0,menus.get(FragmentKeys.FRAGMENT_ONE).getName());
            addNewItem(R.id.one,FragmentKeys.FRAGMENT_TWO,0,menus.get(FragmentKeys.FRAGMENT_TWO).getName());
            addNewItem(R.id.one,FragmentKeys.FRAGMENT_THREE,0,menus.get(FragmentKeys.FRAGMENT_THREE).getName());
            addNewItem(R.id.one,FragmentKeys.FRAGMENT_FOUR,0,menus.get(FragmentKeys.FRAGMENT_FOUR).getName());
            System.out.println(menus.toString());
        }
    }

    private void getMenuList() {
        mMenuItemList = new ArrayList<>();
        operations.getResponses(mMenuItemList);
        System.out.println("get menu list response " + mMenuItemList);
    }

    private void setFragmentsVisibility(String tagName) {

        for (int i = 0; i < mFragments.size(); i++) {
            if (tagName.equals(mFragments.get(i).getTag())) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(mFragments.get(i).getFragment());
                transaction.commit();
            } else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(mFragments.get(i).getFragment());
                transaction.commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
            int backStackCount = mFragmentsTags.size();
            if (backStackCount > 1) {
                // navigating backwards if the backstack greater then 1, and remove the old top fragment
                String topFragmentTag = mFragmentsTags.get(backStackCount - 1);

                String newTopFragmentTag = mFragmentsTags.get(backStackCount - 2);
                setFragmentsVisibility(newTopFragmentTag);

                mFragmentsTags.remove(topFragmentTag);

                mExitCount = 0;
            } else if (backStackCount == 1){
                String topFragmentTag = mFragmentsTags.get(backStackCount-1);
                if (topFragmentTag.equals(getString(R.string.tag_fragment_first))){
                    mExitCount++;
                    Toast.makeText(this,"one more tap to exit the app",
                            Toast.LENGTH_SHORT).show();
                }else{
                mExitCount++;
                Toast.makeText(this,"one more tap to exit the app",
                        Toast.LENGTH_SHORT).show();
                }
            }
            if (mExitCount >=2) {
                super.onBackPressed();
            }
        }

    //---------------------------FRAGMENTS INFLATING------------------------------------------------

    private void inflateItemOneFragment() {
        if (mFirstFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mFirstFragment).commitAllowingStateLoss();
        }
            mFirstFragment = new FirstFragment();

            Bundle args = new Bundle();
            if (mMenuItemList != null) {
                args.putString("param1", mMenuItemList.get(FragmentKeys.FRAGMENT_ONE).getParam());
                System.out.println("in the bundle goes " + mMenuItemList.get(FragmentKeys.FRAGMENT_ONE).getParam());
                mFirstFragment.setArguments(args);
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mFirstFragment, getString(R.string.tag_fragment_first));
            transaction.commit();
            mFragmentsTags.add(getString(R.string.tag_fragment_first));
            mFragments.add(new FragmentTag(mFirstFragment, getString(R.string.tag_fragment_first)));

        setFragmentsVisibility(getString(R.string.tag_fragment_first));
    }

    private void inflateItemTwoFragment() {
        if (mSecondFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mSecondFragment).commitAllowingStateLoss();
        }
            mSecondFragment = new SecondFragment();

            Bundle args = new Bundle();
            if (mMenuItemList != null) {
                args.putString("param2", mMenuItemList.get(FragmentKeys.FRAGMENT_TWO).getParam());
                System.out.println("in the bundle goes " + mMenuItemList.get(FragmentKeys.FRAGMENT_TWO).getParam());
                mSecondFragment.setArguments(args);
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mSecondFragment, getString(R.string.tag_fragment_second));
            transaction.commit();
            mFragmentsTags.add(getString(R.string.tag_fragment_second));
            mFragments.add(new FragmentTag(mSecondFragment, getString(R.string.tag_fragment_second)));

        setFragmentsVisibility(getString(R.string.tag_fragment_second));
    }

    private void inflateItemThreeFragment() {
        if (mThirdFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mThirdFragment).commitAllowingStateLoss();
        }
            mThirdFragment = new ThirdFragment();

            Bundle args = new Bundle();
            if (mMenuItemList != null) {
                args.putString("param3", mMenuItemList.get(FragmentKeys.FRAGMENT_THREE).getParam());
                System.out.println("in the bundle goes " + mMenuItemList.get(FragmentKeys.FRAGMENT_THREE).getParam());
                mThirdFragment.setArguments(args);
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mThirdFragment, getString(R.string.tag_fragment_third));
            transaction.commit();
            mFragmentsTags.add(getString(R.string.tag_fragment_third));
            mFragments.add(new FragmentTag(mThirdFragment, getString(R.string.tag_fragment_third)));

        setFragmentsVisibility(getString(R.string.tag_fragment_third));
    }

    private void inflateItemFourFragment() {
        if (mFourthFragment != null){
            getSupportFragmentManager().beginTransaction().remove(mFourthFragment).commitAllowingStateLoss();

        }

            mFourthFragment = new FourthFragment();

            Bundle args = new Bundle();
            if (mMenuItemList != null) {
                args.putString("param4", mMenuItemList.get(FragmentKeys.FRAGMENT_FOUR).getParam());
                System.out.println("in the bundle goes " + mMenuItemList.get(FragmentKeys.FRAGMENT_FOUR).getParam());
                mFourthFragment.setArguments(args);
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mFourthFragment, getString(R.string.tag_fragment_fourth));
            transaction.commit();
            mFragmentsTags.add(getString(R.string.tag_fragment_fourth));
            mFragments.add(new FragmentTag(mFourthFragment, getString(R.string.tag_fragment_fourth)));

        setFragmentsVisibility(getString(R.string.tag_fragment_fourth));

    }
    //---------------------------FRAGMENTS INFLATING END--------------------------------------------

    class DownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ...");
            getMenuList();
            return null;
        }


    }

}

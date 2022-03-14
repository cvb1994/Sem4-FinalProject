package com.sem4.music_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sem4.bottomnavigationlib.SpaceItem;
import com.sem4.bottomnavigationlib.SpaceNavigationView;
import com.sem4.bottomnavigationlib.SpaceOnClickListener;
import com.sem4.music_app.R;
import com.sem4.music_app.activity.AllMusicActivity;
import com.sem4.music_app.activity.MainActivity;

public class FragmentDashBoard extends Fragment {

    static SpaceNavigationView spaceNavigationView;
    private FragmentManager fm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setHasOptionsMenu(true);

        fm = getActivity().getSupportFragmentManager();

        spaceNavigationView = rootView.findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.home), R.mipmap.ic_home_bottom));
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.chart), R.drawable.ic_chart));
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.artist), R.mipmap.ic_artist));
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.albums), R.mipmap.albums));

        FragmentHome f1 = new FragmentHome();
        loadFrag(f1, getString(R.string.home));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent intent = new Intent(getActivity(), AllMusicActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        FragmentHome f1 = new FragmentHome();
                        loadFrag(f1, getString(R.string.home));
                        break;
                    case 1:
                        FragmentChart fchart = new FragmentChart();
                        loadFrag(fchart, getString(R.string.chart));
                        break;
                    case 2:
                        FragmentArtist fart = new FragmentArtist();
                        loadFrag(fart, getString(R.string.artist));
                        break;
                    case 3:
                        FragmentAlbums falb = new FragmentAlbums();
                        loadFrag(falb, getString(R.string.albums));
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

        return rootView;
    }

    public void loadFrag(Fragment f1, String name) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (name.equals(getString(R.string.search))) {
            ft.hide(fm.getFragments().get(fm.getBackStackEntryCount()));
            ft.add(R.id.fragment_dash, f1, name);
            ft.addToBackStack(name);
        } else {
            ft.replace(R.id.fragment_dash, f1, name);
        }
        ft.commit();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
    }
}
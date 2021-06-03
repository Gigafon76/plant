package space.example.myapplication.Support;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import space.example.myapplication.R;
import space.example.myapplication.Util.FilePaths;
import space.example.myapplication.Util.FileSearch;

public class GalleryFragment extends Fragment {
    private static final String TAG = "GalleryFragment";

    //widget
    private GridView gridView;
    private ImageView galleryImage;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;

    //vars
    private ArrayList<String> directories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery,container,false);
        galleryImage = (ImageView) view.findViewById(R.id.galleryImageView);
        gridView = (GridView) view.findViewById(R.id.gridView);
        directorySpinner = (Spinner) view.findViewById(R.id.spinnerDirectory);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directories = new ArrayList<>();
        Log.d(TAG, "onCreateView: started.");

        ImageView shareClose = (ImageView) view.findViewById(R.id.ivCloseShare);
        shareClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the gallery fragment.");
                getActivity().finish();
            }
        });

        TextView nextScreen = (TextView) view.findViewById(R.id.tvNext);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigation to the final share screen.");
            }
        });

        init();

        return view;
    }
    private void init(){
        FilePaths filePath = new FilePaths();
        //check for other folders inside "/storage/emulate/0/pictures "
        if (FileSearch.getDirectoryPaths(filePath.PICTURES)!=null){
            directories = FileSearch.getDirectoryPaths(filePath.PICTURES);
        }
        directories.add(filePath.CAMERA);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,directories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                Log.d(TAG , "onItemSelected: selected" + directories.get(position));

                //setup our image grid for the directory chosen
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}

package edu.westga.android.fileexplorer;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends ListActivity implements View.OnClickListener{

    private static final File ROOT = Environment.getExternalStorageDirectory();

    private EntryAdapter adapter;
    private Toolbar toolBar;
    private File currentfile;
    private ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolBar = findViewById(R.id.my_toolbar);
        this.setActionBar(toolBar);

        this.backbutton = (ImageButton) findViewById(R.id.backbutton);
        this.backbutton.setImageResource(R.drawable.back_icon);
        this.backbutton.setVisibility(View.GONE);
        this.backbutton.setOnClickListener(MainActivity.this);

        this.generate(ROOT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolBar.setTitle("SD");
    }

    private void generate(File dir) {
        List<Entry> dirs = new ArrayList<Entry>();
        List<Entry> files = new ArrayList<Entry>();
        if (dir != null) {
            for (File file : dir.listFiles()) {
                if(file.isDirectory()) {

                    dirs.add(new Entry(file.getName(), file.getAbsolutePath(), true));
                }
                else {
                    files.add(new Entry(file.getName(), file.getAbsolutePath(), false));
                }
            }
            Collections.sort(files);
            Collections.sort(dirs);
            dirs.addAll(files);
            adapter = new EntryAdapter(MainActivity.this, R.layout.entry_view, dirs);
            setListAdapter(adapter);
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l,v,position,id);
        Entry entry = adapter.getItem(position);
        this.currentfile = new File(entry.getPath());
        if (this.currentfile.isDirectory()) {
            if (this.currentfile.listFiles().length == 0) {
                this.setToast("No files to display.");
            } else {
                if (this.currentfile != ROOT) {
                    this.backbutton.setEnabled(true);
                    this.backbutton.setVisibility(View.VISIBLE);
                }
                    toolBar.setTitle(this.currentfile.getName());
                    generate(this.currentfile);
                }
            }
    }

    private void setToast(CharSequence message) {
        int duration = Toast.LENGTH_SHORT;
        Context context = this.getApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backbutton:
                if (this.currentfile.getParentFile().equals(ROOT)) {
                    this.backbutton.setEnabled(false);
                    this.backbutton.setVisibility(View.GONE);
                    toolBar.setTitle("SD");
                    generate(this.currentfile.getParentFile());
                    this.currentfile = this.currentfile.getParentFile();
                } else {
                    generate(this.currentfile.getParentFile());
                    this.currentfile = this.currentfile.getParentFile();
                    toolBar.setTitle(this.currentfile.getName());
                }
                break;

        }
    }
}

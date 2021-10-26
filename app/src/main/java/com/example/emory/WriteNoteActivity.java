package com.example.emory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class WriteNoteActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "sharedPrefs";
    //request key for camera and gallery intent
    private static final int GALLERY_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private ActionList actionList;
    private ArrayList<Action> chosenActions = new ArrayList<>();
    private String icon, date, note, encodedPic;
    private ArrayList<Diary> diaries = new ArrayList<>();
    private Bitmap bitmap;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        getDataFromAddMood();
        handleActionIcons();
        getImage();
        getNoteExpansion();
        saveData();
    }

    //get mood icon from AddMoodActivity
    public void getDataFromAddMood() {
        Intent intent = getIntent();
        icon = intent.getStringExtra("icon");
        date = intent.getStringExtra("date");

        //please find the references on Planner in References List 2 "Get resource Id from name"
        int resourceId = getResources().getIdentifier("com.example.emory:drawable/" + icon, null, null);
        ImageView imageView = findViewById(R.id.iconChosen);
        Drawable drawable = ContextCompat.getDrawable(this, resourceId);
        imageView.setImageDrawable(drawable);
    }

    //change background color of icon clicked
    public void handleActionIcons() {
        actionList = new ActionList(this);
        GridView grid = findViewById(R.id.actionList);
        grid.setAdapter(new ActionList(this, actionList.getAllActions()));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view = grid.getChildAt(position);
                ImageView img = view.findViewById(R.id.imageView);

                //find the drawable needed to change the background
                Drawable normalBackground = ContextCompat.getDrawable(WriteNoteActivity.this, R.drawable.circle);
                Drawable backgroundSelected = ContextCompat.getDrawable(WriteNoteActivity.this, R.drawable.circle_selected);

                //by default, the tag of each element is "unselected"
                /* when user clicks on a button, it will change the tag to "selected",
                change background and add the list of chosen actions*/
                if (img.getTag().equals("unselected")) {
                    img.setBackground(backgroundSelected);
                    img.setTag("selected");
                    chosenActions.add(actionList.getAction(position));
                    return;
                }

                /*
                when user unclicks a button, it will change the tag to "unselected",
                change background and remove the list of chosen actions
                 */
                if (img.getTag().equals("selected")) {
                    img.setBackground(normalBackground);
                    img.setTag("unselected");
                    chosenActions.remove(actionList.getAction(position));
                    return;
                }
            }
        });
    }

    //expand note
    public void getNoteExpansion() {
        ImageButton expandNote = findViewById(R.id.expandNote);
        expandNote.setOnClickListener((View v) -> {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_note_expansion);
            getNote();
            EditText textContent = dialog.findViewById(R.id.noteContent);

            //the text that user is typing before opening dialog is embedded to the note in expansion view
            textContent.setText(note);
            saveNoteExpansion(dialog);
            dialog.show();
        });
    }

    //get text from noteExpansion
    public void saveNoteExpansion(Dialog dialog) {
        FloatingActionButton doneIcon = dialog.findViewById(R.id.doneIcon);
        doneIcon.setOnClickListener((View v) -> {
            EditText textContent = dialog.findViewById(R.id.noteContent);
            String noteExpansion = textContent.getText().toString();

            //embedded the text from the note in dialog expansion view to the note box in this activity
            EditText editText = findViewById(R.id.writeNote);
            editText.setText(noteExpansion);

            dialog.dismiss();
        });
    }

    //get text from small note to expanded note
    public void getNote() {
        EditText editText = findViewById(R.id.writeNote);
        note = editText.getText().toString();
    }

    //get image from gallery of camera
    public void getImage() {
        ImageButton addImage = findViewById(R.id.addPhoto);
        addImage.setOnClickListener((View v) -> {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_import_picture);
            checkBtnClick(dialog);
            dialog.show();
        });
    }

    //check if user choose camera of gallery button
    public void checkBtnClick(Dialog dialog) {
        ImageButton btnCamera = dialog.findViewById(R.id.takePhotoBtn);
        ImageButton btnGallery = dialog.findViewById(R.id.openGalleryBtn);
        btnCamera.setOnClickListener((View v) -> {
            openCamera();
        });
        btnGallery.setOnClickListener((View v) -> {
            openGallery();
        });
    }

    //intent to open camera
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    //intent to open gallery
    public void openGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
        cameraIntent.setType("image/*");
        cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(cameraIntent, GALLERY_REQUEST);
    }

    /*
    on activity result of open camera or gallery
    image is saved get and decode to bitmap and scale to the size we want
    please find the reference on Planner Reference List 1 "Reference for add photo taken by camera"
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                        encodeBitmap();
                        ImageView imageView = findViewById(R.id.photoChosen);
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error loading file", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    encodeBitmap();
                    ImageView imageView = findViewById(R.id.photoChosen);
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(this, "Error loading file", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
        dialog.dismiss();
    }

    //plase find the reference on Planner in References List 1 "Encode/Decode a bitmap to base64"
    public void encodeBitmap() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        byte[] b = output.toByteArray();
        encodedPic = Base64.encodeToString(b, Base64.DEFAULT);
    }

    //save mood, action, note and picture to diary by gson, mood is required type
    public void saveDiary() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String data = sharedPreferences.getString(date, null);

        if (data != null) {
            Type diaryType = new TypeToken<ArrayList<Diary>>() {
            }.getType();
            diaries = gson.fromJson(data, diaryType);
        }

        diaries.add(new Diary(icon, chosenActions, note, encodedPic));
        editor.putString(date, gson.toJson(diaries));
        editor.apply();
    }

    //save all data to shared pref when button clicked and return to EntriesActivity
    public void saveData() {
        FloatingActionButton floatBtn = findViewById(R.id.doneIcon);
        floatBtn.setOnClickListener(view -> {
            getNote();
            saveDiary();
            Intent intent = new Intent(this, EntriesActivity.class);
            startActivity(intent);
        });
    }
}
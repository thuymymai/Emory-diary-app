package com.example.emory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Diary extends Mood {
    private String note;
    private String pic;

    public Diary(String mood, ArrayList<Action> actions, String note, String pic) {
        super(mood, actions);
        this.note = note;
        this.pic = pic;
    }

    public String getNote() {
        if (this.note.isEmpty()) {
            return "Nothing was written...";
        }
        return this.note;
    }

    //please find the reference "Encode/Decode a bitmap to base64" on References box on Planner
    public Bitmap decodePic() {
        Bitmap bitmap = null;
        if (this.pic != null) {
            byte[] decodedByte = Base64.decode(this.pic, 0);
            bitmap = BitmapFactory
                    .decodeByteArray(decodedByte, 0, decodedByte.length);
        }
        return bitmap;
    }

    //please find the reference "Ger resource Id from name" on Reference 2 box on Planner
    public int retrieveMoodIdFromName() {
        try {
            Field field = R.drawable.class.getDeclaredField(super.getMood());
            return field.getInt(field);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}



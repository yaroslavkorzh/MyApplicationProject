package com.example.myapplication;
/**
 * Created by Yaroslav on 03.01.14.
 */
import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.myapplication.import_parser;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class import_timetable extends Activity {
    final String LOG_TAG = "myLogs";

    /** Called when the activity is first created. */
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_timetable);
        String tmp = "";

        try {
            XmlPullParser xpp = prepareXpp();

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                + ", depth = " + xpp.getDepth() + ", attrCount = "
                                + xpp.getAttributeCount());
                        tmp = "";
                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            tmp = tmp + xpp.getAttributeName(i) + " = "
                                    + xpp.getAttributeValue(i) + ", ";
                        }
                        if (!TextUtils.isEmpty(tmp))
                            Log.d(LOG_TAG, "Attributes: " + tmp);
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    XmlPullParser prepareXpp() {
        return getResources().getXml(R.xml.new_timetable);
    }
}


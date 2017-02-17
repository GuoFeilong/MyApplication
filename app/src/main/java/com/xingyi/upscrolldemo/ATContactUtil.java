package com.xingyi.upscrolldemo;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>use like this</pre>
 * <pre>1, call gotoSystemContact</pre>
 * <pre>2, onActivityResult call getContactResult</pre>
 *
 * Created by jsion on 16/5/10.
 */
public class ATContactUtil {
    private static final String TAG = "ATContactUtil";
    private ATContactUtil() {
    }

    public static void gotoSystemContact(final Activity activityContext, int requestCode) {
        Intent moblieIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activityContext.startActivityForResult(moblieIntent, requestCode);
    }

    /**
     * onActivityResult call the method get the contact phone num
     *
     * @param activityContext     activity
     * @param data                data
     * @param phoneNumGetListener phone num callback
     */
    public static void getContactResult(final Activity activityContext, Intent data, final OnPhoneNumGetListener phoneNumGetListener) {
        if (data != null) {
            final Uri contactData = data.getData();
            if (contactData != null) {
                activityContext.getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
                            @Override
                            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                                CursorLoader cursorLoader = new CursorLoader(activityContext);
                                cursorLoader.setUri(contactData);
                                return cursorLoader;
                            }

                            @Override
                            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                                if (data != null && data.moveToFirst()) {
                                    String name = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                    List<String> numbers = getContactPhone(activityContext, data);
                                    if (numbers != null && numbers.size() > 0) {
                                        String[] mPhoneNumbers = new String[numbers.size()];
                                        for (int i = 0; i < numbers.size(); i++) {
                                            String number = numbers.get(i);
                                            String newNumber = number.replaceAll("[^\\d]", "");
                                            mPhoneNumbers[i] = newNumber;
                                        }
                                        if (phoneNumGetListener != null) {
                                            phoneNumGetListener.getPhoneNum(mPhoneNumbers[0]);
                                        }
                                    }
                                    data.close();
                                    // when get data do not forget destoryloader
                                    activityContext.getLoaderManager().destroyLoader(0);
                                }
                            }

                            @Override
                            public void onLoaderReset(Loader<Cursor> loader) {

                            }
                        }

                );
            }
        }

    }


    /**
     * get contact phone
     *
     * @param cursor cursor
     * @return all phone nums
     */
    private static List<String> getContactPhone(Activity activityContext, Cursor cursor) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor phone = null;
        try {
            int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int phoneNum = cursor.getInt(phoneColumn);
            if (phoneNum > 0) {
                int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String contactId = cursor.getString(idColumn);
                phone = activityContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                if (phone != null && phone.moveToFirst()) {
                    for (; !phone.isAfterLast(); phone.moveToNext()) {
                        int index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int typeindex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        int phone_type = phone.getInt(typeindex);
                        String phoneNumber = phone.getString(index).trim();
                        arrayList.add(phoneNumber);
                    }
                }

            }
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        if (phone != null && !phone.isClosed()) {
            phone.close();
        }
        return arrayList;
    }

    public interface OnPhoneNumGetListener {
        void getPhoneNum(String phoneNum);
    }
}

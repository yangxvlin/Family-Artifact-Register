package com.example.family_artifact_register.FoundationLayer.SocialModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();

    private static UserDatabase instance = null;

    // To delete all content and repopulate the database whenever the app is started
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(instance).execute();
                }
            };

    public static UserDatabase getInstance(Context appContext) {
        if (instance == null)
            synchronized (UserDatabase.class) {
                if(instance== null) {
                    instance = Room.databaseBuilder(appContext,
                            UserDatabase.class, "user_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        return instance;
    }

    // populate data: the AsyncTask that delete all data in the DB and populate it with two new users
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDAO mDao;

        PopulateDbAsync(UserDatabase db) {
            mDao = db.getUserDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            // Word word = new Word("Hello");
            User user = new User("1", "Tim", "haha", "unimelb");
            mDao.insertUser(user);
            // word = new Word("World");
            user = new User("2", "Matt", "hehe", "unimelb");
            mDao.insertUser(user);
            return null;
        }
    }
}

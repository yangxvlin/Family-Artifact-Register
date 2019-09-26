package com.example.family_artifact_register.FoundationLayer.SocialModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Friend.class}, version = 1)
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
                    // delete the database from device
                    appContext.deleteDatabase("user_database");
                    // fallbackToDestructiveMigration(): By including this clause in your app's
                    // database-building logic, you tell Room to destructively recreate your app's
                    // database tables in cases where a migration path between schema versions is missing.
                    instance = Room.databaseBuilder(appContext,
                            UserDatabase.class, "user_database")
                            .fallbackToDestructiveMigration()
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
//            mDao.deleteAllUser();
            // fake data
            User tim = new User("Tim", "haha", "unimelb");
            mDao.insertUser(tim);

            User matt = new User("Matt", "hehe", "unimelb");
            mDao.insertUser(matt);

            User leon = new User("Leon", "haha", "unimelb");
            mDao.insertUser(leon);

            User justin = new User("Justin", "hehe", "unimelb");
            mDao.insertUser(justin);

            User richard = new User("Richard", "haha", "unimelb");
            mDao.insertUser(richard);

            User me = new User("Me", "haha", "unimelb");
            mDao.insertUser(me);

            Friend friend = new Friend(me.username, tim.username);
            mDao.insertFriend(friend);

            friend = new Friend(me.username, matt.username);
            mDao.insertFriend(friend);
            return null;
        }
    }
}

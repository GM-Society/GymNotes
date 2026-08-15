package com.gmsociety.gymnotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class GymNotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: GymNotesDatabase? = null

        fun getDatabase(context: Context): GymNotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GymNotesDatabase::class.java,
                    "gym_notes_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}

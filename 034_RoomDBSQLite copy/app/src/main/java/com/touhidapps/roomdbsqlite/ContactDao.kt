package com.touhidapps.roomdbsqlite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {

    @Query("SELECT * FROM ${DbConstants.TABLE_CONTACT_2}")
    suspend fun getAllContact(): List<ContactEntity>

    @Insert
    suspend fun insertAll(vararg contacts: ContactEntity)

    @Update
    suspend fun updateContact(vararg contacts: ContactEntity)

    @Delete
    suspend fun deleteContact(vararg contacts: ContactEntity)

}

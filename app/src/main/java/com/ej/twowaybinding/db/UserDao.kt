package com.ej.twowaybinding.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAllData() : List<UserEntity>

    @Query("SELECT * FROM user_table")
    fun getAllDataWithFlow() : Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user : UserEntity)

    @Update
    fun update(userEntity: UserEntity)
    @Delete
    fun delete(userEntity: UserEntity)
}
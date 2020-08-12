package com.example.scoreis.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: DBGame)

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGame(gameId: Int): GameWithPlayersWithScores?

    @Transaction
    @Query("SELECT * FROM games WHERE id = :gameId")
    fun observeGame(gameId: Int): LiveData<GameWithPlayersWithScores?>

}

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(players: List<DBPlayer>)
}

@Dao
interface ScoreDao {
    @Insert
    suspend fun insert(scores: List<DBScore>)
}

@Database(entities = [DBGame::class, DBPlayer::class, DBScore::class], version = 1)
abstract class ScoreisDatabase : RoomDatabase() {
    abstract val gameDao: GameDao
    abstract val playerDao: PlayerDao
    abstract val scoreDao: ScoreDao
}

private lateinit var INSTANCE: ScoreisDatabase

fun getDatabase(context: Context): ScoreisDatabase {
    synchronized(ScoreisDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ScoreisDatabase::class.java,
                "scoreis-db"
            ).build()
        }
    }
    return INSTANCE
}

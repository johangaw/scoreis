package com.example.scoreis.database

import androidx.room.*

@Entity(tableName = "games")
data class DBGame(
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

@Entity(
    tableName = "players",
    foreignKeys = [ForeignKey(
        entity = DBGame::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("gameId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DBPlayer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val gameId: Int
)

@Entity(
    tableName = "scores",
    foreignKeys = [ForeignKey(
        entity = DBPlayer::class,
        parentColumns = ["id"],
        childColumns = ["playerId"]
    )]
)
data class DBScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playerId: Int,
    val value: Int
)

data class GameWithPlayersWithScores(
    @Embedded val game: DBGame,
    @Relation(
        entity = DBPlayer::class,
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val players: List<PlayerWithScores>
)

data class PlayerWithScores(
    @Embedded val player: DBPlayer,
    @Relation(
        parentColumn = "id",
        entityColumn = "playerId"
    )
    val scores: List<DBScore>
)

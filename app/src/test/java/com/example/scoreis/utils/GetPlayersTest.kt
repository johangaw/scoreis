package com.example.scoreis.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class GetPlayersTest {
    @Test
    fun `when only names are provided it returns those`() {
        assertThat(getPlayers("Elin Johan Josef")).containsExactly(
            "Elin",
            "Johan",
            "Josef"
        )
    }

    @Test
    fun `when "och" is provided it is ignored`() {
        assertThat(getPlayers("Johan och Josef")).containsExactly(
            "Johan",
            "Josef"
        )
    }
}
package com.example.scoreis.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class GetParticipantsTest {
    @Test
    fun `when only names are provided it returns those`() {
        assertThat(getParticipants("Elin Johan Josef")).containsExactly(
            "Elin",
            "Johan",
            "Josef"
        )
    }

    @Test
    fun `when "och" is provided it is ignored`() {
        assertThat(getParticipants("Johan och Josef")).containsExactly(
            "Johan",
            "Josef"
        )
    }
}
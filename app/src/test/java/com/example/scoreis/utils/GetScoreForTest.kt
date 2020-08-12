package com.example.scoreis.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Ignore
import org.junit.Test

class GetScoreForTest {

    @Test
    fun `when name and score are provided it returns the score`() {
        val speech = "Elin 10 Johan 9"
        assertThat(getScoreFor(speech, "Elin")).isEqualTo(10)
        assertThat(getScoreFor(speech, "Johan")).isEqualTo(9)
    }

    @Test
    fun `when "bridging" words are included it returns the correct score`() {
        val speech = "Elin 100 poäng och Johan fick 2"
        assertThat(getScoreFor(speech, "Elin")).isEqualTo(100)
        assertThat(getScoreFor(speech, "Johan")).isEqualTo(2)
    }

    @Ignore("Not implemented yet")
    @Test
    fun `when the points comes before the participant`() {
        val speech = "25 poäng till Elin 10 till Johan"
        assertThat(getScoreFor(speech, "Elin")).isEqualTo(25)
        assertThat(getScoreFor(speech, "Johan")).isEqualTo(10)
    }
}
package com.example.androidlab2.model

class DeceptionDetector {

    private val deceptionRules = listOf(


        Regex(
            "\\b(freez(e|ing|ing)|frozen|ice[- ]?up|icing|frost|"
                    + "sub[- ]?zero|below[- ]?freezing|extreme cold)\\b",
            RegexOption.IGNORE_CASE
        ),


        Regex(
            "\\b(valve|valves)\\b.*\\b(fail(ed|ure)?|broken|stuck|"
                    + "malfunction(ing)?|leak(ing)?)\\b",
            RegexOption.IGNORE_CASE
        ),

        Regex(
            "\\b(electrical|electric|power|voltage|current|circuit|"
                    + "wiring|transformer)\\b.*\\b(fail(ed|ure)?|short|"
                    + "surge|overload|fault|hazard|danger|unstable)\\b",
            RegexOption.IGNORE_CASE
        ),

        Regex(
            "\\b(structural|structure|wall|foundation|beam|pipe|"
                    + "building)\\b.*\\b(crack(ed)?|fracture|damage(d)?|"
                    + "collapse|unstable|weak)\\b",
            RegexOption.IGNORE_CASE
        ),
        Regex(
            "\\b(critical|urgent|imminent|emergency|immediately|"
                    + "danger|dangerous|acute)\\b",
            RegexOption.IGNORE_CASE
        ),


        Regex(
            "\\b(hvac|compressor|compression|blowout|pressure|"
                    + "cooling system|heating system)\\b.{0,40}\\b("
                    + "failure|failed|fault|faulty|broken|malfunction|"
                    + "blowout|leak|leaking|danger|dangerous)\\b",
            RegexOption.IGNORE_CASE
        ),


        Regex(
            "\\b(do not|don't|never)\\s+"
                    + "(lower|reduce|change|adjust|modify)\\b",
            RegexOption.IGNORE_CASE
        )

    )

    fun validatePullComments(comment: String): Int {
        var score = 0

        for (rule in deceptionRules) {
            if (rule.containsMatchIn(comment)) {
                score += 25
            }
        }

        return score.coerceAtMost(100)
    }
}
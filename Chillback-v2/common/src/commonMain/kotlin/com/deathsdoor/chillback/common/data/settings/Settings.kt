package com.deathsdoor.chillback.common.data.settings

import androidx.compose.runtime.Composable
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.icerock.moko.resources.compose.stringResource
import com.deathsdoor.chillback.common.resources.MR as resources

internal enum class Settings {
    About {
        @Composable
        override fun category() : SettingsCategory =  SettingsCategory (
            name = stringResource(resources.strings.settings_about),
            items = listOf(
                SettingsItem(
                    stringResource(resources.strings.settings_about_version_name),
                    "1.0",
                    Type.Info
                ), SettingsItem(
                    stringResource(resources.strings.settings_about_third_party_software_name),
                    stringResource(resources.strings.settings_about_third_party_software_description),
                    Type.OpenWebsite("")
                ), SettingsItem(
                    stringResource(resources.strings.settings_about_terms_conditions_name),
                    stringResource(resources.strings.settings_about_terms_conditions_description),
                    Type.OpenWebsite("")
                ),
                SettingsItem(
                    stringResource(resources.strings.settings_about_privacy_policy_name),
                    stringResource(resources.strings.settings_about_privacy_policy_description),
                    Type.OpenWebsite("")
                ),
                SettingsItem(
                    stringResource(resources.strings.settings_about_support_name),
                    stringResource(resources.strings.settings_about_support_description),
                    Type.OpenWebsite("")
                )
            )
        )
    },
    Account{
        @Composable
        override fun category() : SettingsCategory = SettingsCategory(
            name = stringResource(resources.strings.settings_account),
            items = listOf(
                SettingsItem(
                    name = stringResource(resources.strings.settings_account_log_out_name),
                    description = stringResource(resources.strings.settings_account_log_out_description),
                    type = Type.Custom {

                    }
                ),
                SettingsItem(
                    name = stringResource(resources.strings.settings_account_delete_account_name),
                    description = stringResource(resources.strings.settings_account_delete_account_description),
                    type = Type.Custom {

                    }
                ),
                SettingsItem(
                    name = stringResource(resources.strings.settings_account_change_password_name),
                    description = stringResource(resources.strings.settings_account_change_password_description),
                    type = Type.Custom {

                    }
                ),
                SettingsItem(
                    name = stringResource(resources.strings.settings_account_language_name),
                    description = stringResource(resources.strings.settings_account_language_description),
                    type = Type.Custom {

                    }
                )
            )
        )
    };

    @Composable
    abstract fun category() : SettingsCategory

    companion object {
        private val settings get() = "${Firebase.auth.currentUser?.uid}/settings"
        val currentTheme get() = "$settings/theme/current"
    }
}